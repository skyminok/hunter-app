package app;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Nonnull;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.SecureRandom;

@Slf4j
public class RequestIdFilter extends OncePerRequestFilter {

    public static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final int ID_SIZE = 8;
    private static final String REQUEST_ID_KEY = "requestId";
    private transient final SecureRandom secureRandom;

    public RequestIdFilter() {
        byte[] seed = generateSeed();
        secureRandom = new SecureRandom(seed);
    }

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request,
                                    @Nonnull HttpServletResponse response,
                                    @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String requestId = obtainRequestId(request);
        MDC.put(REQUEST_ID_KEY, requestId);
        try {
            log.trace("Incoming request: {}", requestId);
            response.setHeader(REQUEST_ID_HEADER, requestId);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(REQUEST_ID_KEY);
        }
    }

    private String obtainRequestId(@Nonnull HttpServletRequest request) {
        String requestHeader = request.getHeader(REQUEST_ID_HEADER);
        if (requestHeader == null) {
            byte[] bytes = new byte[ID_SIZE];
            secureRandom.nextBytes(bytes);
            return DatatypeConverter.printHexBinary(bytes);
        }
        return requestHeader;
    }

    private byte[] generateSeed() {
        long t = System.currentTimeMillis();
        return new byte[]{
                (byte) (t & 0xFF),
                (byte) (t >> 8 & 0xFF),
                (byte) (t >> 16 & 0xFF),
                (byte) (t >> 24 & 0xFF),
                (byte) (t >> 32 & 0xFF),
                (byte) (t >> 40 & 0xFF),
                (byte) (t >> 48 & 0xFF),
                (byte) (t >> 56 & 0xFF)
        };
    }
}

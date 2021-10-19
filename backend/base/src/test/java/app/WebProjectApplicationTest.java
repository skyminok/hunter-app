package app;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Boot testing of entire app
 */
@Slf4j
class WebProjectApplicationTest extends BaseBackendTest {

    private static final MediaType TEXT_PLAIN_UTF8 = new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8);
    private static final String REQUEST_ID_HEADER = "X-Request-Id";

    @Autowired
    private transient MockMvc mvc;
    @Autowired
    private transient Environment env;

    @Test
    void testHealth() throws Exception {
        mvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TEXT_PLAIN_UTF8))
                .andExpect(content().string("OK"))
                .andExpect(header().doesNotExist(REQUEST_ID_HEADER));
    }

    @Test
    void testApi() throws Exception {
        ResultActions resultActions = performGetApiRequest(get("/api"));
        checkVersionResponse(resultActions);
    }

    @Test
    void testApiWithRequestId() throws Exception {
        String generatedRequestId = UUID.randomUUID().toString();
        MockHttpServletRequestBuilder requestBuilder =
                get("/api").header(REQUEST_ID_HEADER, generatedRequestId);
        ResultActions resultActions = performGetApiRequest(requestBuilder)
                .andExpect(header().stringValues(REQUEST_ID_HEADER, generatedRequestId));
        checkVersionResponse(resultActions);
    }

    private void checkVersionResponse(ResultActions resultActions) throws Exception {
        resultActions
                .andExpect(jsonStringMatcher("version", "app.project-info.version"))
                .andExpect(jsonStringMatcher("gitBranch", "app.project-info.gitBranch"))
                .andExpect(jsonStringMatcher("gitHash", "app.project-info.gitHash"))
                .andExpect(jsonStringMatcher("gitHashFull", "app.project-info.gitHashFull"));
    }

    private ResultMatcher jsonStringMatcher(String nodeName, String property) {
        String value = env.getProperty(property);
        assertNotNull(value);
        return jsonPath(nodeName).value(value);
    }

    private ResultActions performGetApiRequest(MockHttpServletRequestBuilder requestBuilder) throws Exception {
        return mvc.perform(requestBuilder)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(header().exists(REQUEST_ID_HEADER));
    }
}

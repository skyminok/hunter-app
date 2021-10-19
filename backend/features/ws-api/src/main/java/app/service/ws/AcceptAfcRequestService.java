package app.service.ws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AcceptAfcRequestService {

    private final EntityManager em;

    public void execute(String requestBody) {
        AfcRequest request = new AfcRequest();
        request.setSourceRequest(requestBody);
        em.persist(request);
        em.flush();
    }
}

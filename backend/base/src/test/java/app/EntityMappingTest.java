package app;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@Transactional
public class EntityMappingTest extends BaseBackendTest {

    @Autowired
    private transient EntityManager em;

    @Test
    void test() {
        Metamodel metamodel = em.getMetamodel();
        Set<EntityType<?>> entities = metamodel.getEntities();
        for (EntityType<?> entity : entities) {
            log.info("Testing entity: {}", entity.getName());
            String query = String.format("select e from %s e where 1 = 0", entity.getName());
            List<?> list = em.createQuery(query, entity.getJavaType())
                    .getResultList();
            assertTrue(list.isEmpty());
        }
    }
}

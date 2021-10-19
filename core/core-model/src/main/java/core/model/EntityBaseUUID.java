package core.model;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.io.Serial;
import java.util.UUID;

@Getter
@MappedSuperclass
@ToString(of = "id")
public abstract class EntityBaseUUID extends EntityBase<UUID> {

    @Serial
    private static final long serialVersionUID = 4957231455602524793L;

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @PrePersist
    void initId() {
        id = UUID.randomUUID();
    }
}

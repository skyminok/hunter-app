package app.service.ws;

import core.model.EntityBaseUUID;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serial;

@Entity
@Table(name = "afc_request")
@Getter
@Setter
public class AfcRequest extends EntityBaseUUID {

    @Serial
    private static final long serialVersionUID = -3937399130396876458L;

    @Column(name = "source_request")
    private String sourceRequest;
}

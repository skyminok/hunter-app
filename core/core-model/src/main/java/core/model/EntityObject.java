package core.model;

import java.io.Serializable;

public interface EntityObject<K extends Serializable> extends Serializable {

    K getId();
}

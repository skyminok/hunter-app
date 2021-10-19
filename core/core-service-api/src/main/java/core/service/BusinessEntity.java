package core.service;

import java.util.List;

public interface BusinessEntity {

    List<ClassMethod> getClassMethods();

    List<ObjectMethod> getObjectMethods();
}

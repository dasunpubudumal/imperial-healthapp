package org.health.imperialhealthapp.models;

import java.lang.reflect.Executable;

@FunctionalInterface
public interface NetworkUtil<T> {

    GeneralResult<T> wrap(Executable executable);

}

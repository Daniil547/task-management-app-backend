package io.github.daniil547.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;

public final class Reflection {
    public static Object invokeMethod(Object onObject, String methodName, Object... args) {
        try {
            return onObject.getClass()
                           .getMethod(methodName)
                           .invoke(onObject, args);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("Method " + methodName + " not found in class " + onObject.getClass().getName() + "\n" +
                                            "If it's an accessor, check if it follows the common naming convention\n", e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(onObject.getClass().getName() + "." + methodName + "isn't public", e);
        } catch (InvocationTargetException e) {
            throw new IllegalStateException(onObject.getClass().getName() + "." + methodName + "threw an exception", e);
        }
    }

    public static Class<?>[] getGenerics(Class<?> clazz) {
        return (Class<?>[]) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments();
    }
}

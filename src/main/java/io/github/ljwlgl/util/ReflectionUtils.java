package io.github.ljwlgl.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author liwei
 * @date 2020/7/5 4:41 下午
 */
public class ReflectionUtils {

  public static Object invokeMethod(Method method, Object target) {
    return invokeMethod(method, target, new Object[0]);
  }

  public static Object invokeMethod(Method method, Object target, Object... args) {
    try {
      return method.invoke(target, args);
    }
    catch (Exception ex) {
      handleReflectionException(ex);
    }
    throw new IllegalStateException("Should never get here");
  }

  public static void handleReflectionException(Exception ex) {
    if (ex instanceof NoSuchMethodException) {
      throw new IllegalStateException("Method not found: " + ex.getMessage());
    }
    if (ex instanceof IllegalAccessException) {
      throw new IllegalStateException("Could not access method: " + ex.getMessage());
    }
    if (ex instanceof InvocationTargetException) {
      handleInvocationTargetException((InvocationTargetException) ex);
    }
    if (ex instanceof RuntimeException) {
      throw (RuntimeException) ex;
    }
    throw new UndeclaredThrowableException(ex);
  }

  public static void handleInvocationTargetException(InvocationTargetException ex) {
    rethrowRuntimeException(ex.getTargetException());
  }

  public static void rethrowRuntimeException(Throwable ex) {
    if (ex instanceof RuntimeException) {
      throw (RuntimeException) ex;
    }
    if (ex instanceof Error) {
      throw (Error) ex;
    }
    throw new UndeclaredThrowableException(ex);
  }

  public static Method getMethod(Class<?> clazz, String name) {
    return getMethod(clazz,name,null,null);
  }

  public static Method getMethod(Class<?> clazz, String name, String returnType, String[] args) {
    Method[] methods = clazz.getDeclaredMethods();
    MainLoop:
    for (Method method : methods) {
      if (name != null && !name.equals(method.getName())) {
        continue;
      }
      if(returnType != null && !returnType.equals(method.getReturnType().getName())) {
        continue;
      }
      if (args != null) {
        Class<?>[] args2 = method.getParameterTypes();
        if (args2.length != args.length) {
          continue;
        }
        for (int count = 0; count < args.length; count++) {
          if (!(args[count].equals(args2[count].getName()))) {
            continue MainLoop;
          }
        }
      }
      return method;
    }
    return null;
  }
}

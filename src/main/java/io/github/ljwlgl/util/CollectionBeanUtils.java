package io.github.ljwlgl.util;

import io.github.ljwlgl.asserts.Assert;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liwei
 * @date 2020/7/5 4:22 下午
 */
public class CollectionBeanUtils {

  private static final String GETTER_PREFIX = "get";


  public static <T> Map<String, T> convertToMap(Iterable<T> iterable, Class<T> c, String name) {
    Assert.hasLength(name, "keyFieldName can not be empty !!!!!");

    if (IterableUtils.isEmpty(iterable)) {
      return Collections.emptyMap();
    }

    Method method = getGetterMethod(c, name);

    Map<String, T> result = new HashMap<>();
    for (T instance : iterable) {
      String key = (String) ReflectionUtils.invokeMethod(method, instance);
      result.put(key, instance);
    }
    return result;
  }

  public static <T> Map<String, List<T>> groupToMap(Iterable<T> iterable, Class<T> c, String name) {
    Assert.hasLength(name, "keyFieldName can not be empty !!!!!");

    if (IterableUtils.isEmpty(iterable)) {
      return Collections.emptyMap();
    }

    Method getterMethod = getGetterMethod(c, name);

    Map<String, List<T>> result = new HashMap<>();
    for (T instance : iterable) {
      String key = (String) ReflectionUtils.invokeMethod(getterMethod, instance);
      List<T> groupContent = result.get(key) == null ? new ArrayList<T>() : result.get(key);
      groupContent.add(instance);
      result.put(key, groupContent);
    }
    return result;
  }

  public static <T, F> List<F> getFieldList(Iterable<T> iterable, Class<T> c, String name) {
    Assert.hasLength(name, "keyFieldName can not be empty !!!!!");

    if (IterableUtils.isEmpty(iterable)) {
      return Collections.emptyList();
    }

    Method getterMethod = getGetterMethod(c, name);

    List<F> result = new ArrayList<>();
    for (T instance : iterable) {
      F value = (F) ReflectionUtils.invokeMethod(getterMethod, instance);
      result.add(value);
    }
    return result;
  }

  private static <T> Method getGetterMethod(Class<T> c, String name) {
    String getterMethodName = name;
    Method getterMethod;
    if (!name.startsWith(GETTER_PREFIX)) {
      getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name);
    }
    getterMethod = ReflectionUtils.getMethod(c, getterMethodName);
    if (getterMethod == null && !getterMethodName.equals(name)) {
      getterMethodName = name;
      getterMethod = ReflectionUtils.getMethod(c, getterMethodName);
    }
    if (getterMethod == null) {
      throw new IllegalArgumentException(String.format("Could not find getter method '%s' on %s", name, c));
    }
    return getterMethod;
  }
}

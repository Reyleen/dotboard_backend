package it.uniroma3.siw.dotboard_backend.utils;

import java.lang.reflect.Field;

public interface MergeUpdate {

  static <T> T merge(T target, T source) throws IllegalAccessException {
    if (target == null || source == null) {
      return target != null ? target : source;
    }

    for (Field field : target.getClass().getDeclaredFields()) {
      field.setAccessible(true);
      Object sourceValue = field.get(source);
      if (sourceValue != null) {
        field.set(target, sourceValue);
      }
    }

    return target;
  }

}


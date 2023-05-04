package it.uniroma3.siw.dotboard_backend.services;

import java.lang.reflect.Field;
import java.util.*;

public class Sanitizer<T> {

  // A set of keys to nullify from the object T (e.g. ApplicationUser)
  private static final Set<String> KEYS_TO_REMOVE = new HashSet<>(
      Arrays.asList(
          "id",
          "uuid",
          "createdAt",
          "version",
          "updatedAt",
          "deletedAt"
      )
  );

  public T sanitize(T object) throws IllegalAccessException {

    // the fields of the object
    Field[] fields = object.getClass().getDeclaredFields();

    // for each field, if it is in the KEYS_TO_REMOVE set, set it to null
    for (Field field : fields) {
      if (KEYS_TO_REMOVE.contains(field.getName())) {
        field.setAccessible(true);
        field.set(object, null);
      }
    }

    return object;
  }

}

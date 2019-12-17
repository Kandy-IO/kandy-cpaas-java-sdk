package com.cpaassdk;

import org.json.JSONArray;
import org.json.JSONObject;

public class Util {
  public static String extractString(JSONObject obj, String key, String defaultValue) {
    String val = null;

    if (obj.has(key)) {
      val = (String) obj.get(key);
    } else if (defaultValue != null) {
      val = defaultValue;
    }

    return val;
  }

  public static String extractString(JSONObject obj, String key) {
    return extractString(obj, key, null);
  }

  public static JSONArray extractArray(JSONObject obj, String key) {
    if (obj.get(key) instanceof JSONArray) {
      return (JSONArray) obj.get(key);
    } else {
      String[] arr = {(String) obj.get(key)};

      new JSONArray(arr);
    }
    return null;
  }

  public static String idFrom(String url) {
    if (url != null) {
      String[] parts = url.split("/");

      return parts[parts.length - 1];
    }

    return null;
  }
}

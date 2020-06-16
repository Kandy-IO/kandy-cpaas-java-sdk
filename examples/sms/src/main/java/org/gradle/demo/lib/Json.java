package org.gradle.demo.lib;

import java.io.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class Json {
  final static String filePath = "tmp/notification.json";

  public static JSONArray read() throws IOException {
    String a = content();

    return new JSONArray(a);
  }

  public static void write(JSONObject obj) throws IOException {
    FileWriter fileWriter;
    File file = new File(filePath);

    if (fileExists()) {
      JSONArray arr = read();
      fileWriter = new FileWriter(file, false);

      arr.put(arr.length(), obj);

      fileWriter.write(arr.toString());

    } else {
      file.createNewFile();

      fileWriter = new FileWriter(file);
      JSONArray a = new JSONArray().put(0, obj);
      fileWriter.write(a.toString());
    }

    fileWriter.close();
  }

  public static Boolean fileExists() {
    File file = new File(filePath);

    return file.exists();
  }

  public static String content() throws IOException {
    if (!fileExists()) {
      return "[]";
    }

    BufferedReader reader = new BufferedReader(new FileReader(filePath));
    StringBuilder stringBuilder = new StringBuilder();
    char[] buffer = new char[10];
    while (reader.read(buffer) != -1) {
      stringBuilder.append(new String(buffer));
      buffer = new char[10];
    }
    reader.close();

    String content = stringBuilder.toString();
    return content;
  }
}
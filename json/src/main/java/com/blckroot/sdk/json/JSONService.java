package com.blckroot.sdk.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.io.PrintStream;

public final class JSONService {
    public static String getFormattedJSON(Object object) {
        try {
            JsonMapper jsonMapper = new JsonMapper();
            return jsonMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getObjectFromJSON(String json, Class<?> object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printFormattedJSON(PrintStream printStream, Object object) {
        printStream.print(getFormattedJSON(object));
    }
}

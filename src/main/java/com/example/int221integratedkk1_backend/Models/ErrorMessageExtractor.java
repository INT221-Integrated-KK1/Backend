package com.example.int221integratedkk1_backend.Models;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessageExtractor {

    private static final Map<String, Integer> FIELD_MAX_LENGTH_MAP = new HashMap<>();

    static {
        FIELD_MAX_LENGTH_MAP.put("taskTitle", 100);
        FIELD_MAX_LENGTH_MAP.put("taskDescription", 500);
        FIELD_MAX_LENGTH_MAP.put("taskAssignees", 30);
        FIELD_MAX_LENGTH_MAP.put("statusName", 50);
        FIELD_MAX_LENGTH_MAP.put("statusDescription", 200);
    }

    public static String extractErrorMessage(String errorMessage) {
        String[] parts = errorMessage.split(":");
        if (parts.length >= 2) {
            String detail = parts[1].trim();
            if (detail.startsWith("Data too long for column")) {
                String columnName = detail.substring(detail.indexOf("'") + 1, detail.lastIndexOf("'"));
                String fieldName = columnName.substring(columnName.indexOf("_") + 1);
                Integer maxLength = FIELD_MAX_LENGTH_MAP.get(fieldName);
                if (maxLength != null) {

                    return "Size of " + fieldName + " must be between 0 and " + maxLength + " characters";
                }
            }
        }
        return "Invalid input data";
    }
}

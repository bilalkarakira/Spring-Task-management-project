package com.bilal.taskmanager.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.bilal.taskmanager.dto.AuthRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthRequestParser {
    private final ObjectMapper objectMapper;

    public AuthRequestParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public AuthRequest parse(String contentType, String body) {
        if (body == null || body.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Request body is empty");
        }

        // Try straightforward JSON parsing first
        try {
            return objectMapper.readValue(body, AuthRequest.class);
        } catch (JsonProcessingException ignored) {
            // fallthrough to other strategies
        }

        String ct = contentType == null ? "" : contentType.toLowerCase();

        // If looks like form-encoded or plain text, try key=value pairs
        if (ct.contains(MediaType.APPLICATION_FORM_URLENCODED_VALUE) || ct.contains(MediaType.TEXT_PLAIN_VALUE) || ct.isEmpty()) {
            String[] parts = body.split("&");
            String email = null, password = null;
            for (String p : parts) {
                String[] kv = p.split("=", 2);
                if (kv.length == 2) {
                    String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
                    String value = URLDecoder.decode(kv[1], StandardCharsets.UTF_8);
                    if ("email".equalsIgnoreCase(key)) email = value;
                    if ("password".equalsIgnoreCase(key)) password = value;
                }
            }
            if (email != null && password != null) {
                AuthRequest req = new AuthRequest();
                req.setEmail(email);
                req.setPassword(password);
                return req;
            }
        }

        // Last resort: naive JSON-like extraction (handles text/plain that contains JSON-ish text)
        String trimmed = body.trim();
        if (trimmed.startsWith("{") && trimmed.endsWith("}")) {
            String email = extractQuotedValue(trimmed, "email");
            String password = extractQuotedValue(trimmed, "password");
            if (email != null && password != null) {
                AuthRequest req = new AuthRequest();
                req.setEmail(email);
                req.setPassword(password);
                return req;
            }
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Unsupported or invalid request body. Expected JSON or form-encoded data with 'email' and 'password'.");
    }

    private String extractQuotedValue(String s, String key) {
        String pattern = "\"" + key + "\"\s*:\s*\"";
        int idx = s.indexOf(pattern);
        if (idx == -1) return null;
        int start = idx + pattern.length();
        int end = s.indexOf('"', start);
        if (end == -1) return null;
        return s.substring(start, end);
    }
}

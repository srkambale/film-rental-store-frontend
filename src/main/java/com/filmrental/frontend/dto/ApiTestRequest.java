package com.filmrental.frontend.dto;

public class ApiTestRequest {
    private String method;
    private String url;
    private String body;
    private String token;
    private String moduleId;

    public ApiTestRequest() {}

    public ApiTestRequest(String method, String url, String body, String token, String moduleId) {
        this.method = method;
        this.url = url;
        this.body = body;
        this.token = token;
        this.moduleId = moduleId;
    }

    // ── Getters & Setters ──────────────────────────────────────
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getModuleId() { return moduleId; }
    public void setModuleId(String moduleId) { this.moduleId = moduleId; }
}

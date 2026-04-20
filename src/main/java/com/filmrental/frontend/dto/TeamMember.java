package com.filmrental.frontend.dto;

import java.util.ArrayList;
import java.util.List;

public class TeamMember {
    private String name;
    private String role;
    private String imagePath;
    private String moduleId;
    private String description;
    private List<EndpointInfo> endpoints;
    private List<String> allowedRoles;

    public TeamMember() {}

    public TeamMember(String name, String role, String imagePath, String moduleId,
                      String description, List<EndpointInfo> endpoints) {
        this.name = name;
        this.role = role;
        this.imagePath = imagePath;
        this.moduleId = moduleId;
        this.description = description;
        this.endpoints = endpoints;
        this.allowedRoles = new ArrayList<>();
    }

    public TeamMember(String name, String role, String imagePath, String moduleId,
                      String description, List<EndpointInfo> endpoints, List<String> allowedRoles) {
        this.name = name;
        this.role = role;
        this.imagePath = imagePath;
        this.moduleId = moduleId;
        this.description = description;
        this.endpoints = endpoints;
        this.allowedRoles = allowedRoles;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public String getModuleId() { return moduleId; }
    public void setModuleId(String moduleId) { this.moduleId = moduleId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<EndpointInfo> getEndpoints() { return endpoints; }
    public void setEndpoints(List<EndpointInfo> endpoints) { this.endpoints = endpoints; }

    public List<String> getAllowedRoles() { return allowedRoles; }
    public void setAllowedRoles(List<String> allowedRoles) { this.allowedRoles = allowedRoles; }
}

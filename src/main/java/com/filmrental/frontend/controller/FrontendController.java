package com.filmrental.frontend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmrental.frontend.client.BackendClient;
import com.filmrental.frontend.dto.ApiTestRequest;
import com.filmrental.frontend.dto.EndpointInfo;
import com.filmrental.frontend.dto.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class FrontendController {

    @Value("${backend.base-url:http://localhost:8081}")
    private String backendUrl;

    @Autowired
    private BackendClient backendClient;

    private final List<TeamMember> teamMembers = new ArrayList<>();

    @PostConstruct
    public void init() {
        // Mayank Agrawal: Auth Module
        teamMembers.add(new TeamMember("Mayank Agrawal", "Auth Module", "/images/mayank.png", "auth", 
            "Authentication and Security System",
            Arrays.asList(
                new EndpointInfo("POST", "/api/v1/auth/register", "Register new Customer/Staff/Admin", "Public", "{\"firstName\":\"John\",\"lastName\":\"Doe\",\"email\":\"john@example.com\",\"username\":\"john_doe_99\",\"password\":\"pass123\",\"role\":\"CUSTOMER\",\"storeId\":1}"),
                new EndpointInfo("POST", "/api/v1/auth/login", "User Login (Email/Username)", "Public", "{\"identifier\":\"john@example.com\",\"password\":\"pass123\"}")
            )));
        
        // ... (remaining team members added here)

        // Roshni Bajaj: Customer Module
        teamMembers.add(new TeamMember("Roshni Bajaj", "Customer Module", "/images/roshni.png", "customer", 
            "Customer Management and Profile Operations",
            Arrays.asList(
                new EndpointInfo("POST", "/api/v1/customer", "Create New Customer Profile", "Public", "{\"firstName\":\"Alice\",\"lastName\":\"Smith\",\"email\":\"alice@example.com\",\"storeId\":1,\"addressId\":1}"),
                new EndpointInfo("GET", "/api/v1/customer/{id}", "Get Customer Details", "Admin/Customer"),
                new EndpointInfo("PUT", "/api/v1/customer/{id}", "Update Profile Basics", "Admin/Customer", "{\"firstName\":\"Alice\",\"lastName\":\"Updated\",\"email\":\"alice.new@example.com\",\"storeId\":1,\"addressId\":1,\"active\":true}"),
                new EndpointInfo("PATCH", "/api/v1/customer/{id}", "Partial Profile Update", "Admin/Customer", "{\"firstName\":\"Alice\",\"lastName\":\"Updated\",\"email\":\"alice.new@example.com\"}"),
                new EndpointInfo("PUT", "/api/v1/customer/{id}/address", "Update Customer Address", "Admin/Customer", "{\"address\":\"456 New St\",\"address2\":\"\",\"district\":\"Central\",\"cityId\":1,\"postalCode\":\"12345\",\"phone\":\"555-0199\"}"),
                new EndpointInfo("PATCH", "/api/v1/customer/{id}/address", "Partial Address Update", "Admin/Customer", "{\"address\":\"456 New St\",\"phone\":\"555-9999\"}"),
                new EndpointInfo("GET", "/api/v1/customer/search", "Search Customers by Name", "Admin/Staff", "{\"name\":\"Smith\"}"),
                new EndpointInfo("GET", "/api/v1/customer/location", "Search Customers by Location", "Admin/Staff", "{\"location\":\"London\"}"),
                new EndpointInfo("GET", "/api/v1/customer/{id}/rentals", "View Customer Rental History", "Admin/Customer"),
                new EndpointInfo("GET", "/api/v1/customer/{id}/rentals/{rentalId}", "Get Specific Rental Detail", "Admin/Customer"),
                new EndpointInfo("GET", "/api/v1/customer/{id}/payments", "View Personal Payments", "Admin/Customer"),
                new EndpointInfo("GET", "/api/v1/customer/{id}/payment/{paymentId}", "Get Specific Payment Detail", "Admin/Customer"),
                new EndpointInfo("POST", "/api/v1/customer/rentals", "Create Rental (Customer Side)", "Customer Only", "{\"customerId\":1, \"inventoryId\":1}"),
                new EndpointInfo("PUT", "/api/v1/customer/rentals/{rentalId}/return", "Return Film (Customer Side)", "Customer Only"),
                new EndpointInfo("GET", "/api/v1/films/search/title", "Customer Search: Films by Title", "Any", "{\"title\":\"ACADEMY DINOSAUR\"}"),
                new EndpointInfo("GET", "/api/v1/films/search/actor", "Customer Search: Films by Actor", "Any", "{\"actor\":\"PENELOPE\"}"),
                new EndpointInfo("GET", "/api/v1/films/search/category", "Customer Search: Films by Category", "Any", "{\"category\":\"Action\"}")
            )));

        // Abhishek Rodage: Catalog and Admin Module
        teamMembers.add(new TeamMember("Abhishek Rodage", "Catalog (Admin) Module", "/images/abhishek.png", "catalog", 
            "Film Catalog Management and Admin Operations",
            Arrays.asList(
                new EndpointInfo("GET", "/api/v1/catalog/films", "List All Films (Summary)", "Any"),
                new EndpointInfo("GET", "/api/v1/catalog/films/{id}", "Get Detailed Film Info", "Any"),
                new EndpointInfo("GET", "/api/v1/catalog/films/search", "Admin Search: Films (Title/Year)", "Any", "{\"title\":\"ACADEMY\",\"year\":\"2006\"}"),
                new EndpointInfo("GET", "/api/v1/catalog/films/category/{categoryName}", "Filter Films by Genre", "Any", "{\"categoryName\":\"Action\"}"),
                new EndpointInfo("GET", "/api/v1/catalog/films/actor", "Films by Actor Name Query", "Any", "{\"name\":\"PENELOPE\"}"),
                new EndpointInfo("GET", "/api/v1/catalog/films/language/{name}", "Films by Language", "Any"),
                new EndpointInfo("GET", "/api/v1/catalog/films/rating/{rating}", "Films by MPAA Rating", "Any"),
                new EndpointInfo("PATCH", "/api/v1/catalog/films/{id}", "Partial Film Update", "Admin Only", "{\"title\":\"RESERVATION LAKES\",\"rentalRate\":4.99}"),
                new EndpointInfo("GET", "/api/v1/catalog/actors", "List All Actors", "Any"),
                new EndpointInfo("GET", "/api/v1/catalog/actors/search", "Search Actors by Name", "Any", "{\"name\":\"PENELOPE\"}"),
                new EndpointInfo("GET", "/api/v1/catalog/actors/{id}", "Get Actor Details", "Any"),
                new EndpointInfo("POST", "/api/v1/catalog/actors", "Create New Actor", "Admin Only", "{\"firstName\":\"MARLON\",\"lastName\":\"BRANDO\"}"),
                new EndpointInfo("PUT", "/api/v1/catalog/actors/{id}", "Update Actor Info", "Admin Only", "{\"firstName\":\"MARLON\",\"lastName\":\"UPDATED\"}"),
                new EndpointInfo("PATCH", "/api/v1/catalog/actors/{id}", "Partial Actor Update", "Admin Only", "{\"lastName\":\"BRANDO UPDATED\"}"),
                new EndpointInfo("GET", "/api/v1/catalog/categories", "List All Genres/Categories", "Any"),
                new EndpointInfo("GET", "/api/v1/catalog/categories/search", "Search Categories by Name", "Any", "{\"name\":\"Action\"}"),
                new EndpointInfo("GET", "/api/v1/catalog/categories/{id}", "Get Category Details", "Any"),
                new EndpointInfo("POST", "/api/v1/catalog/categories", "Create New Category", "Admin Only", "{\"name\":\"Noir\"}"),
                new EndpointInfo("PUT", "/api/v1/catalog/categories/{id}", "Update Category Info", "Admin Only", "{\"name\":\"Noir Updated\"}"),
                new EndpointInfo("PATCH", "/api/v1/catalog/categories/{id}", "Partial Category Update", "Admin Only", "{\"name\":\"Film-Noir\"}"),
                new EndpointInfo("GET", "/api/v1/admin/staff", "Admin Dash: List All Staff", "Admin Only"),
                new EndpointInfo("GET", "/api/v1/admin/staff/{id}", "Admin Dash: Get Staff by ID", "Admin Only"),
                new EndpointInfo("GET", "/api/v1/admin/staff/search", "Admin Dash: Search Staff", "Admin Only", "{\"name\":\"Staff\"}"),
                new EndpointInfo("POST", "/api/v1/admin/staff", "Admin Dash: Create New Staff", "Admin Only", "{\"firstName\":\"Admin\",\"lastName\":\"User\",\"email\":\"admin@store.com\",\"username\":\"admin.user\",\"password\":\"admin123\",\"storeId\":1,\"addressId\":1,\"role\":\"ADMIN\"}"),
                new EndpointInfo("PUT", "/api/v1/admin/staff/{id}", "Admin Dash: Update Staff", "Admin Only", "{\"firstName\":\"AdminUpdate\"}"),
                new EndpointInfo("DELETE", "/api/v1/admin/staff/{id}", "Admin Dash: Delete Staff", "Admin Only")
            )));

        // Saniya Kamble: Rental and Staff Module
        teamMembers.add(new TeamMember("Saniya Kamble", "Rental and Staff Module", "/images/saniya.png", "rental", 
            "Internal Operations and Inventory Management",
            Arrays.asList(
                new EndpointInfo("GET", "/api/v1/staff", "Retrieve All Staff Members", "Admin/Staff"),
                new EndpointInfo("GET", "/api/v1/staff/{id}", "Get Specific Staff Details", "Admin/Staff"),
                new EndpointInfo("POST", "/api/v1/staff", "Add New Staff to Store", "Admin/Staff", "{\"firstName\":\"Staff\",\"lastName\":\"Member\",\"email\":\"staff@store.com\",\"username\":\"staff.member\",\"password\":\"staff123\",\"storeId\":1,\"addressId\":1,\"role\":\"STAFF\"}"),
                new EndpointInfo("PUT", "/api/v1/staff/{id}", "Update Staff Information", "Admin/Staff", "{\"lastName\":\"Updated\"}"),
                new EndpointInfo("DELETE", "/api/v1/staff/{id}", "Terminate/Delete Staff Record", "Admin/Staff"),
                new EndpointInfo("POST", "/api/v1/rentals", "Process New Rental (Staff Side)", "Admin/Staff", "{\"customerId\":1,\"inventoryId\":1,\"staffId\":1}"),
                new EndpointInfo("GET", "/api/v1/rentals", "List All Store Rentals", "Admin/Staff"),
                new EndpointInfo("GET", "/api/v1/rentals/{id}", "Get Single Rental Record", "Admin/Staff"),
                new EndpointInfo("PUT", "/api/v1/rentals/{id}/return", "Process Recording Return", "Admin/Staff"),
                new EndpointInfo("DELETE", "/api/v1/rentals/{id}", "Remove Rental Record", "Admin Only"),
                new EndpointInfo("GET", "/api/v1/inventory", "View Complete Inventory", "Any"),
                new EndpointInfo("GET", "/api/v1/inventory/{id}", "Get Inventory Item Details", "Any"),
                new EndpointInfo("GET", "/api/v1/inventory/film/{id}", "List All Copies of a Film", "Any"),
                new EndpointInfo("GET", "/api/v1/inventory/store/{id}", "View Inventory by Store", "Any"),
                new EndpointInfo("GET", "/api/v1/inventory/available", "Find Available Copies", "Any", "{\"filmId\":1,\"storeId\":1}"),
                new EndpointInfo("POST", "/api/v1/inventory", "Add Film Copy to Store", "Admin/Staff", "{\"filmId\":1,\"storeId\":1}"),
                new EndpointInfo("DELETE", "/api/v1/inventory/{id}", "Remove Film Copy", "Admin/Staff")
            )));

        // Megha Muttha: Payment Module
        teamMembers.add(new TeamMember("Megha Muttha", "Payment Module", "/images/megha.png", "payment", 
            "Transaction Processing and Financial Reporting",
            Arrays.asList(
                new EndpointInfo("GET", "/api/v1/payments", "Get All System Payments", "Admin Only"),
                new EndpointInfo("GET", "/api/v1/payments/my", "Get Payments for Query-ID", "Admin/Customer", "{\"customerId\":1}"),
                new EndpointInfo("GET", "/api/v1/payments/{id}", "Get Payment by ID", "Admin/Customer"),
                new EndpointInfo("GET", "/api/v1/payments/customer/{customerId}", "Get Payments for Path-ID", "Admin/Customer"),
                new EndpointInfo("GET", "/api/v1/payments/rentals/{rentalId}", "Get Payment for Rental", "Admin/Customer"),
                new EndpointInfo("GET", "/api/v1/payments/balance/{customerId}", "Check Spending Balance", "Admin/Customer"),
                new EndpointInfo("POST", "/api/v1/payments", "Record New Payment", "Admin/Staff", "{\"rentalId\":1,\"amount\":4.99,\"paymentDate\":\"2024-04-16T12:00:00\"}")
            )));
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("team", teamMembers);
        model.addAttribute("backendUrl", backendUrl);
        return "index";
    }

    @GetMapping("/endpoints/{moduleId}")
    public String endpoints(@PathVariable String moduleId, Model model) {
        TeamMember member = teamMembers.stream()
                .filter(m -> m.getModuleId().equals(moduleId))
                .findFirst()
                .orElse(null);

        if (member == null) {
            return "redirect:/";
        }

        model.addAttribute("member", member);
        model.addAttribute("backendUrl", backendUrl);
        return "endpoints";
    }

    @PostMapping("/api-test/execute")
    public String executeTest(@ModelAttribute ApiTestRequest request, Model model) {
        HttpHeaders headers = new HttpHeaders();
        
        if (request.getToken() != null && !request.getToken().isEmpty()) {
            String token = request.getToken().trim();
            if (!token.startsWith("Bearer ")) {
                token = "Bearer " + token;
            }
            headers.set("Authorization", token);
        }
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            ResponseEntity<Object> response;
            String method = request.getMethod().toUpperCase();
            
            // Construct the base URI
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(backendUrl + request.getUrl());
            
            // Parse JSON String to Map
            Map<String, String> bodyMap = new HashMap<>();
            if (request.getBody() != null && !request.getBody().trim().isEmpty()) {
                try {
                    bodyMap = new ObjectMapper().readValue(request.getBody(), new TypeReference<Map<String, String>>() {});
                } catch (Exception e) {
                    model.addAttribute("error", "Invalid JSON body format: " + e.getMessage());
                    return "results";
                }
            }

            if ("GET".equals(method) && !bodyMap.isEmpty()) {
                // For GET requests, append parameters as query variables
                bodyMap.forEach(builder::queryParam);
            }
            URI uri = builder.build().toUri();
            switch (method) {
                case "GET":
                    response = backendClient.get(uri, headers);
                    break;
                case "POST":
                    response = backendClient.post(uri, headers, bodyMap);
                    break;
                case "PUT":
                    response = backendClient.put(uri, headers, bodyMap);
                    break;
                case "DELETE":
                    response = backendClient.delete(uri, headers);
                    break;
                case "PATCH":
                    response = backendClient.patch(uri, headers, bodyMap);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported method: " + method);
            }
            model.addAttribute("status", response.getStatusCode().value());
            model.addAttribute("data", response.getBody());
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            model.addAttribute("status", e.getStatusCode().value());
            model.addAttribute("error", e.getResponseBodyAsString());
        } catch (Exception e) {
            model.addAttribute("status", 500);
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("method", request.getMethod());
        model.addAttribute("url", request.getUrl());
        return "results";
    }
}

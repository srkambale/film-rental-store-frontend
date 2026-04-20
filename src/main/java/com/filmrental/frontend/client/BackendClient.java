package com.filmrental.frontend.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import java.net.URI;
import java.util.Map;

@Service
public class BackendClient {
    private final RestTemplate restTemplate;

    public BackendClient() {
        this.restTemplate = new RestTemplate();
        // Uses modern JDK factory supporting PATCH for Java 21/Spring 6
        this.restTemplate.setRequestFactory(new JdkClientHttpRequestFactory());
    }

    public ResponseEntity<Object> get(URI uri, HttpHeaders headers) {
        return restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), Object.class);
    }

    public ResponseEntity<Object> post(URI uri, HttpHeaders headers, Map<String, String> body) {
        return restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(body, headers), Object.class);
    }

    public ResponseEntity<Object> put(URI uri, HttpHeaders headers, Map<String, String> body) {
        return restTemplate.exchange(uri, HttpMethod.PUT, new HttpEntity<>(body, headers), Object.class);
    }

    public ResponseEntity<Object> delete(URI uri, HttpHeaders headers) {
        return restTemplate.exchange(uri, HttpMethod.DELETE, new HttpEntity<>(headers), Object.class);
    }

    public ResponseEntity<Object> patch(URI uri, HttpHeaders headers, Map<String, String> body) {
        return restTemplate.exchange(uri, HttpMethod.PATCH, new HttpEntity<>(body, headers), Object.class);
    }
}

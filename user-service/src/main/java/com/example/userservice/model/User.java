package com.example.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

@Document(collection = "users")
public class User {

    @Schema(description = "User ID", example = "1")
    @Id
    private String id;

    @Schema(description = "User email", example = "maria@example.com")
    private String email;

    @Schema(description = "User password", example = "strongPassword123")
    private String password;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}

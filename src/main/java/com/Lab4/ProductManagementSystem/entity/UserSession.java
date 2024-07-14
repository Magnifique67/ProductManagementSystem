package com.Lab4.ProductManagementSystem.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "user_sessions")
@Data
public class UserSession {
    @Id
    private String sessionId;
    private Long userId;
    private List<String> activities;
    private LocalDateTime timestamp;
}

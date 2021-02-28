package com.finalproject.shelter.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiRequest {

    private Long id;

    private String userId;

    private String password;

    private String name;

    private LocalDateTime lastLoginAt;

    private int loginFailCount; // integer의 용량 이 int보다 큼 바꿀라면 나중에 바꾸자

    private LocalDateTime createdAt;

    private LocalDateTime uncreatedAt;

    private LocalDateTime updatedAt;

    private String updatedBy;
}

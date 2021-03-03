package com.finalproject.shelter.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserApiResponse {

    private Long id;

    private String name;

    private String nickname;

    private String password;

    private LocalDateTime lastLoginAt;

    private LocalDateTime createdAt;

    private LocalDateTime uncreatedAt;

    private LocalDateTime updatedAt;

    private String updatedBy;

}

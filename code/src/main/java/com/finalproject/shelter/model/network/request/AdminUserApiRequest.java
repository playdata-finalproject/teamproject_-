package com.finalproject.shelter.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminUserApiRequest {

    private Long id;

    private String name;

    private String nickname;

    private String password;

    private LocalDateTime lastLoginAt;

    private LocalDate createdAt;

    private LocalDate uncreatedAt;

    private LocalDate updatedAt;

    private String updatedBy;

}

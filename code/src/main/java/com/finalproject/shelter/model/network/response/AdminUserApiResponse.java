package com.finalproject.shelter.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    private LocalDate createdAt;

    private LocalDate uncreatedAt;

    private LocalDate updatedAt;

    private String updatedBy;

}

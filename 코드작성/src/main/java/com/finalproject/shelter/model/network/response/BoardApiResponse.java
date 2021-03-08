package com.finalproject.shelter.model.network.response;

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
public class BoardApiResponse {

    private Long id;

    private String title;

    private String nickname;

    private String contents;

    private int view;

    private int goodBoard;

    private int hateBoard;

    private LocalDate registeredAt;

    private LocalDate unregisteredAt;

    private LocalDate updatedAt;

    private String updatedBy;
}

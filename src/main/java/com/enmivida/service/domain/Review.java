package com.enmivida.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Review {
    private String reviewId;
    private Long movieInfoId;
    private String comment;
    private Double rating;
}

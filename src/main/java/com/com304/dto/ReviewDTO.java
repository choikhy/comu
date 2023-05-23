package com.com304.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    private long rvno;

    @NotEmpty
    private String review;

    private String writer;

    private long fno;

    private long rating;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}

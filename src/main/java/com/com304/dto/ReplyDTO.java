package com.com304.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.com304.entity.Board;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private long rno;

    @NotEmpty
    private String reply;

    private String writer;

    private long bno;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}

package com.com304.dto;

import com.com304.entity.ImgBoard;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImgBoardDTO {

    private long bno;
    @NotEmpty
    @Size(min = 3, max = 100)
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String writer;
    private Long writerId;
    private Long view;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private List itemImgDtoList = new ArrayList();

    private static ModelMapper modelMapper = new ModelMapper();

    public ImgBoard create(){
        return modelMapper.map(this, ImgBoard.class);
    }
}

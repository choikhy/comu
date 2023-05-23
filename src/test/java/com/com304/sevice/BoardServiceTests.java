package com.com304.sevice;

import com.com304.dto.BoardDTO;
import com.com304.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void dummies() {

        for (int i = 0; i < 100; i++) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .title("제목.." + i)
                    .content("내용.." + i)
                    .writer("작성자.." + i)
                    .build();

            boardService.register(boardDTO);
        }
    }
}

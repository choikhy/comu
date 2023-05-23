package com.com304.service;

import com.com304.dto.BoardDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;

public interface BoardService {
    Long register(BoardDTO boardDTO);

    BoardDTO readOne(Long bno);

    void modify(BoardDTO boardDTO);

    void remove(Long bno);

    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);





    }




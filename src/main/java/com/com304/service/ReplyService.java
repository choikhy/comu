package com.com304.service;

import com.com304.dto.ReplyDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;

public interface ReplyService {
    Long register(ReplyDTO replyDTO);
    

    void modify(ReplyDTO replyDTO);

    void remove(Long rno);

    PageResponseDTO<ReplyDTO> list(PageRequestDTO pageRequestDTO, Long bno);





    }




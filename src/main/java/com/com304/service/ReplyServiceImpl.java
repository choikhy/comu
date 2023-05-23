package com.com304.service;

import com.com304.dto.BoardDTO;
import com.com304.entity.Board;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.com304.dto.ReplyDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;
import com.com304.entity.Reply;
import com.com304.repository.ReplyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {

    private final ModelMapper modelMapper;

    private  final ReplyRepository replyRepository;

    @Override
    public Long register(ReplyDTO replyDTO){
        Reply reply = modelMapper.map(replyDTO, Reply.class);

        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public void modify(ReplyDTO replyDTO) {

    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> list(PageRequestDTO pageRequestDTO, Long bno) {
            Pageable pageable = pageRequestDTO.getPageable("bno");

            Page<Reply> result = replyRepository.searchAll(pageable, bno);

            List<ReplyDTO> dtoList = result.getContent().stream()
                    .map(reply -> modelMapper.map(reply,ReplyDTO.class)).collect(Collectors.toList());


            return PageResponseDTO.<ReplyDTO>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)result.getTotalElements())
                    .build();

    }


}

package com.com304.service;

import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;
import com.com304.dto.ReplyDTO;
import com.com304.dto.ReviewDTO;
import com.com304.entity.Reply;
import com.com304.entity.Review;
import com.com304.repository.ReplyRepository;
import com.com304.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ReviewService{

    private final ModelMapper modelMapper;

    private  final ReviewRepository reviewRepository;

    public Long register(ReviewDTO reviewDTO){

        Review review = modelMapper.map(reviewDTO, Review.class);

        Long rvno = reviewRepository.save(review).getRvno();

        return rvno;
    }

    public void remove(Long rvno) {
        reviewRepository.deleteById(rvno);
    }

    public PageResponseDTO<ReviewDTO> list(PageRequestDTO pageRequestDTO, Long fno) {
            Pageable pageable = pageRequestDTO.getPageable("fno");

            Page<Review> result = reviewRepository.searchAll(pageable, fno);

            List<ReviewDTO> dtoList = result.getContent().stream()
                    .map(review -> modelMapper.map(review,ReviewDTO.class)).collect(Collectors.toList());


            return PageResponseDTO.<ReviewDTO>withAll()
                    .pageRequestDTO(pageRequestDTO)
                    .dtoList(dtoList)
                    .total((int)result.getTotalElements())
                    .build();

    }


}

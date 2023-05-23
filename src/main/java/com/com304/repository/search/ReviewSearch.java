package com.com304.repository.search;

import com.com304.entity.Reply;
import com.com304.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewSearch {

    Page<Review> search1(Pageable pageable);

    Page<Review> searchAll(Pageable pageable, Long fno);


}



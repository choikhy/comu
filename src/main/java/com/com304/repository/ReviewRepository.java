package com.com304.repository;


import com.com304.entity.Reply;
import com.com304.entity.Review;
import com.com304.repository.search.ReplySearch;
import com.com304.repository.search.ReviewSearch;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewSearch {
    Review findByfno(Long fno);

}

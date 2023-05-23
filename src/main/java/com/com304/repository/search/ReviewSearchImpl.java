package com.com304.repository.search;

import com.com304.entity.QReply;
import com.com304.entity.QReview;
import com.com304.entity.Reply;
import com.com304.entity.Review;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReviewSearchImpl extends QuerydslRepositorySupport implements ReviewSearch {

    public ReviewSearchImpl(){
        super((Review.class));
    }

    @Override
    public Page<Review> search1(Pageable pageable){

        QReview review = QReview.review1; //Q도메인 객체

        JPQLQuery<Review> query = from(review); // select.. from reply

        this.getQuerydsl().applyPagination(pageable, query);

        List<Review> list = query.fetch();

        long count = query.fetchCount();


        return null;
    }

    @Override
    public Page<Review> searchAll(Pageable pageable, Long fno) {

        QReview review = QReview.review1;
        JPQLQuery<Review> query = from(review);

        query.where(review.fno.eq(fno));
        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Review> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }


}

package com.com304.repository.search;

import com.com304.entity.Reply;
import com.com304.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReplySearchImpl extends QuerydslRepositorySupport implements ReplySearch {

    public ReplySearchImpl(){
        super((Reply.class));
    }

    @Override
    public Page<Reply> search1(Pageable pageable){

        QReply reply = QReply.reply1; //Q도메인 객체

        JPQLQuery<Reply> query = from(reply); // select.. from reply

        this.getQuerydsl().applyPagination(pageable, query);

        List<Reply> list = query.fetch();

        long count = query.fetchCount();


        return null;
    }

    @Override
    public Page<Reply> searchAll(Pageable pageable, Long bno) {

        QReply reply = QReply.reply1;
        JPQLQuery<Reply> query = from(reply);

        query.where(reply.bno.eq(bno));
        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Reply> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }


}

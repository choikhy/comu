package com.com304.repository.search;

import com.com304.entity.Board;
import com.com304.entity.ImgBoard;
import com.com304.entity.QBoard;
import com.com304.entity.QImgBoard;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ImgBoardSearchImpl extends QuerydslRepositorySupport implements ImgBoardSearch {

    public ImgBoardSearchImpl(){
        super((ImgBoard.class));
    }

    @Override
    public Page<ImgBoard> search1(Pageable pageable){

        QImgBoard imgBoard = QImgBoard.imgBoard; //Q도메인 객체

        JPQLQuery<ImgBoard> query = from(imgBoard); // select.. from board

        query.where(imgBoard.title.contains("1")); // where title like ...

        this.getQuerydsl().applyPagination(pageable, query);

        List<ImgBoard> list = query.fetch();

        long count = query.fetchCount();


        return null;
    }

    @Override
    public Page<ImgBoard> searchAll(String[] types, String keyword, Pageable pageable) {

        QImgBoard imgBoard = QImgBoard.imgBoard;
        JPQLQuery<ImgBoard> query = from(imgBoard);

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(imgBoard.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(imgBoard.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(imgBoard.writer.contains(keyword));
                        break;
                }
            }//end for
                query.where(booleanBuilder);
        }//end if

        //bno>0
        query.where(imgBoard.bno.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<ImgBoard> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }


}

package com.com304.repository.search;

import com.com304.entity.Board;
import com.com304.entity.ImgBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImgBoardSearch {

    Page<ImgBoard> search1(Pageable pageable);

    Page<ImgBoard> searchAll(String[] types, String keyword, Pageable pageable);


}



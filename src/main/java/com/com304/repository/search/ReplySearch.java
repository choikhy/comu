package com.com304.repository.search;

import com.com304.entity.Reply;
import com.com304.entity.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReplySearch {

    Page<Reply> search1(Pageable pageable);

    Page<Reply> searchAll(Pageable pageable, Long bno);


}



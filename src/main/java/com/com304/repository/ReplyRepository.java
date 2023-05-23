package com.com304.repository;


import com.com304.repository.search.ReplySearch;
import org.springframework.data.jpa.repository.JpaRepository;

import com.com304.entity.Reply;


public interface ReplyRepository extends JpaRepository<Reply, Long>, ReplySearch {
    Reply findByBno(Long bno);

}

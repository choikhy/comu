package com.com304.repository;

import com.com304.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findById(String id);

    Member findByIdNo(Long idno);
    Member findByName(String name);
}

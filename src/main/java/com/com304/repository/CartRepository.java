package com.com304.repository;

import com.com304.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteByMeno(Long meno);

    List<Cart> findByMemberId(String memberId);
}

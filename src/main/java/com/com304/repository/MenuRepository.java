package com.com304.repository;

import com.com304.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByFno(long fno);

    Menu findByMeNo(long meno);

}

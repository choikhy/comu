package com.com304.repository;

import com.com304.entity.ImgBoard;
import com.com304.repository.search.ImgBoardSearch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImgBoardRepository extends JpaRepository<ImgBoard, Long>, ImgBoardSearch {
}

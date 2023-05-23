package com.com304.controller;

import com.com304.dto.BoardDTO;
import com.com304.dto.ImgBoardDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;
import com.com304.service.BoardService;
import com.com304.service.ImgBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class MainController {

    private final BoardService boardService;
    private final ImgBoardService imgBoardService;

    @GetMapping("/")
    public String index(PageRequestDTO pageRequestDTO, Model model){
        pageRequestDTO.setSize(6);
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        System.out.println(responseDTO.getDtoList());
        pageRequestDTO.setSize(3);
        PageResponseDTO<ImgBoardDTO> imgResponseDTO = imgBoardService.list(pageRequestDTO);
        System.out.println(imgResponseDTO.getDtoList());

        model.addAttribute("responseDTO", responseDTO);
        model.addAttribute("imgResponseDTO", imgResponseDTO);

        return "index";
    }
}

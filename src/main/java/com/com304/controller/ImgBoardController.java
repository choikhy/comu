package com.com304.controller;

import com.com304.dto.*;
import com.com304.entity.Member;
import com.com304.repository.MemberRepository;
import com.com304.service.ImgBoardService;
import com.com304.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/imgboard")
public class ImgBoardController {

    private final ImgBoardService imgBoardService;

    private final MemberRepository memberRepository;

    private final ReplyService replyService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        PageResponseDTO<ImgBoardDTO> responseDTO = imgBoardService.list(pageRequestDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    @GetMapping(value = "/register")
    public String itemForm(Model model, Principal principal){
        model.addAttribute("imgBoardDTO", new ImgBoardDTO());
        try {
            Member member = memberRepository.findById(principal.getName());
            model.addAttribute("writer", member.getName());
        } catch (Exception e){
            return "/member/login";
        }
        return "imgboard/register";
    }

    @PostMapping(value = "/register")
    public String itemNew(@Valid ImgBoardDTO imgBoardDTO, BindingResult bindingResult,
                          Model model, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList){
        if(bindingResult.hasErrors()){
            return "imgboard/register";
        }

        if(itemImgFileList.get(0).isEmpty() && imgBoardDTO.getOriImgName() == null){
            model.addAttribute("errorMessage", "이미지는 필수 입력 값 입니다.");
            return "imgboard/register";
        }

        try {
            imgBoardService.register(imgBoardDTO, itemImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "게시물 등록 중 에러가 발생하였습니다.");
            return "imgboard/list";
        }

        return "redirect:/imgboard/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model, Principal principal){

        ImgBoardDTO imgBoardDTO = imgBoardService.readOne(bno);
        PageResponseDTO<ReplyDTO> replyDTO = replyService.list(pageRequestDTO, bno);

        if(principal != null){
            Member sessionUser = memberRepository.findById(principal.getName());
            model.addAttribute("sessionUser", sessionUser.getName());
            model.addAttribute("sessionRole", sessionUser.getRole().toString());
        }

        model.addAttribute("dto", imgBoardDTO);
        model.addAttribute("replyDTO", replyDTO);
    }

    @PostMapping("/modify")
    public String modify( PageRequestDTO pageRequestDTO,
                          @Valid ImgBoardDTO imgBoardDTO,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {

        if(bindingResult.hasErrors()){

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", imgBoardDTO.getBno());

            return "redirect:/imgboard/modify?"+link;
        }

        imgBoardService.modify(imgBoardDTO, itemImgFileList);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", imgBoardDTO.getBno());

        return "redirect:/imgboard/read";

    }

    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes ){

        imgBoardService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/imgboard/list";

    }
}

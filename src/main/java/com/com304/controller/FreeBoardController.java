package com.com304.controller;

import com.com304.dto.ReplyDTO;
import com.com304.entity.Member;
import com.com304.entity.Reply;
import com.com304.repository.MemberRepository;
import com.com304.service.MemberService;
import com.com304.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.com304.dto.BoardDTO;
import com.com304.dto.PageRequestDTO;
import com.com304.dto.PageResponseDTO;
import com.com304.service.BoardService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/freeboard")
@Log4j2
@RequiredArgsConstructor
public class FreeBoardController {
    private final BoardService boardService;
    private final ReplyService replyService;
    private final MemberRepository memberRepository;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }


    @GetMapping("/register")
    public String registerGET(Principal principal, Model model) {
        try {
            Member member = memberRepository.findById(principal.getName());
            model.addAttribute("writer", member.getName());
        } catch (Exception e) {
            return "/member/login";
        }
        return "/freeboard/register";
    }

    @PostMapping("/register")
    public String registerPost(@Valid BoardDTO boardDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("board POST register,,,,,,,,");

        if (bindingResult.hasErrors()) {
            log.info("has errors....... ");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/freeboard/register";
        }

        log.info(boardDTO);

        Long bno = boardService.register(boardDTO);

        redirectAttributes.addFlashAttribute("result", bno);

        return "redirect:/freeboard/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long bno, PageRequestDTO pageRequestDTO, Model model, Principal principal) {

        BoardDTO boardDTO = boardService.readOne(bno);
        PageResponseDTO<ReplyDTO> replyDTO = replyService.list(pageRequestDTO, bno);

        if (principal != null) {
            Member sessionUser = memberRepository.findById(principal.getName());
            model.addAttribute("sessionUser", sessionUser.getName());
            model.addAttribute("sessionRole", sessionUser.getRole().toString());
        }

        model.addAttribute("dto", boardDTO);
        model.addAttribute("replyDTO", replyDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid BoardDTO boardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        log.info("board modify post......." + boardDTO);

        if (bindingResult.hasErrors()) {
            log.info("has errors...........");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("bno", boardDTO.getBno());

            return "redirect:/freeboard/modify?" + link;
        }

        boardService.modify(boardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("bno", boardDTO.getBno());

        return "redirect:/freeboard/read";

    }


    @PostMapping("/remove")
    public String remove(Long bno, RedirectAttributes redirectAttributes) {

        log.info("remove post...." + bno);

        boardService.remove(bno);

        redirectAttributes.addFlashAttribute("result", "remove");

        return "redirect:/freeboard/list";

    }

    @PostMapping("/reply")
    public String reply(ReplyDTO reply, Principal principal) {
        Member member = memberRepository.findById(principal.getName());
        reply.setWriter(member.getName());
        replyService.register(reply);

        return "redirect:/freeboard/read?bno=" + reply.getBno();
    }

    @PostMapping("/delReply")
    public String delReply(Long rno, HttpServletRequest request){

        replyService.remove(rno);

        return "redirect:" + request.getHeader("Referer");
    }


}

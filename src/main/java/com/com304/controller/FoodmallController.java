package com.com304.controller;

import com.com304.dto.*;
import com.com304.entity.*;
import com.com304.repository.MemberRepository;
import com.com304.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RequestMapping("/foodmall")
@Controller
public class FoodmallController {

    private final FoodService foodService;
    private final MemberRepository memberRepository;
    private final MenuService menuService;
    private final ReviewService reviewService;
    private final CartService cartService;

    @GetMapping("/main")
    public void main(Principal principal, Model model){
        if(principal != null){
            Member member = memberRepository.findById(principal.getName());

            model.addAttribute("member", member);
        }
    }

    @GetMapping("/list")
    public void list(String cate, Model model){

        List<Food> food = foodService.getList(cate);

        model.addAttribute("foodlist", food);
    }

    @GetMapping("/post")
    public String post(Model model, Principal principal){
        model.addAttribute("foodDto", new FoodDTO());
        try {
            Member member = memberRepository.findById(principal.getName());
            model.addAttribute("writer", member.getName());
        } catch (Exception e){
            return "/member/login";
        }
        return "/foodmall/post";
    }

    @PostMapping("/post")
    public String posting(Model model, FoodDTO foodDTO, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "foodmall/post";
        }

        if(itemImgFileList.get(0).isEmpty() && foodDTO.getOriImgName() == null){
            model.addAttribute("errorMessage", "이미지는 필수 입력 값 입니다.");
            return "foodmall/post";
        }

        try {
            foodService.register(foodDTO, itemImgFileList);
        } catch (Exception e){
            System.out.println(e);
            model.addAttribute("errorMessage", "게시물 등록 중 에러가 발생하였습니다.");
            return "foodmall/post";
        }

        return "redirect:/foodmall/main";
    }

    @GetMapping("/restaurant")
    public String restaurant(Long fno, Model model, Principal principal, PageRequestDTO pageRequestDTO){

        if (principal == null){
            return "/member/login";
        }

        if (principal != null) {
            Member sessionUser = memberRepository.findById(principal.getName());
            model.addAttribute("sessionUser", sessionUser.getName());
            model.addAttribute("sessionRole", sessionUser.getRole().toString());
        }

        FoodDTO food = foodService.readOne(fno);

        PageResponseDTO<ReviewDTO> rev = reviewService.list(pageRequestDTO, fno);

        List<Menu> menu = menuService.list(fno);

        System.out.println("name ==="  + principal.getName());

        Member mem = memberRepository.findById(principal.getName());

        model.addAttribute("rest", food);
        model.addAttribute("member", mem);
        model.addAttribute("review", rev);
        model.addAttribute("menu", menu);

        return "/foodmall/restaurant";
    }

    @PostMapping("/postmenu")
    public String postMenu(Menu menu, @RequestParam("itemImgFile") List<MultipartFile> itemImgFileList) throws Exception {

        menuService.register(menu, itemImgFileList);

        return "redirect:/foodmall/restaurant?fno=" + menu.getFno();
    }

    @GetMapping("/postmenu")
    public void postmenu(){

    }

    @PostMapping("/review")
    public String review(ReviewDTO reviewDTO){

        reviewService.register(reviewDTO);

        return "redirect:/foodmall/restaurant?fno=" + reviewDTO.getFno();
    }

    @GetMapping("/insertcart")
    public String insert(long meno, long fno, Principal principal){
        Cart cart = new Cart();
        cart.setMeno(meno);
        cart.setMemberId(principal.getName());

        cartService.insert(cart);
        return "redirect:/foodmall/restaurant?fno=" + fno;
    }

    @GetMapping("/mycart")
    public void mycart(Principal principal, Model model){
        List<Menu> menu = cartService.cartList(principal.getName());

        model.addAttribute("menu", menu);
    }
    @GetMapping("/dropcart")
    public String dropCart(long meno, HttpServletRequest request){
        cartService.delete(meno);

        if (request.getHeader("Referer") != null) {
            return "redirect:" + request.getHeader("Referer");
        } else {
            return "/foodmall/main";
        }

    }

}

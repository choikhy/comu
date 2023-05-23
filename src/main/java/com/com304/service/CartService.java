package com.com304.service;

import com.com304.entity.Cart;
import com.com304.entity.Member;
import com.com304.entity.Menu;
import com.com304.repository.CartRepository;
import com.com304.repository.MemberRepository;
import com.com304.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final MenuRepository menuRepository;

    public void insert(Cart cart) {
        cartRepository.save(cart);
    }

    public void delete(long meno) {
        cartRepository.deleteByMeno(meno);
    }

    public List<Menu> cartList(String memberId){
        List<Cart> cart = cartRepository.findByMemberId(memberId);

        List<Menu> menu = new ArrayList<>();

        for(int i = 0; i < cart.size(); i++){
            Menu menu1 = menuRepository.findByMeNo(cart.get(i).getMeno());
            System.out.println(menu1.getName());
            menu.add(menu1);
        }

        return menu;
    }

}

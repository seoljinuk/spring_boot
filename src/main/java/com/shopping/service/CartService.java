package com.shopping.service;

import com.shopping.dto.CartItemDto;
import com.shopping.entity.Cart;
import com.shopping.entity.CartItem;
import com.shopping.entity.Item;
import com.shopping.entity.Member;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.CartRepository;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

// @RequiredArgsConstructor를 작성해 두면, 생성자를 통하여 해당 변수에 값을 주입해줍니다.
// @Autowired는 주로 변수에 작성하는 데, setter을 이용하여 주입해 줍니다.
@Service
@RequiredArgsConstructor // 생성자(Constructor)를 통하여 필수 입력(Required)해야 하는 매개 변수(Args)
public class CartService {
    private final ItemRepository itemRepository ;
    private final MemberRepository memberRepository ;
    private final CartRepository cartRepository ;
    private final CartItemRepository cartItemRepository ;

    // 장바구니에 담을 정보와 이메일을 이용하여 카트에 추가합니다.
    public Long addCart(CartItemDto cartItemDto, String email){
        Long itemId = cartItemDto.getItemId() ;

        // 장바구니에 담고자 하는 상품
        Item item = itemRepository.findById(itemId)
                .orElseThrow(EntityNotFoundException::new) ;

        // 로그인 한 사람의 정보
        Member member = memberRepository.findByEmail(email) ;

        // 로그인 한 사람의 장바구니 정보
        Cart cart = cartRepository.findByMemberId(member.getId()) ;

        if(cart == null){ // 장바구니가 준비 않된 경우
            cart = Cart.createCart(member) ;
            cartRepository.save(cart) ;
        }

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId()) ;

        if(savedCartItem != null){ // 해당 상품이 나의 카트에 이미 들어 있는 경우
            // 이미 담긴 상품에 대하여 수량을 누적합니다.
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();

        }else{ // 신규 상품을 장바구니에 담고자 하는 경우
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount()) ;
            cartItemRepository.save(cartItem) ;
            return cartItem.getId();
        }
    }
}

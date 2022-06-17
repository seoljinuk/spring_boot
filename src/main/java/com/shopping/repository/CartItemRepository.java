package com.shopping.repository;

import com.shopping.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 카드 정보와 상품 정보를 이용하여 특정 장바구니에 상품을 조회합니다.
    CartItem findByCartIdAndItemId(Long cartId, Long itemId) ;
}

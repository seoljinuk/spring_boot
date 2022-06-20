package com.shopping.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Getter
@Setter
@ToString
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_item_id")
    private Long id ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item ;

    private int count ; // 주문 수량 갯수

    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem() ;
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem ;
    }

    public void addCount(int count){
        this.count += count ;
    }

    // 장바구니 내역 보기에서 사용자가 상품 수량을 변경할 때 사용되는 메소드입니다.
    public void updateCount(int count){
        this.count = count ; // 기존 수량 무시하고 덮어 쓰겠습니다.
        // this.count += count ; // 기존 수량에 누적하겠습니다.
    }
}

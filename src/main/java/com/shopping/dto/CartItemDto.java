package com.shopping.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

// 상품 상세 페이지에서 상품의 id와 구매 수량을 전달 받는 클래스
@Getter @Setter
public class CartItemDto {
    @NotNull(message = "상품 아이디는 필수 입력 값입니다.")
    private Long itemId ;

    @Min(value = 1, message = "최소 1개 이상 담아 주세요.")
    private int count ;
}

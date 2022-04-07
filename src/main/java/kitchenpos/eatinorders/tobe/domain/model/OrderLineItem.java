package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.dto.MenuResponse;
import kitchenpos.global.domain.vo.Price;

import java.util.UUID;

public class OrderLineItem {

    private Long sequence;
    private Price price;
    private long quantity;

    private UUID menuId;


    public OrderLineItem(MenuResponse menu, Price price, long quantity) {
        validate(menu, price);
        this.menuId = menu.getId();
        this.price = price;
        this.quantity = quantity;
    }

    private void validate(MenuResponse menu, Price other) {
        if (!menu.isDisplayed()) {
            throw new IllegalArgumentException("현재 판매중인 상품이 아닙니다.");
        }
        if (!menu.getPrice().equals(other)) {
            throw new IllegalArgumentException("진열된 메뉴의 가격과 현재 메뉴의 가격이 일치하지 않습니다.");
        }
    }

}

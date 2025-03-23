package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

/**
 * EatInOrder 생성자에서 EatInOrderMenu 를 통해 메뉴에 대한 유효성 검사를 진행함
 * 메뉴 존재 여부
 * 메뉴 표시 여부
 * 메뉴 가격 동일시 여부
 */
public interface EatInOrderMenu {

    boolean exists(final UUID menuID);
    boolean isDisplayed(final UUID menuId);
    boolean isSamePrice(final UUID menuId, final int price);
}

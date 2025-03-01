package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

/**
 * 현재 EatInOrder 생성자에서 EatInOrderMenus 를 필수적으로 입력받아서 아래와 같은 불변식을 도메인 엔티티 레벨에서 처리중
 * 1. 메뉴 사이즈가 같아야한다.
 * 2. 메뉴는 노출 되어야 한다.
 * 3. 메뉴의 가격이 같아야 한다.
 */
public interface EatInOrderMenus {
    boolean isSameSize(int size);

    boolean isDisplayed(UUID menuId);

    boolean isSamePrice(UUID menuId, int menuPrice);
}

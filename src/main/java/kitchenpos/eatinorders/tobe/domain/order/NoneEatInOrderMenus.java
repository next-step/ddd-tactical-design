package kitchenpos.eatinorders.tobe.domain.order;

import java.util.UUID;

/**
 * 현재 EatInOrder 생성자에서 EatInOrderMenus 를 필수적으로 입력받아서 아래와 같은 불변식을 도메인 엔티티 레벨에서 처리중
 * 1. 메뉴 사이즈가 같아야한다.
 * 2. 메뉴는 노출 되어야 한다.
 * 3. 메뉴의 가격이 같아야 한다.
 *
 * SQL 쿼리를 통해 EatInOrder 를 생성하는 케이스에도, EatInOrderMenus가 필요함
 * 그러나 해당 시점에는 메뉴가 없으므로 불변식을 무조건 통과하도록 만든 NoneEatInOrderMenus 클래스
 */
public class NoneEatInOrderMenus implements EatInOrderMenus {
    @Override
    public boolean isSameSize(final int size) {
        return true;
    }

    @Override
    public boolean isDisplayed(final UUID menuId) {
        return true;
    }

    @Override
    public boolean isSamePrice(final UUID menuId, final int menuPrice) {
        return true;
    }
}

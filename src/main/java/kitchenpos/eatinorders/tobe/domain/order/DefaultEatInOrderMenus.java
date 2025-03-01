package kitchenpos.eatinorders.tobe.domain.order;

import java.util.List;
import java.util.UUID;

/**
 * Menu Bounded Context 에서 조회한 Menu 정보
 * 질문 : Aggregate 로 판단? Data 로 판단? Value Object 로 판단?
 * 질문 이유 :
 * 1. Menu 이긴 하지만, EatInOrder 생성시에 불변식 기준으로 제공하고 있음
 *   1-1. EatInOrder 에서 바라보는 Menu 는 EatInOrderItem 으로 대응 중
 *   1-2. hash code, equals, toString 구현 기준 고민
 */
public class DefaultEatInOrderMenus implements EatInOrderMenus {
    private final List<EatInOrderMenu> defaultEatInOrderMenus;

    public DefaultEatInOrderMenus(final EatInOrderMenu... defaultEatInOrderMenus) {
        this(List.of(defaultEatInOrderMenus));
    }

    public DefaultEatInOrderMenus(final List<EatInOrderMenu> defaultEatInOrderMenus) {
        this.defaultEatInOrderMenus = defaultEatInOrderMenus;
    }

    public boolean isSameSize(final int size) {
        return defaultEatInOrderMenus.size() == size;
    }

    public boolean isDisplayed(final UUID menuId) {
        return defaultEatInOrderMenus.stream()
                .filter(defaultEatInOrderMenu -> defaultEatInOrderMenu.isSameMenu(menuId))
                .findFirst()
                .map(EatInOrderMenu::isDisplayed)
                .orElseThrow(IllegalArgumentException::new);
    }

    public boolean isSamePrice(final UUID menuId, final int price) {
        return defaultEatInOrderMenus.stream()
                .filter(defaultEatInOrderMenu -> defaultEatInOrderMenu.isSameMenu(menuId))
                .findFirst()
                .map(defaultEatInOrderMenu -> defaultEatInOrderMenu.isSamePrice(price))
                .orElseThrow(IllegalArgumentException::new);
    }
}

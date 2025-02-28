package kitchenpos.eatinorders.tobe.domain;

import java.util.List;

public class EatInOrderMenus {
    private final List<EatInOrderMenu> eatInOrderMenus;

    public EatInOrderMenus(final List<EatInOrderMenu> eatInOrderMenus) {
        this.eatInOrderMenus = eatInOrderMenus;
    }

    public void verifySameSize(final EatInOrder eatInOrder) {
        if (eatInOrderMenus.size() != eatInOrder.size()) {
            throw new IllegalArgumentException();
        }
    }
}

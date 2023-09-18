package kitchenpos.menu.domain.menu;

import kitchenpos.support.vo.Name;

public interface MenuNameFactory {

    /**
     * @throws IllegalArgumentException nameCandidate에 비속어가 포함되어 있을 때
     */
    MenuName create(final Name nameCandidate);
}

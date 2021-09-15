package kitchenpos.menus.domain.tobe.domain.menu;

import java.util.NoSuchElementException;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.menus.domain.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuValidator {

    private final MenuGroupRepository menuGroupRepository;

    public MenuValidator(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public void validate(final MenuGroupId menuGroupId) {
        menuGroupRepository.findById(menuGroupId)
            .orElseThrow(() -> new NoSuchElementException("메뉴는 특정 메뉴 그룹에 속해야 합니다."));
    }
}

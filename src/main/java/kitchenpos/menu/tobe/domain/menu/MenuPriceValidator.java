package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.exception.MenuException;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static kitchenpos.common.exception.ErrorCode.*;


@Component
public class MenuPriceValidator implements MenuValidator {
    private final MenuGroupRepository menuGroupRepository;

    public MenuPriceValidator(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    @Override
    public void validateMenuGroup(UUID menuGroupId) {
        menuGroupRepository.findById(menuGroupId)
                .orElseThrow(() -> new MenuException(MENU_GROUP_NOT_FOUND));
    }
}

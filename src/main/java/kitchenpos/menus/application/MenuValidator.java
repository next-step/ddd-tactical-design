package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.request.MenuProductCreateRequest;
import kitchenpos.menus.domain.MenuGroupRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Component
public class MenuValidator {
    private final MenuGroupRepository menuGroupRepository;

    public MenuValidator(MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public void validateMenuGroup(UUID menuGroupId) {
        menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    public void validateMenuProduct(List<MenuProductCreateRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
    }
}

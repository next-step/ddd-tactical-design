package kitchenpos.menus.application.tobe;

import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.MenuId;
import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.domain.tobe.domain.menu.Menu;
import kitchenpos.menus.domain.tobe.domain.menu.MenuProducts;
import kitchenpos.menus.domain.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.domain.tobe.domain.menu.MenuValidator;
import kitchenpos.menus.ui.tobe.MenuCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {

    private final MenuValidator menuValidator;
    private final MenuRepository menuRepository;
    private final Profanities profanities;

    public MenuService(
        final MenuValidator menuValidator,
        final MenuRepository menuRepository,
        final Profanities profanities
    ) {
        this.menuValidator = menuValidator;
        this.menuRepository = menuRepository;
        this.profanities = profanities;
    }

    @Transactional
    public void register(final MenuCreateRequest request) {
        menuValidator.validateMenuGroup(request.getMenuGroupId());
        menuValidator.validateProducts(request.getProductIds());

        final Menu newMenu = new Menu(
            new MenuId(UUID.randomUUID()),
            new DisplayedName(request.getDisplayedName(), profanities),
            request.getPrice(),
            request.getMenuGroupId(),
            request.isDisplayed(),
            new MenuProducts(request.getMenuProducts())
        );

        menuRepository.save(newMenu);
    }

    @Transactional
    public void changePrice() {
        // TODO
    }

    @Transactional
    public void display() {
        // TODO
    }

    @Transactional
    public void hide() {
        // TODO
    }

    @Transactional(readOnly = true)
    public void findAll() {
        // TODO
    }
}

package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.menus.tobe.ui.dto.MenuChangePriceRequest;
import kitchenpos.menus.tobe.ui.dto.MenuCreateRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuCreateValidator menuCreateValidator;

    public MenuService(final MenuRepository menuRepository, final MenuCreateValidator menuCreateValidator) {
        this.menuRepository = menuRepository;
        this.menuCreateValidator = menuCreateValidator;
    }

    @Transactional
    public Menu create(final MenuCreateRequest request) {
        final Menu menu = menuCreateValidator.validate(request);
        return menuRepository.save(menu);
    }

    @Transactional
    public Menu changePrice(final UUID menuId, final MenuChangePriceRequest request) {
        final Menu menu = findById(menuId);
        menu.changePrice(request.price());
        return menu;
    }

    @Transactional
    public void checkAllHideByProductId(final UUID productId) {
        final List<Menu> menu = findAllByProductId(productId);
        menu.forEach(Menu::checkHide);
    }

    @Transactional
    public Menu display(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.display();
        return menu;
    }

    @Transactional
    public Menu hide(final UUID menuId) {
        final Menu menu = findById(menuId);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Menu findById(final UUID menuId) {
        return menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Menu> findAllByProductId(final UUID productId) {
        return menuRepository.findAllByProductId(productId);
    }
}

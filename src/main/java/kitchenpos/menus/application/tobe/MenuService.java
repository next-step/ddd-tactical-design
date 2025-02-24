package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuProductsValidator menuProductsValidator;

    public MenuService(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository, MenuProductsValidator menuProductsValidator) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuProductsValidator = menuProductsValidator;
    }

    @Transactional
    public Menu create(final Menu request) {
        validateMenuGroup(request);
        validateMenuProducts(request);
        return menuRepository.save(request);
    }

    @Transactional
    public Menu changePrice(final MenuId menuId, final Menu request) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(request.getPrice());
        return menu;
    }

    @Transactional
    public Menu display(final MenuId menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.show();
        return menu;
    }

    @Transactional
    public Menu hide(final MenuId menuId) {
        final Menu menu = menuRepository.findById(menuId)
                .orElseThrow(NoSuchElementException::new);
        menu.hide();
        return menu;
    }

    @Transactional(readOnly = true)
    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    private void validateMenuGroup(Menu request) {
        menuGroupRepository.findById(request.getGroupId())
                .orElseThrow(NoSuchElementException::new);
    }

    private void validateMenuProducts(Menu menu) {
        //메뉴 상품의 검증을 하는 도메인 서비스  menuProductsValidator 사용하도록 변경
        menuProductsValidator.validate(menu.getMenuProducts());
    }
}

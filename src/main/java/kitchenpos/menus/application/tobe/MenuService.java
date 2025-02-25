package kitchenpos.menus.application.tobe;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.presentation.dto.*;
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
    public MenuCreateResponse create(final MenuCreateRequest request) {
        Menu menu = MenuCreateRequest.From(request);
        validateMenuGroup(menu.getGroupId());
        validateMenuProducts(menu.getMenuProducts());
        return MenuCreateResponse.from(menuRepository.save(menu));
    }

    @Transactional
    public MenuChangePriceResponse changePrice(final MenuChangePriceRequest request) {
        MenuId id = new MenuId(request.getId());
        Price price = new Price(request.getPrice());

        final Menu menu = menuRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        menu.changePrice(price);

        return MenuChangePriceResponse.from(menu);
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

    @Transactional
    public MenuProductPriceChangeResponse changeProductPrice(final MenuProductPriceChangeRequest request){
        List<Menu> menus = menuRepository.findAllByProductId(request.getId());
        for (Menu menu : menus) {
            menu.changeProductPrice(request.getId(), request.getPrice());
        }
        return MenuProductPriceChangeResponse.from(menus);
    }

    private void validateMenuGroup(MenuGroupId menuGroupId) {
        menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    private void validateMenuProducts(MenuProducts menuProducts) {
        //메뉴 상품의 검증을 하는 도메인 서비스  menuProductsValidator 사용하도록 변경
        menuProductsValidator.validate(menuProducts);
    }
}

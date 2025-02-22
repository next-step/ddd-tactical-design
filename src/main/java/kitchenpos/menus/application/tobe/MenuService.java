package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductsException;
import kitchenpos.products.application.tobe.application.ProductInfoQuery;
import kitchenpos.products.tobe.domain.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final ProductInfoQuery productInfoQuery;

    public MenuService(MenuRepository menuRepository, MenuGroupRepository menuGroupRepository, ProductInfoQuery productInfoQuery) {
        this.menuRepository = menuRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.productInfoQuery = productInfoQuery;
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
        /*
        MenuProduct 검증을 위해서 ProductRepository를 직접 호출하는 상황
        Menu context에서 ProductRepository 직접 참조를 막기 위해서

        ACL 역할을 하는 ProductInfoQuery 생성하여
        ProductInfoQuery 통해서 Product 조회하도록 변경
        */
        final MenuProducts menuProductRequests = menu.getMenuProducts();
        final List<ProductId> productIds = menuProductRequests.productIds();

        int productSize = productInfoQuery.size(productIds);
        if (menuProductRequests.isSizeMismatch(productSize)) {
            throw new InvalidMenuProductsException();
        }

    }
}

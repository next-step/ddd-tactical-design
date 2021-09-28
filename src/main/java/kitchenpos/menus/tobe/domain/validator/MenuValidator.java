package kitchenpos.menus.tobe.domain.validator;

import kitchenpos.menus.tobe.domain.model.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.support.DomainService;

import java.util.List;

@DomainService
public class MenuValidator {

    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    public MenuValidator(MenuGroupRepository menuGroupRepository, ProductRepository productRepository) {
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    public void validateMenu(Menu menu) {
        menuGroupRepository.findById(menu.getMenuGroupId()).orElseThrow(() -> new IllegalArgumentException("메뉴는 특정 메뉴 그룹에 속해야 합니다."));
    }

    public void validateMenuProducts(List<MenuProduct> menuProducts) {
        menuProducts
                .forEach(menuProduct -> productRepository.findById(menuProduct.getProductId()).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 상품이 포함되어 있습니다.")));
    }

}

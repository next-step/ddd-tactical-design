package kitchenpos.menus.tobe.domain.service;

import kitchenpos.commons.tobe.domain.service.Validator;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.products.tobe.domain.model.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class MenuCreateValidator implements Validator<Menu> {

    private final MenuGroupRepository menuGroupRepository;

    private final ProductRepository productRepository;

    public MenuCreateValidator(final MenuGroupRepository menuGroupRepository, final ProductRepository productRepository) {
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void validate(final Menu menu) {
        menuGroupRepository.findById(menu.getMenuGroupId())
                .orElseThrow(() -> new NoSuchElementException("메뉴는 메뉴 그룹에 속해야 합니다"));

        List<UUID> productIds = menu.getProductIds();
        if (productIds.isEmpty()) {
            throw new IllegalArgumentException("1 개 이상의 메뉴 상품을 가져야 합니다");
        }

        List<Product> products = productRepository.findAllByIdIn(productIds);
        if (productIds.size() != products.size()) {
            throw new IllegalArgumentException("메뉴 상품은 대응하는 상품이 있어야 합니다");
        }

        if (menu.isDisplayed() && menu.isPriceGreaterThanAmount()) {
            throw new IllegalArgumentException("가격은 금액보다 적거나 같아야 합니다");
        }
    }
}

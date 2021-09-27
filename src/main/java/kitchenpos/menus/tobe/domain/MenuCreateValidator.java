package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class MenuCreateValidator {
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    public MenuCreateValidator(final MenuGroupRepository menuGroupRepository, final ProductRepository productRepository) {
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    public void validate(Menu menu) {
        validateExistentMenuGroup(menu.getMenuGroup());
        validateExistentProduct(menu.getMenuProducts());
    }

    private void validateExistentProduct(final MenuProducts menuProducts) {
        List<Product> products = productRepository.findAllByIdIn(
                menuProducts.getMenuProducts()
                        .stream()
                        .map(MenuProduct::getProductId)
                        .collect(Collectors.toList())
        );
        if (products.size() != menuProducts.getMenuProducts().size()) {
            throw new IllegalArgumentException("존재하지 않은 상품은 메뉴에 등록할 수 없습니다.");
        }
    }

    private MenuGroup validateExistentMenuGroup(final MenuGroup menuGroup) {
        return menuGroupRepository.findById(menuGroup.getId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 MenuGroup에 메뉴를 등록할 수 없습니다."));
    }
}

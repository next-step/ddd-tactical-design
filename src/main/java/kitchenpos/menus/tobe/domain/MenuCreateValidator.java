package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.exception.WrongPriceException;
import kitchenpos.menus.tobe.domain.repository.MenuGroupRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static kitchenpos.menus.tobe.domain.exception.WrongPriceException.PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE;

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
        validatePrice(menu.getPrice(), menu.getMenuProducts());
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

    private void validateExistentMenuGroup(final MenuGroup menuGroup) {
        menuGroupRepository.findById(menuGroup.getId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 MenuGroup에 메뉴를 등록할 수 없습니다."));
    }

    private void validatePrice(final Price price, final MenuProducts menuProducts) {
        BigDecimal sum = menuProducts.getMenuProducts().stream()
                .map(menuProduct -> menuProduct.getPrice().getValue()
                        .multiply(
                                BigDecimal.valueOf(menuProduct.getQuantity())
                        ))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (price.getValue().compareTo(sum) > 0) {
            throw new WrongPriceException(PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE);
        }
    }
}

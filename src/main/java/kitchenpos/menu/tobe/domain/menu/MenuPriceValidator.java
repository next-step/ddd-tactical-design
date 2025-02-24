package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.exception.MenuException;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.common.exception.ErrorCode.*;


@Component
public class MenuPriceValidator implements MenuValidator {
    private final ProductRepository productRepository;

    public MenuPriceValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public void validateMenuProductSize(MenuProducts menuProducts) {
        List<UUID> collect = menuProducts.getProducts().stream().map(MenuProduct::getProductId).collect(Collectors.toList());
        List<Product> allByIdIn = productRepository.findAllByIdIn(collect);
        if (allByIdIn.size() != menuProducts.getProducts().size()) {
            throw new MenuException(MENU_PRODUCTS_SIZE_NOT_MATCHED);
        }
    }
    @Override
    public void validateMenuPrice(MenuProducts menuProducts, MenuPrice menuPrice) {
        // 메뉴 상품들의 총 가격 계산
        Long totalProductPrice = calculateTotalProductPrice(menuProducts);
        System.out.println("totalProductPrice = " + totalProductPrice);
        System.out.println("menuPrice.getValue() = " + menuPrice.getValue());
        if (menuPrice.getValue() > totalProductPrice) {
            throw new MenuException(MENU_PRICE_INVALID);
        }
    }

    private Long calculateTotalProductPrice(MenuProducts menuProducts) {
        return menuProducts.getProducts().stream()
                .mapToLong(menuProduct -> {
                    Product product = productRepository.findById(menuProduct.getProductId())
                            .orElseThrow(() -> new MenuException(MENU_PRODUCT_NOT_FOUND));

                    if (menuProduct.getQuantity() < 0) {
                        throw new MenuException(MENU_QUANTITY_NEGATIVE);
                    }

                    return product.getPrice().getPrice() * menuProduct.getQuantity();
                })
                .sum();
    }
}

package kitchenpos.menu.tobe.infra;

import kitchenpos.common.exception.MenuException;
import kitchenpos.menu.tobe.domain.menu.MenuPrice;
import kitchenpos.menu.tobe.domain.menu.MenuProduct;
import kitchenpos.menu.tobe.domain.menu.MenuProducts;
import kitchenpos.menu.tobe.domain.menu.ProductClient;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static kitchenpos.common.exception.ErrorCode.*;
import static kitchenpos.common.exception.ErrorCode.MENU_QUANTITY_NEGATIVE;


@Component
public class ProductClientImpl implements ProductClient {
    private final ProductRepository productRepository;

    public ProductClientImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void validateMenuProductSize(MenuProducts menuProducts) {
        List<UUID> collect = menuProducts.getMenuProducts().stream().map(MenuProduct::getProductId).collect(Collectors.toList());
        List<Product> allByIdIn = productRepository.findAllByIdIn(collect);
        if (allByIdIn.size() != menuProducts.getMenuProducts().size()) {
            throw new MenuException(MENU_PRODUCTS_SIZE_NOT_MATCHED);
        }
    }

    @Override
    public void validateMenuPrice(MenuProducts menuProducts, MenuPrice menuPrice) {
        Long totalProductPrice = calculateTotalProductPrice(menuProducts);
        if (menuPrice.getValue() > totalProductPrice) {
            throw new MenuException(MENU_PRICE_INVALID);
        }
    }

    private Long calculateTotalProductPrice(MenuProducts menuProducts) {
        return menuProducts.getMenuProducts().stream()
                .mapToLong(menuProduct -> {
                    Product product = productRepository.findById(menuProduct.getProductId())
                            .orElseThrow(() -> new MenuException(MENU_PRODUCT_NOT_FOUND));

                    if (menuProduct.getQuantity() < 0) {
                        throw new MenuException(MENU_QUANTITY_NEGATIVE);
                    }

                    return product.getProductPrice().getPrice() * menuProduct.getQuantity();
                })
                .sum();
    }
}

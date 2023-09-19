package kitchenpos.menus.application;

import kitchenpos.menus.application.dto.request.MenuProductCreateRequest;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuPrice;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MenuValidator {
    private final ProductRepository productRepository;
    private final MenuGroupRepository menuGroupRepository;

    public MenuValidator(ProductRepository productRepository, MenuGroupRepository menuGroupRepository) {
        this.productRepository = productRepository;
        this.menuGroupRepository = menuGroupRepository;
    }

    public void validateMenuGroup(UUID menuGroupId) {
        menuGroupRepository.findById(menuGroupId)
                .orElseThrow(NoSuchElementException::new);
    }

    public void validateMenuProduct(List<MenuProductCreateRequest> menuProductRequests) {
        if (Objects.isNull(menuProductRequests) || menuProductRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Product> products = productRepository.findAllByIdIn(
                menuProductRequests.stream()
                        .map(MenuProductCreateRequest::getProductId)
                        .collect(Collectors.toList())
        );
        if (products.size() != menuProductRequests.size()) {
            throw new IllegalArgumentException();
        }
    }

    public void validateMenuPrice(List<MenuProductCreateRequest> menuProductRequests, BigDecimal price) {
        MenuPrice menuPrice = new MenuPrice(price);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProductCreateRequest menuProductRequest : menuProductRequests) {
            final long quantity = menuProductRequest.getQuantity();
            if (quantity < 0) {
                throw new IllegalArgumentException();
            }
            final Product product = productRepository.findById(menuProductRequest.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            sum = sum.add(
                    product.getPrice()
                            .multiply(BigDecimal.valueOf(quantity))
                            .getValue()
            );
        }
        if (menuPrice.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
    }

    public void validateChangePrice(List<MenuProduct> menuProducts, BigDecimal price) {
        MenuPrice menuPrice = new MenuPrice(price);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menuProducts) {
            final Product product = productRepository.findById(menuProduct.getProductId())
                    .orElseThrow(NoSuchElementException::new);
            sum = sum.add(product.multiplyPrice(menuProduct.getQuantity()).getValue());
        }
        if (menuPrice.compareTo(sum) > 0) {
            throw new IllegalArgumentException();
        }
    }
}

package kitchenpos.products.tobe.domain.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.springframework.stereotype.Component;

public interface ChangePrice {
    Product execute(final UUID productId, final ProductPriceChangeDto request);
}

@Component
class DefaultChangePrice implements ChangePrice {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public DefaultChangePrice(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Product execute(UUID productId, ProductPriceChangeDto request) {
        final ProductPrice price = ProductPrice.of(request.getPrice());
        final Product product = productRepository.findById(productId)
                                                 .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                    menuProduct.getProduct()
                               .getPrice()
                               .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return product;
    }
}
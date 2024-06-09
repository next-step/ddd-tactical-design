package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity {
    BigDecimal execute(Menu menu);
}

@Service
class DefaultCalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity implements CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity {
    private final ProductRepository productRepository;

    public DefaultCalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal execute(Menu menu) {
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                productRepository.findById(menuProduct.getProductId())
                                 .orElseThrow(
                                     () -> new NoSuchElementException("MenuProduct 에 올바르지 않은 ProductId 가 있습니다"))
                                 .getPrice()
                                 .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }
}

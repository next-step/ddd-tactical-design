package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.products.tobe.domain.entity.Product;
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
        List<UUID> productIds = menu.getMenuProducts().stream().map(MenuProduct::getProductId).toList();

        Map<UUID, BigDecimal> productIdPriceMap = productRepository.findAllByIdIn(productIds).stream().collect(Collectors.toMap(
                Product::getId,
                Product::getPrice
        ));

        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            if (!productIdPriceMap.containsKey(menuProduct.getProductId())) {
                throw new NoSuchElementException("MenuProduct 에 올바르지 않은 ProductId 가 있습니다");
            }
            sum = sum.add(
                    productIdPriceMap.get(menuProduct.getProductId())
                                     .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        return sum;
    }
}

package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductTotalPricePolicy implements MenuPricePolicy {

    private ProductRepository productRepository;

    public ProductTotalPricePolicy(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public boolean isNotPermit(MenuPrice price, Menu menu) {
        BigDecimal totalPrice = menu.getMenuProducts()
                .stream()
                .map(menuProduct -> {
                    Product product = productRepository.findById(menuProduct.getProductId()).orElseThrow(IllegalArgumentException::new);
                    return product.getPrice().multiply(BigDecimal.valueOf(menuProduct.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return price.isOver(totalPrice);
    }

}

package kitchenpos.menus.tobe.domain.model;

import kitchenpos.products.tobe.domain.repository.ProductRepository;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Embeddable
public class MenuProducts {

    private List<MenuProduct> menuProducts;

    protected MenuProducts() {

    }

    public MenuProducts(List<MenuProduct> menuProducts, ProductRepository productRepository) {
        menuProducts
                .forEach(menuProduct -> productRepository.findById(menuProduct.getProductId()).orElseThrow(() -> new IllegalArgumentException("등록되지 않은 상품이 포함되어 있습니다.")));

        this.menuProducts = menuProducts;
    }

    public BigDecimal calculateSum() {
        List<BigDecimal> amounts = this.menuProducts.stream()
                .map(MenuProduct::getAmount)
                .collect(Collectors.toList());

        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal amount : amounts) {
            sum = sum.add(amount);
        }
        return sum;
    }

}

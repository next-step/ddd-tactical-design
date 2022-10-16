package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ChangeProductPriceService {
    private final MenuRepository menuRepository;

    private final ProductRepository productRepository;

    public ChangeProductPriceService(
        final MenuRepository menuRepository,
        final ProductRepository productRepository
    ) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product changePrice(final UUID productId, final BigDecimal price) {
        final ProductPrice productPrice = new ProductPrice(price);
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(productPrice);
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                Product product1 = menuProduct.getProduct();
                sum = sum.add(
                        product1
                                .getPrice()
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

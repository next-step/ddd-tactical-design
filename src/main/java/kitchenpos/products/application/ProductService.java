package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.infra.MenuRepository;
import kitchenpos.products.common.NamePolicy;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.infra.ProductRepository;
import kitchenpos.products.ui.request.ProductPriceChangeRequest;
import kitchenpos.products.ui.request.ProductReq;
import kitchenpos.products.ui.response.ProductRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final NamePolicy namePolicy;

    public ProductService(
            ProductRepository productRepository,
            MenuRepository menuRepository,
            NamePolicy namePolicy
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.namePolicy = namePolicy;
    }

    @Transactional
    public ProductRes create(final ProductReq request) {
        Product product = productRepository.save(request.toEntity(namePolicy));
        return ProductRes.from(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductPriceChangeRequest request) {
        final Product product = findByProduct(productId);
        product.updatePrice(request.getPrice());

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

    private Product findByProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

}

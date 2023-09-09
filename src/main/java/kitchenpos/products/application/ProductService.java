package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static kitchenpos.products.exception.ProductExceptionMessage.NOT_FOUND_PRODUCT;
import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_PRICE_MORE_ZERO;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final CreateProductRequest request) {
        Product product = Product.create(request.getPrice(), request.getName(), purgomalumClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final ChangeProductPriceRequest request) {
        final Product product = productRepository.findById(request.getId())
            .orElseThrow( () -> new NoSuchElementException(NOT_FOUND_PRODUCT));
        product.changePrice(request.getPrice());
        final List<Menu> menus = menuRepository.findAllByProductId(request.getId());
        //applyChangedPrice()

        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                    menuProduct.getProduct()
                        .getPriceValue()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}

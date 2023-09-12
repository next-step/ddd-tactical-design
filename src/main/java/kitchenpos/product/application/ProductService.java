package kitchenpos.product.application;

import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.product.application.port.in.ProductUseCase;
import kitchenpos.product.application.port.out.LoadProductPort;
import kitchenpos.product.application.port.out.UpdateProductPort;
import kitchenpos.product.domain.Product;
import kitchenpos.profanity.infra.PurgomalumClient;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.product.adapter.out.persistence.ProductEntity;
import kitchenpos.product.adapter.out.persistence.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService implements ProductUseCase {
    private final ProductRepository productRepository;
    private final LoadProductPort loadProductPort;
    private final UpdateProductPort updateProductPort;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(ProductRepository productRepository, LoadProductPort loadProductPort, UpdateProductPort updateProductPort, MenuRepository menuRepository, PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.loadProductPort = loadProductPort;
        this.updateProductPort = updateProductPort;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final Product request) {
        final String name = request.getName();
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }

        updateProductPort.updateProduct(request);
        return request;
    }

    @Transactional
    public ProductEntity changePrice(final UUID productId, final ProductEntity request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final ProductEntity product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.setPrice(price);
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

    @Transactional(readOnly = true)
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }
}

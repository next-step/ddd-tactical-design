package kitchenpos.products.tobe.domain;

import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductDomainService {

    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final MenuRepository menuRepository;

    public ProductDomainService(ProductRepository productRepository, PurgomalumClient purgomalumClient, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.menuRepository = menuRepository;
    }

    public Product create(String name, BigDecimal price) {
        Product product = Product.create(
                ProductName.create(name, purgomalumClient),
                ProductPrice.create(price)
        );
        return productRepository.save(product);
    }

    public Product changePrice(UUID productId, BigDecimal price) {
        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(ProductPrice.update(productId, price, menuRepository));
        return product;
    }
}

package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.model.ProductModel;
import kitchenpos.products.domain.vo.Products;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuService menuService;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuService menuService,
            PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.menuService = menuService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final Product request) {
        return productRepository.save(new ProductModel(request, purgomalumClient)
                .toProduct());
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        Product product = productRepository.save(new ProductModel(getProduct(productId), purgomalumClient)
                .changePrice(request.getPrice()).toProduct());
        menuService.changeProductPrice(productId, request.getPrice());
        return this.productRepository.save(product);

    }

    private Product getProduct(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }

    @Transactional(readOnly = true)
    public Products findAllByIdIn(List<UUID> productIds) {
        return new Products(productRepository.findAllByIdIn(productIds));
    }

    @Transactional(readOnly = true)
    public Product findById(UUID productId) {
        return getProduct(productId);

    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }


}

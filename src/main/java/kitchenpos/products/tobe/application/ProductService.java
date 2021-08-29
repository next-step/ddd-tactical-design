package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public ProductDTO create(final ProductDTO request) {
        Product product = new Product(request.getName(), request.getPrice(), purgomalumClient::containsProfanity);
        return new ProductDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO changePrice(final UUID productId, final ProductDTO request) {
        return null;
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }
}

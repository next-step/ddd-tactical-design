package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductPriceChangeService productPriceChangeService;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            final ProductPriceChangeService productPriceChangeService
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productPriceChangeService = productPriceChangeService;
    }

    @Transactional
    public ProductDTO create(final ProductDTO request) {
        Product product = new Product(request.getName(), request.getPrice(), purgomalumClient::containsProfanity);
        return new ProductDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO changePrice(final UUID productId, final ProductDTO request) {
        Product product = productPriceChangeService.changePrice(productId, request.getPrice());
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());
    }
}

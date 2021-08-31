package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductPriceChangeService productPriceChangeService;
    private final DisplayedNameFactory displayedNameFactory;

    public ProductService(
            final ProductRepository productRepository,
            final ProductPriceChangeService productPriceChangeService,
            final DisplayedNameFactory displayedNameFactory
    ) {
        this.productRepository = productRepository;
        this.productPriceChangeService = productPriceChangeService;
        this.displayedNameFactory = displayedNameFactory;
    }

    @Transactional
    public ProductDTO create(final ProductDTO request) {
        final DisplayedName displayedName = displayedNameFactory.create(request.getName());
        final Price price = new Price(request.getPrice());
        final Product product = new Product(displayedName, price);
        return new ProductDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO changePrice(final UUID productId, final ProductDTO request) {
        final Product product = productPriceChangeService.changePrice(productId, request.getPrice());
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

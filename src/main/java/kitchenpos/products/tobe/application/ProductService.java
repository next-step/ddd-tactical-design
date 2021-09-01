package kitchenpos.products.tobe.application;

import kitchenpos.products.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final DisplayedNameFactory displayedNameFactory;

    public ProductService(
            final ProductRepository productRepository,
            final DisplayedNameFactory displayedNameFactory
    ) {
        this.productRepository = productRepository;
        this.displayedNameFactory = displayedNameFactory;
    }

    @Transactional
    public ProductDTO create(final ProductDTO request) {
        final DisplayedName displayedName = displayedNameFactory.create(request.getName());
        final Price price = new Price(request.getPrice());
        final Product product = new Product(UUID.randomUUID(), displayedName, price);
        return new ProductDTO(productRepository.save(product));
    }

    @Transactional
    public ProductDTO changePrice(final UUID productId, final ProductDTO request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("일치하는 상품이 없습니다"));
        final Price price = new Price(request.getPrice());
        product.changePrice(price);
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

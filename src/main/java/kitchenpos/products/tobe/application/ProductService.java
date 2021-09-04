package kitchenpos.products.tobe.application;

import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.ProductTranslator;
import kitchenpos.products.tobe.dto.ChangeProductPriceRequest;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import kitchenpos.products.tobe.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("TobeProductService")
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTranslator productTranslator;
    private final PurgomalumClient purgomalumClient;

    public ProductService(final ProductRepository productRepository, final ProductTranslator productTranslator, final PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.productTranslator = productTranslator;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductResponse create(final CreateProductRequest request) {
        final Product product = request.toProduct();

        if (purgomalumClient.containsProfanity(product.getName())) {
            throw new IllegalArgumentException();
        }
        return ProductResponse.from(productRepository.save(product));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ChangeProductPriceRequest request) {
        final BigDecimal price = request.getPrice();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new)
                .withPrice(price);

        productTranslator.changeMenuStatus(productId);
        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(ProductResponse::from)
                .collect(Collectors.toList());
    }
}

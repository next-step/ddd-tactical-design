package kitchenpos.products.tobe.application;

import kitchenpos.common.infra.Profanities;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.ChangeProductPriceRequest;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import kitchenpos.products.tobe.dto.ProductResponse;
import kitchenpos.products.tobe.infra.ProductTranslator;
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
    private final Profanities profanities;

    public ProductService(final ProductRepository productRepository, final ProductTranslator productTranslator, final Profanities profanities) {
        this.productRepository = productRepository;
        this.productTranslator = productTranslator;
        this.profanities = profanities;
    }

    @Transactional
    public ProductResponse create(final CreateProductRequest request) {
        final ProductName productName = new ProductName(request.getName(), profanities);
        final ProductPrice productPrice = new ProductPrice(request.getPrice());
        return ProductResponse.from(productRepository.save(new Product(productName, productPrice)));
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ChangeProductPriceRequest request) {
        final BigDecimal price = request.getPrice();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.setPrice(price);
        productTranslator.updateMenuStatus(productId);
        return ProductResponse.from(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream().map(ProductResponse::from)
                .collect(Collectors.toList());
    }
}

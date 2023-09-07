package kitchenpos.products.application;

import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductDetailResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductAggregate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductAggregate productAggregate;

    public ProductService(ProductAggregate productAggregate) {
        this.productAggregate = productAggregate;
    }

    @Transactional
    public ProductDetailResponse create(final ProductCreateRequest request) {
        return toProductDetailResponse(productAggregate.create(request.getName(), request.getPrice()));
    }

    @Transactional
    public ProductDetailResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        return toProductDetailResponse(productAggregate.changePrice(productId, request.getPrice()));
    }

    @Transactional(readOnly = true)
    public List<ProductDetailResponse> findAll() {
        return productAggregate.findAll()
                .stream()
                .map(this::toProductDetailResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    private ProductDetailResponse toProductDetailResponse(Product product) {
        return new ProductDetailResponse(product.getId(), product.getName(), product.getPrice());
    }
}

package kitchenpos.products.application;

import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.dto.ProductDetailResponse;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductDomainService;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductDomainService productDomainService;
    private final ProductRepository productRepository;

    public ProductService(ProductDomainService productDomainService, ProductRepository productRepository) {
        this.productDomainService = productDomainService;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDetailResponse create(final ProductCreateRequest request) {
        return toProductDetailResponse(productDomainService.create(request.getName(), request.getPrice()));
    }

    @Transactional
    public ProductDetailResponse changePrice(final UUID productId, final ProductChangePriceRequest request) {
        return toProductDetailResponse(productDomainService.changePrice(productId, request.getPrice()));
    }

    @Transactional(readOnly = true)
    public List<ProductDetailResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toProductDetailResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    private ProductDetailResponse toProductDetailResponse(Product product) {
        return new ProductDetailResponse(product.getId(), product.getName(), product.getPrice());
    }
}

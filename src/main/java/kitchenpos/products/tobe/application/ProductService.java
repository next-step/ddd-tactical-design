package kitchenpos.products.tobe.application;

import java.util.UUID;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.dto.CreateProductRequest;
import kitchenpos.products.tobe.dto.ProductResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(ProductRepository productRepository,
                          PurgomalumClient purgomalumClient) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public ProductResponse create(final CreateProductRequest request) {
        if (purgomalumClient.containsProfanity(request.getName())) {
            throw new IllegalArgumentException("상품명에 비속어가 포함되어 있습니다.");
        }

        Product saved = productRepository.save(Product.of(UUID.randomUUID(), request.getName(), request.getPrice()));
        return ProductResponse.from(saved);
    }
}

package kitchenpos.validate;

import java.util.List;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ValidateService {

    private final ProductRepository productRepository;

    public ValidateService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void checkProductsByIds(List<UUID> productIds) {
        List<Product> products = productRepository.findAllByIdIn(productIds);
        if (products.size() != productIds.size()) {
            throw new ProductNotFoundException("ID 에 해당하는 상품이 존재하지 않습니다.");
        }
    }
}

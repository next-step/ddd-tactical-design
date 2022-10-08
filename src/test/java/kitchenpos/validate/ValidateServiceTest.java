package kitchenpos.validate;

import static kitchenpos.products.ProductFixtures.product;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.UUID;
import kitchenpos.products.domain.InMemoryProductRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ValidateServiceTest {

    private ValidateService validateService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        validateService = new ValidateService(productRepository);
    }

    @DisplayName("ID로 주어진 상품이 존재하지 않으면 예외가 발생한다.")
    @Test
    void productNotFoundException() {
        // given
        Product product1 = productRepository.save(product());

        // when, then
        assertThatThrownBy(() -> validateService.checkProductsByIds(Arrays.asList(
            product1.getId(),
            UUID.randomUUID()
        ))).isExactlyInstanceOf(ProductNotFoundException.class);
    }
}

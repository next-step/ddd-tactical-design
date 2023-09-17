package kitchenpos.products.tobe.domain;

import kitchenpos.support.product.event.ProductPriceChangedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@RecordApplicationEvents
class ProductDomainServiceTest {

    @Autowired
    private ProductDomainService productDomainService;
    @Autowired
    private ApplicationEvents applicationEvents;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
    }

    @DisplayName("상품 가격을 변경할 수 있다")
    @Nested
    class ChangePrice {
        @DisplayName("상품 가격을 변경하면 상품 가격 변경 이벤트가 발생한다")
        @Test
        void givenProductAndPrice_whenPriceIsChanged_thenEventIsPublished() {
            // Given
            final Product product = productRepository.save(product());
            final BigDecimal changedPrice = product.getPrice().add(BigDecimal.valueOf(1_000L));

            // When
            final boolean isChanged = productDomainService.changePrice(product, changedPrice);

            // Then
            assertThat(isChanged).isTrue();
            assertThat(applicationEvents.stream(ProductPriceChangedEvent.class).count()).isOne();
        }

        @DisplayName("상품 가격을 변경되지 않으면, 상품 가격 변경 이벤트가 발생하지 않는다")
        @Test
        void givenProductAndPrice_whenPriceIsNotChanged_thenEventIsNotPublished() {
            // Given
            final Product product = productRepository.save(product());
            final BigDecimal notChangedPrice = product.getPrice();

            // When
            final boolean isChanged = productDomainService.changePrice(product, notChangedPrice);

            // Then
            assertThat(isChanged).isFalse();
            assertThat(applicationEvents.stream(ProductPriceChangedEvent.class).count()).isZero();
        }
    }

}

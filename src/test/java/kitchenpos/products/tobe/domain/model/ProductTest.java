package kitchenpos.products.tobe.domain.model;

import kitchenpos.common.Profanity;
import kitchenpos.doubles.FakeProfanity;
import kitchenpos.products.tobe.domain.events.ProductPriceChangedEvent;
import kitchenpos.products.tobe.domain.events.ProductPriceChangedPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@RecordApplicationEvents
@ExtendWith(SpringExtension.class)
class ProductTest {
    private static final Profanity FAKE_PROFANITY = new FakeProfanity();

    @Autowired
    private ApplicationEvents applicationEvents;

    @Autowired
    private ApplicationContext eventPublisher;

    private ProductPriceChangedPublisher publisher;


    @BeforeEach
    void setUp() {
        publisher = new ProductPriceChangedPublisher(eventPublisher);
    }

    @DisplayName("가격은 0원 이상으로만 변경할 수 있다.")
    @Test
    void changePrice_Exception() {
        Product product = new Product(UUID.randomUUID(), "후라이드 치킨", 9_000L, FAKE_PROFANITY);

        assertThatThrownBy(() -> product.changePrice(-1000L, publisher))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가격은 0원 이상으로 변경하면 ProductPriceChangedEvent가 발행된다.")
    @Test
    void changePrice() {
        // given
        UUID productId = UUID.randomUUID();
        Product product = new Product(productId, "후라이드 치킨", 9_000L, FAKE_PROFANITY);

        // when
        product.changePrice(10_000L, publisher);

        // then
        List<ProductPriceChangedEvent> events = applicationEvents
                .stream(ProductPriceChangedEvent.class)
                .collect(Collectors.toList());

        assertAll(
                () -> assertThat(events).hasSize(1),
                () -> assertThat(events.get(0).getProductId()).isEqualTo(productId),
                () -> assertThat(events.get(0).getChangedPrice()).isEqualTo(10_000L)
        );
    }

    @DisplayName("식별자가 같으면 동등한 상품이다.")
    @Test
    void equals() {
        UUID id = UUID.randomUUID();

        assertThat(new Product(id, "후라이드 치킨", 9_000L, FAKE_PROFANITY))
                .isEqualTo(new Product(id, "Fried Chicken", 9_000L, FAKE_PROFANITY));
    }
}

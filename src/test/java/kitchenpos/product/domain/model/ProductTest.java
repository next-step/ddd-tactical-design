package kitchenpos.product.domain.model;

import kitchenpos.shared.event.DomainEvent;
import kitchenpos.shared.event.ProductPriceChangedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    private MockProduct product;

    @BeforeEach
    void setUp() {
        final ProductName name = ProductName.of("상품", nm -> {});
        final ProductPrice price = ProductPrice.of(BigDecimal.valueOf(1000));
        product = new MockProduct(UUID.randomUUID(), name, price);
    }

    @DisplayName("`Product Price`를 변경할 수 있다.")
    @Test
    void changePrice() {
        // given
        final BigDecimal newPrice = BigDecimal.valueOf(2000);

        // when
        product.changePrice(newPrice);

        // then
        assertThat(product.isSamePrice(newPrice)).isTrue();
    }

    @DisplayName("`Product Price`를 변경시 이벤트를 발생시킨다.")
    @Test
    void changePriceWithEvent() {
        // given
        final BigDecimal newPrice = BigDecimal.valueOf(2000);
        final ProductPriceChangedEvent expectedEvent = new ProductPriceChangedEvent(product.getId(), product.getPrice(), newPrice);

        // when
        product.changePrice(newPrice);

        // then
        assertThat(product.getEvents()).hasSize(1);
        assertThat(product.getFirstEvent()).isEqualTo(expectedEvent);
    }

    static class MockProduct extends Product {
        private final List<DomainEvent> events = new ArrayList<>();

        public MockProduct(UUID id, ProductName name, ProductPrice price) {
            super(id, name, price);
        }

        @Override
        public void registerEvent(DomainEvent event) {
            events.add(event);
        }

        public List<DomainEvent> getEvents() {
            return events;
        }

        public ProductPriceChangedEvent getFirstEvent() {
            return (ProductPriceChangedEvent) events.getFirst();
        }
    }
}
package kitchenpos.products.tobe.domain;

import kitchenpos.common.event.Events;
import kitchenpos.common.vo.Price;
import kitchenpos.products.tobe.domain.event.ProductPriceChangedEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductTest {

    @DisplayName("상품 식별자, 상품명, 가격을 입력하여 상품을 생성한다")
    @Test
    void create() {
        ProductId id = ProductId.generate();
        ProductName name = new ProductName("새우버거", (productName) -> false);
        Price price = new Price(7000);

        Product product = new Product(id, name, price);

        assertAll(
                () -> assertThat(product.getId()).isEqualTo(id),
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price)
        );
    }

    @DisplayName("상품의 가격을 변경할 수 있다. 가격이 변경되면 가격 변경 이벤트가 발행된다")
    @Test
    void changePrice() {
        ApplicationEventPublisher eventPublisher = initializeDomainEventPublisher();

        Product product = new Product(
                ProductId.generate(),
                new ProductName("새우버거", (productName) -> false),
                new Price(7000)
        );

        product.changePrice(7500);

        assertThat(product.getPrice()).isEqualTo(new Price(7500));
        verify(eventPublisher, times(1)).publishEvent(any(ProductPriceChangedEvent.class));
    }

    private ApplicationEventPublisher initializeDomainEventPublisher() {
        ApplicationEventPublisher eventPublisher = mock(ApplicationEventPublisher.class);
        Events events = new Events(eventPublisher);
        return eventPublisher;
    }
}

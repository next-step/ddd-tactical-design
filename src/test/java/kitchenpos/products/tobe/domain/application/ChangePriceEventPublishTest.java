package kitchenpos.products.tobe.domain.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.common.domainevent.SpringApplicationEventPublisher;
import kitchenpos.common.domainevent.event.ProductPriceChanged;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class ChangePriceEventPublishTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Captor
    private ArgumentCaptor<Object> eventPublisherCaptor;

    private ChangePrice changePrice;

    @BeforeEach
    void setUp() {
        changePrice = new DefaultChangePrice(productRepository, new SpringApplicationEventPublisher(eventPublisher));
    }

    @Test
    @DisplayName("상품 가격 변경 시 이벤트 발행 테스트")
    void eventTest() {
        UUID uuid = UUID.randomUUID();
        given(productRepository.findById(uuid)).willReturn(
            Optional.of(Product.createProduct(uuid, "test_product", BigDecimal.TEN, (text) -> false))
        );

        changePrice.execute(uuid, new ProductPriceChangeDto(BigDecimal.TWO));


        verify(eventPublisher, times(1)).publishEvent(eventPublisherCaptor.capture());

        List<Object> allValues = eventPublisherCaptor.getAllValues();

        assertThat(allValues.get(0)).isInstanceOf(ProductPriceChanged.class);
    }
}

package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;
import kitchenpos.products.tobe.domain.event.ProductPriceChanged;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;

public class ProductMenuServiceTest {

    ProductMenuService productMenuService;
    ProductRepository productRepository;


    ApplicationEventPublisher eventPublisher;

    @BeforeEach
    public void init() {
        productRepository = new InMemoryProductRepository();
        eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
        productMenuService = new ProductMenuService(productRepository, eventPublisher);

    }

    @DisplayName("Product가격을 변경할수 있다.")
    @Test
    void test1() {
        //given
        Product product = productRepository.save(new Product("name", BigDecimal.TEN));
        BigDecimal changePrice = BigDecimal.valueOf(1_000);

        //when
        productMenuService.changeProductPrice(product.getId(), changePrice);
        doNothing().when(eventPublisher).publishEvent(any(ProductPriceChanged.class));
        Product result = productRepository.findById(product.getId()).get();

        //then
        assertThat(result.getPrice()).isEqualTo(changePrice);
    }
}

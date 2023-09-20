package kitchenpos.products.application;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.event.ProductPriceChangedEvent;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@RecordApplicationEvents
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MenuRepository menuRepository;
    private PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Autowired
    ApplicationEvents events;

    @Autowired
    private ProductService productService;

    @Test
    public void 가격_변경() throws Exception {
        final Product product = productRepository.save(new Product(UUID.randomUUID(),new ProductName("후라이드",purgomalumClient),new ProductPrice(
            BigDecimal.valueOf(16_000))));
        productService.changePrice(product.getId(), product);
        assertThat(events.stream(ProductPriceChangedEvent.class).count()).isEqualTo(1);
    }

}
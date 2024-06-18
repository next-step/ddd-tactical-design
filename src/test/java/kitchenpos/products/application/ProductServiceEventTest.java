package kitchenpos.products.application;

import kitchenpos.Fixtures;
import kitchenpos.common.domain.ProductPriceChangeEvent;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import kitchenpos.products.application.dto.ProductRequest;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.domain.tobe.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@RecordApplicationEvents
public class ProductServiceEventTest {
  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private MenuRepository menuRepository;
  @Autowired
  private ProductService productService;
  @Autowired
  private ApplicationEventPublisher publisher;
  @Autowired
  private ApplicationEvents events;

  @DisplayName("상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.")
  @Test
  void changePriceInMenu() {
    final Product product = productRepository.save(product("후라이드", 16_000L));
    final Menu menu = menuRepository.save(menu(19_000L, true, Fixtures.menuProduct(product, 2L)));
    productService.changePrice(product.getId(), changePriceRequest(8_000L));

    List<ProductPriceChangeEvent> changeEvents = events.stream(ProductPriceChangeEvent.class).toList();
    assertThat(changeEvents.size()).isEqualTo(1);
    assertThat(changeEvents.getFirst().getProductId()).isEqualTo(product.getId());
    assertThat(BigDecimal.valueOf(changeEvents.getFirst().getPrice())).isEqualTo(product.getProductPrice());

  }
  private ProductRequest changePriceRequest(final Long price) {
    final ProductRequest product = new ProductRequest();
    product.setPrice(price);
    return product;
  }
}

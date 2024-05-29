package kitchenpos.products.tobe.application;

import java.math.BigDecimal;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
  private final ProductService productService;

  public ProductServiceTest(@Mock ProductRepository productRepository, @Mock MenuRepository menuRepository) {
    this.productService = new ProductService(productRepository, menuRepository, new FakePurgomalumClient());
  }

  @Test
  @DisplayName("상품 이름에 비속어가 들어갈 경우")
  void name() {
    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              productService.create("비속어", BigDecimal.ONE);
            });
  }
}

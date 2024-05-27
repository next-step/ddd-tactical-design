package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.tobe.infra.PurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
  @Mock private MenuRepository menuRepository;
  @Mock private ProductRepository productRepository;
  @Mock private PurgomalumClient purgomalumClient;

  @InjectMocks private ProductService productService;

  @Test
  @DisplayName("상품 이름에 비속어가 들어갈 경우")
  void name() {
    BDDMockito.given(purgomalumClient.containsProfanity(any())).willReturn(true);

    Assertions.assertThatIllegalArgumentException()
        .isThrownBy(
            () -> {
              productService.create("비속어", BigDecimal.ONE);
            });
  }
}
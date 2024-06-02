package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.products.application.FakeProfanityValidator;
import kitchenpos.products.domain.tobe.ProfanityValidator;
import kitchenpos.products.domain.tobe.Product;
import kitchenpos.products.fixture.ProductFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

//    - 상품을 생성할 수 있다.
//    - 상품의 가격을 변경할 수 있다.
//    - 상품의 가격이 올바르지 않으면 변경할 수 없다.
//    - 상품의 가격은 0원 이상이어야 한다.
//    - 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
@DisplayName("상품 테스트")
class ProductTest {

  public static final String UDON = "udon";
  public static final long VAL = 10_000L;
  private ProfanityValidator profanityValidator;

  @BeforeEach
  void setUp(){
    profanityValidator = new FakeProfanityValidator();
  }

  @DisplayName("상품을 생성할 수 있다.")
  @Test
  void createProduct(){
    Product actual = Product.from(UDON, VAL, profanityValidator);

    assertAll(
        () -> assertThat(actual.getProductPrice()).isEqualTo(BigDecimal.valueOf(VAL)),
        () -> assertThat(actual.getProductName()).isEqualTo(UDON),
        () -> assertThat(actual.getId()).isNotNull()
    );
  }

  @DisplayName("상품의 가격을 변경할 수 있다.")
  @Test
  void changeProductPrice(){
    Product actual = ProductFixture.createProduct();

    actual.changeProductPrice(20_000L);

    assertThat(actual.getProductPrice()).isEqualTo(BigDecimal.valueOf(20_000L));
  }

}

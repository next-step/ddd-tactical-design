package kitchenpos.products.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import kitchenpos.products.application.FakeProfanityValidator;
import kitchenpos.products.domain.tobe.ProductName;
import kitchenpos.products.domain.tobe.ProductPrice;
import kitchenpos.products.domain.tobe.ProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ProductNameTest {
  private ProfanityValidator profanityValidator;

  @BeforeEach
  void setUp(){
    profanityValidator = new FakeProfanityValidator();
  }
  @DisplayName("상품의 이름을 생성할 수 있다.")
  @Test
  void createProductName(){
    ProductName actual = ProductName.from("udon", profanityValidator);

    assertAll(
        () -> assertThat(actual.getName()).isEqualTo("udon")
    );
  }
  @DisplayName("상품의 이름에는 비속어가 포함될 수 없다.")
  @ParameterizedTest
  @ValueSource(strings = {"비속어", "욕설"})
  void changeProductNameWithBadWords(String name){
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> ProductName.from(name, profanityValidator))
        .withMessageContaining("상품의 이름에는 비속어가 포함될 수 없다.");
  }

  @DisplayName("상품의 가격상품의 이름이 올바르지 않으면 등록할 수 없다.")
  @ParameterizedTest
  @NullSource
  void changeProductNameWithNull(String name){
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> ProductName.from(name, profanityValidator))
        .withMessageContaining("상품의 이름이 올바르지 않으면 등록할 수 없다.");
  }
}

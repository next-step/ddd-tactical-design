package kitchenpos.product.tobe.domain;


import kitchenpos.common.infra.FakePurgomalumClient;
import kitchenpos.product.tobe.domain.validate.ProfanityValidator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductNameTest {

    @Test
    @DisplayName("상품명이 null이라면 예외가 발생한다.")
    void test1() {
        ProfanityValidator profanityValidator = new ProfanityValidator(new FakePurgomalumClient(false));

        Assertions.assertThatThrownBy(() -> new ProductName(null, profanityValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명이 비속어가 포함되어 있다면 예외가 발생한다.")
    void test2() {
        ProfanityValidator profanityValidator = new ProfanityValidator(new FakePurgomalumClient(true));

        Assertions.assertThatThrownBy(() -> new ProductName("상품명", profanityValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("상품명을 정상적으로 생성할 수 있다.")
    void test3() {
        ProfanityValidator profanityValidator = new ProfanityValidator(new FakePurgomalumClient(false));
        ProductName productName = new ProductName("상품명", profanityValidator);

        Assertions.assertThat(productName).isNotNull();
    }

}
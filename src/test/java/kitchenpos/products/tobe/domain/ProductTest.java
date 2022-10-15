package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import kitchenpos.products.tobe.fake.FakeProfanityValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("상품 테스트")
class ProductTest {

    private FakeProfanityValidator profanity;

    @BeforeEach
    void setUp() {
        profanity = FakeProfanityValidator.from("비속어");
    }

    @DisplayName("상품의 이름은 비어 있지 않아야 한다")
    @Test
    void product_displayedName_is_not_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(()-> new Product(null, Price.from(3)));
    }

    @DisplayName("상품의 가격은 비어 있지 않아야 한다")
    @Test
    void product_price_is_not_null() {
        assertThatIllegalArgumentException()
                .isThrownBy(()-> new Product(DisplayedName.of("이름", profanity), null));
    }

    @DisplayName("상품의 가격은 0원 이상이어야 한다")
    @Test
    void proudct_price() {
        assertThatIllegalArgumentException()
                .isThrownBy(()-> new Product(DisplayedName.of("이름", profanity), Price.from(-3)));
    }

    @DisplayName("상품의 가격은 0원 이상이어야 변경이 가능하다.")
    @Test
    void change_validate_price() {
        Product product = new Product(DisplayedName.of("이름", profanity), Price.from(10));

        assertThatIllegalArgumentException()
                .isThrownBy(()-> product.changePrice(Price.from(-3)));
    }


    @DisplayName("상품의 가격을 변경한다.")
    @Test
    void changePrice() {
        Product product = new Product(DisplayedName.of("이름", profanity), Price.from(10));

        product.changePrice(Price.from(15));

        assertThat(product.getPrice()).isEqualTo(Price.from(15));
    }


}

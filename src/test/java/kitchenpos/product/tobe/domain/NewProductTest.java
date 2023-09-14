package kitchenpos.product.tobe.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static kitchenpos.fixture.NewProductFixture.createNewProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class NewProductTest {
    @DisplayName("비속어가 없는 상품을 생성한다")
    @Test
    void test1() {
        final Name name = new Name("name");
        final Price price = new Price(BigDecimal.ONE);

        final NewProduct newProduct = NewProduct.createWithoutProfanity(name, price, text -> false);

        assertThat(newProduct.getId()).isNotNull();
        assertThat(newProduct.getName()).isEqualTo(name);
        assertThat(newProduct.getPrice()).isEqualTo(price);
    }

    @DisplayName("비속어가 있으면 상품 생성을 실패한다")
    @Test
    void test2() {
        final Name name = new Name("name");
        final Price price = new Price(BigDecimal.ONE);

        assertThatExceptionOfType(IllegalNewProductNameException.class)
                .isThrownBy(() -> NewProduct.createWithoutProfanity(name, price, text -> true));
    }

    @DisplayName("상품의 가격을 변경 한다")
    @Test
    void test3() {
        final Price newPrice = new Price(BigDecimal.TEN);
        final NewProduct newProduct = createNewProduct("name", BigDecimal.ONE);

        newProduct.changePrice(newPrice);

        assertThat(newProduct.getPrice()).isEqualTo(newPrice);
    }
}

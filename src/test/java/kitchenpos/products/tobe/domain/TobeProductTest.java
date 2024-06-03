package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

/**
 * - 상품의 가격이 변경될 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 크면 메뉴가 숨겨진다.
 * - 상품의 목록을 조회할 수 있다.
 */
class TobeProductTest {

    @DisplayName("가격 Validation")
    @Nested
    class PriceTest {
        @DisplayName("상품의 가격이 0원 이상이면 정상적으로 product 생성")
        @Test
        void case_1() {
            // given
            var price = 10_000;

            // when
            // then
            assertThatNoException().isThrownBy(() -> createProduct(price));
        }

        @DisplayName("상품의 가격이 0원 미만이면 throw exception")
        @Test
        void case_2() {
            // given
            var price = -1;

            // when
            // then
            assertThatIllegalArgumentException().isThrownBy(() -> createProduct(price));
        }

        @DisplayName("상품의 가격이 0원 이상이면 가격을 변경할 수 있다.")
        @Test
        void case_3() {
            // given
            var product = createProduct(10_000);
            var newPrice = 6_000;

            // when
            product.changePrice(newPrice);

            // then
            var changedPrice = product.getPrice();
            var expectedPrice = Price.of(newPrice);
            assertThat(changedPrice).isEqualTo(expectedPrice);
        }

        @DisplayName("상품의 가격이 0원 미만이면 가격을 변경할 수 있다.")
        @Test
        void case_4() {
            // given
            var product = createProduct(10_000);
            var targetPrice = -1;

            // when
            // then
            assertThatIllegalArgumentException().isThrownBy(() -> product.changePrice(targetPrice));
        }
    }

    @DisplayName("이름 Validation")
    @Nested
    class NameTest {

        private Profanities profanities;

        @BeforeEach
        void setUp() {
            profanities = new FakeProfanities("비속어");
        }

        @DisplayName("상품의 이름이 존재해야 한다.")
        @NullAndEmptySource
        @ParameterizedTest
        void case_1(String name) {
            // when
            // then
            assertThatIllegalArgumentException().isThrownBy(() -> createProduct(name, profanities));
        }

        @DisplayName("상품의 이름에 비속어가 포함될 수 없다.")
        @Test
        void case_2() {
            // given
            var name = "비속어";

            // when
            // then
            assertThatIllegalArgumentException().isThrownBy(() -> createProduct(name, profanities));
        }

        @DisplayName("상품의 이름에 비속어가 없으면 등록 가능하다.")
        @Test
        void case_3() {
            // given
            var name = "후라이드";

            // when
            TobeProduct product = createProduct(name, profanities);

            // then
            assertThat(product.getName()).isEqualTo(name);
        }
    }

    private static TobeProduct createProduct(int price) {
        var name = "후라이드";
        return createProduct(name, price);
    }

    private static TobeProduct createProduct(String name, int price) {
        Profanities profanities = new FakeProfanities("비속어");
        return createProduct(name, price, profanities);
    }


    private static TobeProduct createProduct(String name, Profanities profanities) {
        var price = 10_000;
        return createProduct(name, price, profanities);
    }

    private static TobeProduct createProduct(String name, int price, Profanities profanities) {
        var id = UUID.randomUUID();
        return new TobeProduct(id, name, price, profanities);
    }
}
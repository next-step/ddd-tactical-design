package kitchenpos.products.tobe.domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.DisplayedNamePolicy;
import kitchenpos.common.domain.FakeDisplayedNamePolicy;
import kitchenpos.common.domain.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("상품은")
class ProductTest {

    private static final DisplayedNamePolicy NAME_POLICY = new FakeDisplayedNamePolicy();

    private final DisplayedName name = new DisplayedName("test", NAME_POLICY);

    @Nested
    @DisplayName("등록할 수 있다.")
    class 등록할_수_있다 {

        @DisplayName("이름이 비어있다면 등록할 수 없다")
        @ParameterizedTest(name = "{0}인 경우")
        @NullAndEmptySource
        void 이름이_비어있다면_등록할_수_없다(String value) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(UUID.randomUUID(), new DisplayedName(value, NAME_POLICY), 1000));
        }

        @DisplayName("가격이 0원 미만이라면 등록할 수 없다")
        @Test
        void 가격이_0원_미만이라면_등록할_수_없다() {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(UUID.randomUUID(), name, -1));
        }

        @DisplayName("비속어가 포함되어 있다면 추가할 수 없다")
        @ParameterizedTest
        @ValueSource(strings = {"비속어", "욕설"})
        void 비속어가_포함되어_있다면_추가할_수_없다(String value) {
            assertThatIllegalArgumentException()
                .isThrownBy(() -> new Product(UUID.randomUUID(), new DisplayedName(value, NAME_POLICY), 1000));
        }

        @DisplayName("이름이 비어있지 않고 비속어가 포함되어 있지 않고 가격이 0원 이상이라면 등록할 수 있다")
        @ParameterizedTest(name = "이름: {0}, 가격: {1} 인 경우")
        @CsvSource(value = {"테스트,100", "test,0"})
        void 이름이_비어있지_않고_비속어가_포함되어_있지_않고_가격이_0원_이상이라면_등록할_수_있다(String nameValue, long price) {
            assertDoesNotThrow(() -> new Product(UUID.randomUUID(), new DisplayedName(nameValue, NAME_POLICY), price));
        }
    }

    @Nested
    @DisplayName("변경할 가격이")
    class 변경할_가격이 {

        @DisplayName("0원 미만이라면 변경 불가능하다.")
        @ParameterizedTest(name = "{0} 인 경우")
        @ValueSource(longs = {-1, -10})
        void 빵원_미만이라면_변경_불가능하다(long value) {
            final Product product = new Product(UUID.randomUUID(), name, 1000);

            assertThatIllegalArgumentException()
                .isThrownBy(() -> product.changePrice(new Money(value)));
        }

        @DisplayName("0원 이상이라면 변경 가능하다.")
        @ParameterizedTest(name = "{0} 인 경우")
        @ValueSource(longs = {0, 1})
        void 빵원_이상이라면_변경_가능하다(long value) {
            final Product product = new Product(UUID.randomUUID(), name, 1000);

            assertDoesNotThrow(() -> product.changePrice(new Money(value)));
        }
    }
}

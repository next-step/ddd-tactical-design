package kitchenpos.menus.tobe.domain.menu;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.DisplayedNamePolicy;
import kitchenpos.common.domain.FakeDisplayedNamePolicy;
import kitchenpos.common.domain.Money;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("메뉴는")
class MenuTest {

    private final DisplayedNamePolicy NAME_POLICY = new FakeDisplayedNamePolicy();

    private final UUID menuGroupId = UUID.randomUUID();
    private final MenuProduct 천원메뉴상품 = new MenuProduct(
        new ProductId(UUID.randomUUID()),
        1_000,
        1
    );
    final DisplayedName name = new DisplayedName("test", text -> false);

    @DisplayName("등록할 수 있다.")
    @Nested
    class 등록할_수_있다 {

        @DisplayName("가격이 0원 미만이면 등록할 수 없다.")
        @ParameterizedTest(name = "{0}인 경우")
        @ValueSource(longs = {-1, -2})
        void 가격이_0원_미만이면_등록할_수_없다(long price) {
            assertThatIllegalArgumentException()
                .isThrownBy(
                    () -> new Menu(
                        UUID.randomUUID(),
                        name,
                        price,
                        menuGroupId,
                        true,
                        천원메뉴상품
                    )
                );
        }

        @DisplayName("가격이 속한 메뉴 상품의 금액의 합보다 크다면 등록할 수 없다.")
        @Test
        void 가격이_속한_메뉴_상품의_금액의_합보다_크다면_등록할_수_없다() {

            final MenuProduct 백원메뉴상품 = new MenuProduct(
                new ProductId(UUID.randomUUID()),
                100,
                1
            );

            final MenuProduct 구백원메뉴상품 = new MenuProduct(
                new ProductId(UUID.randomUUID()),
                900,
                1
            );

            assertThatIllegalArgumentException()
                .isThrownBy(
                    () -> new Menu(
                        UUID.randomUUID(),
                        name,
                        1_001,
                        menuGroupId,
                        true,
                        백원메뉴상품,
                        구백원메뉴상품
                    )
                );
        }

        @DisplayName("특정 메뉴 그룹에 속하지 않으면 등록할 수 없다.")
        @Test
        void 특정_메뉴_그룹에_속하지_않으면_등록할_수_없다() {
            assertThatIllegalArgumentException()
                .isThrownBy(
                    () -> new Menu(
                        UUID.randomUUID(),
                        name,
                        1_000,
                        null,
                        true,
                        천원메뉴상품
                    )
                );
        }

        @DisplayName("이름이 비어 있다면 등록할 수 없다.")
        @ParameterizedTest(name = "{0}인 경우")
        @NullAndEmptySource
        void 이름이_비어_있다면_등록할_수_없다(String value) {
            assertThatIllegalArgumentException()
                .isThrownBy(
                    () -> new Menu(
                        UUID.randomUUID(),
                        new DisplayedName(value, NAME_POLICY),
                        1_000,
                        menuGroupId,
                        true,
                        천원메뉴상품
                    )
                );
        }

        @DisplayName("비속어가 포함되어 있다면 추가할 수 없다")
        @ParameterizedTest
        @ValueSource(strings = {"비속어", "욕설"})
        void 비속어가_포함되어_있다면_추가할_수_없다(String value) {
            assertThatIllegalArgumentException()
                .isThrownBy(
                    () -> new Menu(
                        UUID.randomUUID(),
                        new DisplayedName(value, NAME_POLICY),
                        1_000,
                        menuGroupId,
                        true,
                        천원메뉴상품
                    )
                );
        }

        @DisplayName("가격이 속한 메뉴 상품의 금액의 합보다 작고 특정 메뉴그룹에 속하고 이름이 비어있않고 비속어가 포함되어 있지  않다면 등록 가능하다")
        @Test
        void 가격이_속한_메뉴_상품의_금액의_합보다_작고_특정_메뉴그룹에_속하고_이름이_비어있고_비속어가_포함되어_있지_않다면_등록_가능하다() {
            assertDoesNotThrow(
                () -> new Menu(
                    UUID.randomUUID(),
                    name,
                    1_000,
                    menuGroupId,
                    true,
                    천원메뉴상품
                )
            );
        }
    }

    @DisplayName("가격을 변경할 수 있다")
    @Nested
    class 가격을_변경할_수_있다 {

        @DisplayName("변경할 가격이 0보다 작다면 변경할 수 없다")
        @Test
        void 변경할_가격이_0보다_작다면_변경할_수_없다() {
            final Menu menu = new Menu(
                UUID.randomUUID(),
                name,
                1_000,
                menuGroupId,
                true,
                천원메뉴상품
            );

            assertThatIllegalArgumentException()
                .isThrownBy(() -> menu.changePrice(new Money(-1)));
        }

        @DisplayName("변경할 가격이 속한 메뉴 상품의 금액의 합보다 크면 변경할 수 없다.")
        @Test
        void 변경할_가격이_속한_메뉴_상품의_금액의_합보다_크면_변경할_수_없다() {
            final Menu menu = new Menu(
                UUID.randomUUID(),
                name,
                1_000,
                menuGroupId,
                true,
                천원메뉴상품
            );

            assertThatIllegalArgumentException()
                .isThrownBy(() -> menu.changePrice(new Money(1_001)));
        }

        @DisplayName("변경할 가격이 속한 메뉴 상품의 금액의 합보다 작거나 같다면 변경할 수 있다.")
        @ParameterizedTest(name = "기존 가격 1000원 변경할 가격이 {0}원 인 경우")
        @ValueSource(longs = {1000, 999})
        void 변경할_가격이_속한_메뉴_상품의_금액의_합보다_작거나_같다면_변경할_수_있다(long changePrice) {
            final Menu menu = new Menu(
                UUID.randomUUID(),
                name,
                1_000,
                menuGroupId,
                true,
                천원메뉴상품
            );
            assertDoesNotThrow(() -> menu.changePrice(new Money(changePrice)));
        }
    }

    @DisplayName("노출 여부를 변경할 수 있다")
    @Nested
    class 노출_여부를_변경할_수_있다 {

        @DisplayName("전시할 수 있다")
        @Test
        void 전시할_수_있다() {
            final Menu menu = new Menu(
                UUID.randomUUID(),
                name,
                1_000,
                menuGroupId,
                false,
                천원메뉴상품
            );

            assertDoesNotThrow(() -> menu.display());
        }

        @DisplayName("숨길 수 있다")
        @Test
        void 숨길_수_있다() {
            final Menu menu = new Menu(
                UUID.randomUUID(),
                name,
                1_000,
                menuGroupId,
                true,
                천원메뉴상품
            );

            assertDoesNotThrow(() -> menu.hide());
        }
    }
}

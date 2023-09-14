package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.tobe.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.Fixtures.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    private static final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    @Test
    @DisplayName("메뉴를 생성할 수 있다")
    void create() {
        Menu actual = createMenu();

        assertThat(actual).isNotNull();
        assertThat(actual.getId()).isNotNull();
    }

    @Test
    @DisplayName("메뉴에 속한 상품의 합을 계산한다")
    void getSumOfMenuProductPrice() {
        Menu menu = createMenu();
        BigDecimal actual = menu.getSumOfMenuProductPrice();

        assertThat(actual).isEqualTo(BigDecimal.valueOf(16_000L));
    }

    @Test
    @DisplayName("메뉴를 비노출한다")
    void hide() {
        Menu menu = createMenu();
        menu.hide();

        assertThat(menu.isDisplayed()).isFalse();
    }

    @Test
    @DisplayName("메뉴를 노출한다")
    void display() {
        Menu menu = createMenu(false);
        menu.display();

        assertThat(menu.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("메뉴를 노출할 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높다면 에러를 발생시킨다")
    void displayFails() {
        Menu menu = createMenu(17_000L, false);
        assertThatThrownBy(() -> menu.display())
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("메뉴의 가격을 변경한다")
    void changePrice() {
        Menu menu = createMenu();
        menu.changePrice(BigDecimal.valueOf(14_000L));
        assertThat(menu.getBigDecimalPrice()).isEqualTo(BigDecimal.valueOf(14_000L));
    }

    @Test
    @DisplayName("메뉴의 가격을 변경할 때 메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높다면 에러를 발생시킨다")
    void changePriceFails() {
        Menu menu = createMenu();
        assertThatThrownBy(() -> menu.changePrice(BigDecimal.valueOf(17_000L)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Menu createMenu() {
        return createMenu(15_000L, true);
    }

    private static Menu createMenu(boolean displayed) {
        return createMenu(15_000L, displayed);
    }

    public static Menu createMenu(long price) {
        return createMenu(price, true);
    }

    public static Menu createMenu(long price, boolean displayed) {
        // TODO: 픽스쳐 타입 바꾼 후엔 수정할 것
        return Menu.create(
            UUID.randomUUID(),
            "후라이드+후라이드",
            BigDecimal.valueOf(price),
            UUID.randomUUID(),
            displayed,
            List.of(new MenuProduct(1L, product(), 1L, UUID.randomUUID())),
            purgomalumClient::containsProfanity
        );
    }
}

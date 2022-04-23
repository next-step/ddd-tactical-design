package kitchenpos.menus.tobe.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import kitchenpos.products.infra.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static kitchenpos.TobeFixtures.newMenuGroup;
import static kitchenpos.TobeFixtures.newMenuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

class MenuTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    private final MenuProduct mp1 = newMenuProduct("상품1", 1000L);
    private final MenuProduct mp2 = newMenuProduct("상품2", 2000L);
    private final MenuProduct mp3 = newMenuProduct("상품3", 3000L);

    private final List<MenuProduct> menuProducts = Collections.unmodifiableList(Arrays.asList(mp1, mp2, mp3));

    private final MenuGroup mg = newMenuGroup("메뉴 그룹");

    @Test
    @DisplayName("메뉴 상품이 비어 있으면 메뉴 생성 실패")
    void createMenuFail01() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Menu.create("DONT-CARE", purgomalumClient, 1000L, null, true, null))
            .withMessageContaining("메뉴 상품");
    }

    @ParameterizedTest
    @ValueSource(longs = { 0L, -1000L, -1000000L})
    @DisplayName("가격이 0 이하면 생성 실패")
    void createMenuFail02(long price) {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Menu.create("DONT-CARE", purgomalumClient, price, null, true, null))
            .withMessageContaining("가격");
    }

    @ParameterizedTest
    @ValueSource(strings = { "     ", "욕설", "비속어" })
    @NullAndEmptySource
    @DisplayName("이름이 비어있거나 비속어를 사용했으면 생성 실패")
    void createMenuFail03(String name) {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Menu.create(name, purgomalumClient, 1000L, null, true, null))
            .withMessageContaining("이름");
    }

    @Test
    @DisplayName("메뉴 그룹이 비어 있으면 메뉴 생성 실패")
    void createMenuFail04() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Menu.create("DONT-CARE", purgomalumClient, 1000L, null, true, menuProducts))
            .withMessageContaining("메뉴 그룹");
    }

    @Test
    @DisplayName("메뉴의 가격이 메뉴 상품 가격의 합보다 크면 메뉴 생성 실패")
    void createMenuFail05() {
        Long price = mp1.getPrice() + mp2.getPrice() + mp3.getPrice() + 1_000L;
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Menu.create("DONT-CARE", purgomalumClient, price, mg, true, menuProducts))
            .withMessageContaining("가격");
    }

    @Test
    @DisplayName("메뉴 생성 성공")
    void createMenuSuccess() {
        Long price = mp1.getPrice() + mp2.getPrice() + mp3.getPrice();
        Menu.create("DONT-CARE", purgomalumClient, price, mg, true, menuProducts);
    }

    @ParameterizedTest
    @ValueSource(longs = { 0L, -1000L, -1000000L})
    @DisplayName("메뉴의 가격은 0원 이하로 변경 불가능")
    void modifyPriceFail01(long price) {
        // given
        Menu menu = Menu.create("DONT-CARE", purgomalumClient, 1000L, mg, true, menuProducts);

        // when
        assertThatIllegalArgumentException().isThrownBy(() -> menu.modifyPrice(price));
    }

    @Test
    @DisplayName("메뉴의 가격 변경 시 메뉴 상품 가격의 합보다 크면 가격 정상 반영 및 비전시 상태로 변경")
    void modifyPriceSuccess01() {
        // given
        Long price = mp1.getPrice() + mp2.getPrice() + mp3.getPrice() + 10_000L;
        Menu menu = Menu.create("DONT-CARE", purgomalumClient, 1000L, mg, true, menuProducts);

        // when
        menu.modifyPrice(price);

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }

    @ParameterizedTest
    @ValueSource(longs = { 1000L, 2000L, 3000L, 6000L })
    @DisplayName("메뉴의 가격 변경 시 메뉴 상품 가격의 합보다 같거나 작으면 가격 정상 반영")
    void modifyPriceSuccess02(long price) {
        // given
        boolean expected = true;
        Menu menu = Menu.create("DONT-CARE", purgomalumClient, 1000L, mg, expected, menuProducts);

        // when
        menu.modifyPrice(price);

        // then
        assertThat(menu.isDisplayed()).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(longs = { 7000L, 8000L, 20000L })
    @DisplayName("메뉴의 가격이 메뉴 상품 가격의 합보다 크면 메뉴의 상태를 전시 상태로 변경 불가능")
    void displayMenuFail(long price) {
        // given
        Menu menu = Menu.create("DONT-CARE", purgomalumClient, 1000L, mg, false, menuProducts);
        menu.modifyPrice(price);

        // when
        assertThatIllegalStateException().isThrownBy(menu::displayMenu);
    }

    @ParameterizedTest
    @ValueSource(longs = { 1000L, 2000L, 3000L })
    @DisplayName("메뉴의 가격이 메뉴 상품 가격의 합보다 작거나 같을 때 메뉴의 상태를 전시 상태로 변경 가능")
    void displayMenuSuccess(long price) {
        // given
        Menu menu = Menu.create("DONT-CARE", purgomalumClient, 1000L, mg, false, menuProducts);
        menu.modifyPrice(price);

        // when
        menu.displayMenu();

        // then
        assertThat(menu.isDisplayed()).isTrue();
    }

    @Test
    @DisplayName("메뉴를 비전시 상태로 변경하는건 조건 없이 가능")
    void hideMenuSuccess() {
        // given
        Menu menu = Menu.create("DONT-CARE", purgomalumClient, 1000L, mg, true, menuProducts);

        // when
        menu.hideMenu();

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }
}

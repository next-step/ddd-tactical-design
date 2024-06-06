package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct;
import kitchenpos.share.domain.FakeProfanities;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class TobeMenuTest {

    @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
    @Test
    void case_1() {
        // given
        int price = -1;

        // when
        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> createTobeMenu(price));
    }

    @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
    @Test
    void case_2() {
        // given
        String name = "비속어";

        // when
        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> createTobeMenu(name));
    }

    @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다.")
    @Test
    void case_3() {
        // given
        var emptyMenuProducts = new ArrayList<TobeMenuProduct>();

        // when
        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> createTobeMenu(emptyMenuProducts));
    }

    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다.")
    @Test
    void case_4() {
        // given
        var menuProduct = new TobeMenuProduct(1, 5_000, UUID.randomUUID());
        var menuProducts = List.of(menuProduct);

        // when
        // then
        Assertions.assertThatIllegalStateException()
                .isThrownBy(() -> createTobeMenu(menuProducts));
    }

    @DisplayName("메뉴의 가격이 올바르지 않으면 변경할 수 없다. - 메뉴의 가격은 0원 이상이어야 한다.")
    @Test
    void case_5() {
        // given
        var menu = createTobeMenu(10_000);

        // when
        // then
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> menu.changePrice(-1));
    }

    private TobeMenu createTobeMenu(int price) {
        var name = "후라이드";
        var profanities = new FakeProfanities("비속어");
        var menuGroupId = UUID.randomUUID();
        var menuProducts = List.of(new TobeMenuProduct(1, 10_000, UUID.randomUUID()));

        return TobeMenu.of(name, price, profanities, menuGroupId, menuProducts);
    }

    private TobeMenu createTobeMenu(String name) {
        var price = 10_000;
        var profanities = new FakeProfanities("비속어");
        var menuGroupId = UUID.randomUUID();
        var menuProducts = List.of(new TobeMenuProduct(1, 10_000, UUID.randomUUID()));

        return TobeMenu.of(name, price, profanities, menuGroupId, menuProducts);
    }

    private TobeMenu createTobeMenu(List<TobeMenuProduct> menuProducts) {
        var name = "후라이드";
        var price = 10_000;
        var profanities = new FakeProfanities("비속어");
        var menuGroupId = UUID.randomUUID();

        return TobeMenu.of(name, price, profanities, menuGroupId, menuProducts);
    }

}

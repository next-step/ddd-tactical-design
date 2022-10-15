package kitchenpos.menus.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static kitchenpos.Fixtures.menuProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MenuTest {

    private final PurgomalumClient purgomalumClient = new FakePurgomalumClient();

    private String name;
    private String groupName;
    private MenuName menuName;
    private MenuGroupName menuGroupName;
    private MenuPrice menuPrice;
    private MenuProducts menuProducts;

    @BeforeEach
    void setup() {
        name = "메뉴 이름";
        groupName = "메뉴 그룹 이름";
        menuName = new MenuName(name, purgomalumClient);
        menuGroupName = new MenuGroupName(groupName);
        menuPrice = new MenuPrice(BigDecimal.valueOf(15000));
        menuProducts = new MenuProducts(List.of(menuProduct()));
    }

    @Test
    @DisplayName("메뉴를 생성한다.")
    void createMenu() {
        // when
        Menu menu = new Menu(menuName, menuPrice, new MenuGroup(menuGroupName), true, menuProducts);

        // then
        assertAll(
                () -> assertThat(menu.nameValue()).isEqualTo(name),
                () -> assertThat(menu.menuGroup().nameValue()).isEqualTo(groupName),
                () -> assertThat(menu.priceValue()).isEqualTo(menuPrice.price()),
                () -> assertThat(menu.menuProducts()).isEqualTo(menuProducts)
        );
    }

    @Test
    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    void displayAvailabilityCheck() {
        // given
        Menu menu = new Menu(menuName, menuPrice, new MenuGroup(menuGroupName), true, menuProducts);
        // when

        // then
        assertThatThrownBy(() -> menu.displayAvailabilityCheck(BigDecimal.valueOf(14000)))
                .isInstanceOf(IllegalStateException.class);
    }


}

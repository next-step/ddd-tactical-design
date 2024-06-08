package kitchenpos.menus.tobe.domain;

import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.infra.PurgomalumClient;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TobeMenuTest {

    private PurgomalumClient purgomalumClient;

    @BeforeEach
    void setUp() {
        purgomalumClient = new FakePurgomalumClient();
    }

    @Test
    @org.junit.jupiter.api.DisplayName("메뉴의 가격은 메뉴상품의 가격합보다 적거나 같아야한다.")
    void create() {
        // given
        DisplayName name = DisplayName.of("후라이드 치킨", purgomalumClient);
        MenuPrice menuPrice = MenuPrice.of(BigDecimal.valueOf(21000L));
        UUID menuGroup = UUID.randomUUID();

        Displayed displayed = Displayed.DISPLAYED;

        final BigDecimal price = BigDecimal.valueOf(10000L);
        MenuProducts menuProducts = MenuProducts.of(
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L),
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L)
        );

        // when
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> new TobeMenu(UUID.randomUUID(), name, menuPrice, menuGroup, displayed, menuProducts))
                .withMessage("메뉴의 가격은 메뉴에 속한 상품의 가격보다 적거나 같아야합니다.");
    }

    @Test
    @org.junit.jupiter.api.DisplayName("상품의 가격이 변경된 후 메뉴상품과 비교 후  Displayed 상태가 변경될 수 있다.")
    void updateDisplayStatusOnPriceChange() {
        // given
        DisplayName name = DisplayName.of("후라이드 치킨", purgomalumClient);
        MenuPrice menuPrice = MenuPrice.of(BigDecimal.valueOf(20000L));
        UUID menuGroup = UUID.randomUUID();

        Displayed displayed = Displayed.DISPLAYED;

        final BigDecimal price = BigDecimal.valueOf(10000L);
        MenuProducts menuProducts = MenuProducts.of(
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L),
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L)
        );

        TobeMenu menu = new TobeMenu(UUID.randomUUID(), name, menuPrice, menuGroup, displayed, menuProducts);

        // when
        menu.changePrice(BigDecimal.valueOf(21000L));
        menu.updateDisplayStatusOnPriceChange();

        // then
        assertEquals(Displayed.HIDDEN, menu.displayed());
    }

    @Test
    @org.junit.jupiter.api.DisplayName("메뉴의 가격을 변경할 수 있다.")
    void changePrice() {
        // given
        DisplayName name = DisplayName.of("후라이드 치킨", purgomalumClient);
        MenuPrice menuPrice = MenuPrice.of(BigDecimal.valueOf(20000L));
        UUID menuGroup = UUID.randomUUID();
        Displayed displayed = Displayed.DISPLAYED;

        final BigDecimal price = BigDecimal.valueOf(10000L);
        MenuProducts menuProducts = MenuProducts.of(
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L),
                TobeMenuProduct.create(UUID.randomUUID(), price, 1L)
        );

        TobeMenu menu = new TobeMenu(UUID.randomUUID(), name, menuPrice, menuGroup, displayed, menuProducts);

        // when
        menu.changePrice(BigDecimal.valueOf(19000L));

        // then
        assertEquals(BigDecimal.valueOf(19000L), menu.price());
    }

}

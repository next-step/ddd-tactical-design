package kitchenpos.eatinorders.tobe.domain.model;

import kitchenpos.eatinorders.tobe.domain.fixture.OrderLineItemFixture;
import kitchenpos.eatinorders.tobe.domain.validator.OrderLineItemValidator;
import kitchenpos.menus.tobe.domain.fixture.MenuFixture;
import kitchenpos.menus.tobe.domain.model.Menu;
import kitchenpos.menus.tobe.domain.repository.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class OrderLineItemTest {

    private MenuRepository menuRepository;
    private OrderLineItemValidator orderTableValidator;

    @BeforeEach
    void setUp() {
        this.menuRepository = new InMemoryMenuRepository();
        this.orderTableValidator = new OrderLineItemValidator(menuRepository);
    }

    @DisplayName("생성 검증")
    @Test
    void create() {
        Menu menu = menuRepository.save(MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L));

        Assertions.assertDoesNotThrow(() -> OrderLineItemFixture.ORDER_LINE_ITEM_FIXTURE(19000L, 2L, menu.getId(), orderTableValidator));
    }

    @DisplayName("메뉴와 일치하지 않는 가격으로 생성 - 메뉴가 노출되고 있으며 판매되는 메뉴 가격과 일치하면 `Order`가 생성된다.")
    @Test
    void invalidPriceCreate() {
        Menu menu = menuRepository.save(MenuFixture.MENU_FIXTURE(18000L, "두마리후라이드", 10000L, 2L));

        assertThatThrownBy(() -> OrderLineItemFixture.ORDER_LINE_ITEM_FIXTURE(19000L, 2L, menu.getId(), orderTableValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("노출중이 아닌 메뉴로 생성 - 메뉴가 노출되고 있으며 판매되는 메뉴 가격과 일치하면 `Order`가 생성된다.")
    @Test
    void invalidMenuDisplayCreate() {
        Menu menu = menuRepository.save(MenuFixture.MENU_FIXTURE(19000L, "두마리후라이드", 10000L, 2L));
        menu.hide();

        assertThatThrownBy(() -> OrderLineItemFixture.ORDER_LINE_ITEM_FIXTURE(19000L, 2L, menu.getId(), orderTableValidator))
                .isInstanceOf(IllegalArgumentException.class);
    }

}

package kitchenpos.eatinorders.tobe.domain;

import static kitchenpos.eatinorders.tobe.OrderFixtures.eatInOrder;
import static kitchenpos.eatinorders.tobe.OrderFixtures.menuDTO;
import static kitchenpos.eatinorders.tobe.OrderFixtures.orderLineItem;
import static kitchenpos.eatinorders.tobe.OrderFixtures.price;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

import java.util.List;
import kitchenpos.eatinorders.tobe.application.FakeMenuServerClient;
import kitchenpos.eatinorders.tobe.domain.exception.ConsistencyMenuException;
import kitchenpos.eatinorders.tobe.domain.exception.InvalidOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import kitchenpos.eatinorders.tobe.dto.MenuDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OrderLineItemCreateServiceTest {

    private OrderLineItemCreateService orderLineItemCreateService;
    private MenuDTO chickenOfFried;
    private MenuDTO chickenOfSpicy;

    @BeforeEach
    void setUp() {
        chickenOfFried = menuDTO("후라이드 치킨", 16_000);
        chickenOfSpicy = menuDTO("양념 치킨", 15_000);
        List<MenuDTO> menus = List.of(
                chickenOfFried,
                chickenOfSpicy
        );
        MenuServerClient menuServerClient = new FakeMenuServerClient(menus);
        orderLineItemCreateService = new OrderLineItemCreateService(menuServerClient);
    }

    @DisplayName("주문 항목에 필요한 메뉴 정보를 합친다.")
    @Test
    void saveMenu() {
        DisplayedMenu fired = chickenOfFried.toMenu();
        DisplayedMenu spicy = chickenOfSpicy.toMenu();
        EatInOrder request = eatInOrder(
                orderLineItem(1, fired),
                orderLineItem(2, spicy)
        );

        orderLineItemCreateService.saveMenu(request);

        OrderLineItems actual = request.getOrderLineItems();
        assertThat(actual.getValues())
                .isNotEmpty()
                .map(OrderLineItem::getMenu)
                .contains(fired, spicy);
    }

    @DisplayName("에러 케이스")
    @Nested
    class ErrorCaseTest {

        @DisplayName("요청한 주문 항목의 메뉴와 "
                + "실제 등록된 전시 메뉴가 다를 경우 예외가 발생한다.")
        @Test
        void error1() {
            DisplayedMenu fired = chickenOfFried.toMenu();
            EatInOrder request = eatInOrder(
                    orderLineItem(1, fired)
            );

            assertThatExceptionOfType(ConsistencyMenuException.class)
                    .isThrownBy(() -> orderLineItemCreateService.saveMenu(request));
        }

        @DisplayName("요청한 주문 항목의 가격과 "
                + "실제 등록된 전시 메뉴의 가격이 다를 경우 예외가 발생한다.")
        @Test
        void error2() {
            DisplayedMenu fired = chickenOfFried.toMenu();
            DisplayedMenu spicy = changeMenuPrice(chickenOfSpicy, 16_000);

            EatInOrder request = eatInOrder(
                    orderLineItem(1, fired),
                    orderLineItem(1, spicy)
            );

            assertThatExceptionOfType(InvalidOrderLineItemPrice.class)
                    .isThrownBy(() -> orderLineItemCreateService.saveMenu(request));
        }

        private DisplayedMenu changeMenuPrice(MenuDTO menuDTO, int price) {
            return new MenuDTO(
                    menuDTO.getId(),
                    menuDTO.getName(),
                    menuDTO.getPrice().add(price(price))
            ).toMenu();
        }
    }
}

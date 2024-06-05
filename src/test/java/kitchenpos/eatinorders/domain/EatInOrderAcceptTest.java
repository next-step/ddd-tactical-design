package kitchenpos.eatinorders.domain;

import kitchenpos.ToBeFixtures;
import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.service.EatInOrderAccept;
import kitchenpos.menus.domain.FakeMenuRepository;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@DisplayName("주문 ACL 서비스 테스트")
public class EatInOrderAcceptTest {
    private OrderTableRepository orderTableRepository;
    private MenuRepository menuRepository;
    private EatInOrderAccept orderAccept;
    private ToBeFixtures toBeFixtures;

    @BeforeEach
    void setUp() {
        initializeRepository();
        orderAccept = new EatInOrderAccept(orderTableRepository, menuRepository);
        toBeFixtures = new ToBeFixtures();
    }

    private void initializeRepository() {
        orderTableRepository = new FakeOrderTableRepository();
        menuRepository = new FakeMenuRepository();
    }

    @Test
    @DisplayName("주문을 생성한다.")
    void create() {
        OrderLineItems orderLineItems = createDefaultOrderLineItems();
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(orderLineItems, 주문_테이블.getId());

        orderAccept.checkRequiredList(매장_식사);

        Assertions.assertThat(매장_식사.getId()).isNotNull();
    }

    @Test
    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    void create_exception_nonMenu() {
        OrderLineItem 주문_항목 = EatInOrderFixture.orderLineItemOf(1, BigDecimal.ONE, UUID.randomUUID());
        OrderLineItems orderLineItems = new OrderLineItems(List.of(주문_항목));
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(orderLineItems, 주문_테이블.getId());

        Assertions.assertThatThrownBy(
                () -> orderAccept.checkRequiredList(매장_식사)
        ).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("숨겨진 메뉴는 주문할 수 없다.")
    void create_exception_hide_menu() {
        Menu 메뉴_튀김 = toBeFixtures.메뉴_치킨;
        메뉴_튀김.hide();
        menuRepository.save(메뉴_튀김);
        OrderLineItem 주문_항목 = EatInOrderFixture.orderLineItemOf(1, BigDecimal.ONE, 메뉴_튀김.getId());
        OrderLineItems orderLineItems = new OrderLineItems(List.of(주문_항목));
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(orderLineItems, 주문_테이블.getId());

        Assertions.assertThatThrownBy(
                () -> orderAccept.checkRequiredList(매장_식사)
        ).hasMessage("숨긴 메뉴가 존재합니다.");
    }

    @Test
    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    void create_exception_emptyOrderTable() {
        OrderTable 빈_테이블 = orderTableRepository.save(EatInOrderFixture.emptyOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 빈_테이블.getId());

        Assertions.assertThatThrownBy(
                () -> orderAccept.checkRequiredList(매장_식사)
        ).hasMessage("빈 테이블입니다.");
    }

    private OrderLineItems createDefaultOrderLineItems() {
        Menu menu = saveMenuBeforeTest(toBeFixtures.메뉴_치킨);

        OrderLineItem orderLineItem1 = EatInOrderFixture.orderLineItemOf(
                5, BigDecimal.valueOf(10_000), menu.getId()
        );
        OrderLineItem orderLineItem2 = EatInOrderFixture.orderLineItemOf(
                5, BigDecimal.valueOf(10_000), menu.getId()
        );
        return new OrderLineItems(List.of(orderLineItem1, orderLineItem2));
    }

    private Menu saveMenuBeforeTest(Menu menu) {
        menuRepository.save(menu);
        return menu;
    }
}

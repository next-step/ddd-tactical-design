package kitchenpos.eatinorders.application;

import kitchenpos.ToBeFixtures;
import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.domain.FakeEatInOrderRepository;
import kitchenpos.eatinorders.domain.FakeOrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.application.acl.EatInOrderServiceAdapter;
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

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("주문 ACL 서비스 테스트")
public class EatInOrderServiceAdapterTest {
    private ToBeFixtures toBeFixtures;
    private EatInOrderRepository orderRepository;
    private OrderTableRepository orderTableRepository;
    private MenuRepository menuRepository;
    private EatInOrderServiceAdapter eatInOrderServiceAdapter;

    @BeforeEach
    void setUp() {
        toBeFixtures = new ToBeFixtures();
        initializeRepository();
        injectionRepositoryToAdapter();
    }

    private void initializeRepository() {
        orderRepository = new FakeEatInOrderRepository();
        orderTableRepository = new FakeOrderTableRepository();
        menuRepository = new FakeMenuRepository();
    }

    private void injectionRepositoryToAdapter() {
        eatInOrderServiceAdapter = new EatInOrderServiceAdapter(
                orderRepository,
                orderTableRepository,
                menuRepository
        );
    }

    @Test
    @DisplayName("주문을 생성한다.")
    void create() {
        OrderLineItems orderLineItems = createDefaultOrderLineItems();
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 주문 = EatInOrderFixture.eatInOrderOf(orderLineItems, 주문_테이블.getId(), eatInOrderServiceAdapter);

        Assertions.assertThat(주문.getId()).isNotNull();
    }

    @Test
    @DisplayName("메뉴가 없으면 등록할 수 없다.")
    void create_exception_nonMenu() {
        OrderLineItem 주문_항목 = EatInOrderFixture.orderLineItemOf(1, BigDecimal.ONE, UUID.randomUUID());
        OrderLineItems orderLineItems = new OrderLineItems(List.of(주문_항목));
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));

        Assertions.assertThatThrownBy(
                () -> EatInOrderFixture.eatInOrderOf(
                        orderLineItems,
                        주문_테이블.getId(),
                        eatInOrderServiceAdapter)
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

        Assertions.assertThatThrownBy(
                () -> EatInOrderFixture.eatInOrderOf(
                        orderLineItems,
                        주문_테이블.getId(),
                        eatInOrderServiceAdapter)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("빈 테이블에는 매장 주문을 등록할 수 없다.")
    void create_exception_emptyOrderTable() {
        OrderTable 빈_테이블 = orderTableRepository.save(EatInOrderFixture.emptyOrderTableOf("주문_테이블"));

        Assertions.assertThatThrownBy(
                () -> EatInOrderFixture.eatInOrderOf(
                        createDefaultOrderLineItems(),
                        빈_테이블.getId(),
                        eatInOrderServiceAdapter)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("주문 테이블에 있는 모든 주문이 완료되면 빈 테이블로 설정한다.")
    void complete_orderTable () {
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId())
        );
        statusAcceptToServe(매장_식사);

        매장_식사.complete(eatInOrderServiceAdapter);

        assertAll(
                () -> Assertions.assertThat(주문_테이블.getNumberOfGuests()).isZero(),
                () -> Assertions.assertThat(주문_테이블.isNotOccupied()).isTrue()
        );
    }

    @Test
    @DisplayName("주문 테이블에 있는 모든 주문이 완료되지 않으면 빈 테이블로 설정되지 않는다.")
    void complete_orderTable_exception() {
        OrderTable 주문_테이블 = orderTableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId())
        );
        EatInOrder 매장_식사2 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId())
        );
        statusAcceptToServe(매장_식사);

        매장_식사.complete(eatInOrderServiceAdapter);

        Assertions.assertThat(주문_테이블.isOccupied()).isTrue();
    }

    private void statusAcceptToServe(EatInOrder order) {
        order.accept();
        order.serve();
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

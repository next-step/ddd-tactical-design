package kitchenpos.eatinorders.domain;

import kitchenpos.ToBeFixtures;
import kitchenpos.eatinorders.EatInOrderFixture;
import kitchenpos.eatinorders.tobe.domain.constant.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.entity.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.entity.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.entity.OrderTable;
import kitchenpos.eatinorders.tobe.domain.repository.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.repository.OrderTableRepository;
import kitchenpos.eatinorders.tobe.domain.service.EatInOrderCompletePolicy;
import kitchenpos.menus.domain.FakeMenuRepository;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("매장 주문 도메인 서비스 테스트")
public class EatInOrderCompletePolicyTest {
    private EatInOrderRepository orderRepository;
    private OrderTableRepository tableRepository;
    private EatInOrderCompletePolicy completePolicy;

    private MenuRepository menuRepository;
    private ToBeFixtures toBeFixtures;


    @BeforeEach
    void setUp() {
        orderRepository = new FakeEatInOrderRepository();
        tableRepository = new FakeOrderTableRepository();
        completePolicy = new EatInOrderCompletePolicy(orderRepository, tableRepository);

        menuRepository = new FakeMenuRepository();
        toBeFixtures = new ToBeFixtures();
    }

    @Test
    @DisplayName("주문 테이블에 있는 모든 주문이 완료되면 빈 테이블로 설정한다.")
    void complete() {
        OrderTable 주문_테이블 = tableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(EatInOrderStatus.SERVED, createDefaultOrderLineItems(), 주문_테이블.getId())
        );

        completePolicy.complete(매장_식사);

        assertAll(
                () -> Assertions.assertThat(주문_테이블.getNumberOfGuests()).isZero(),
                () -> Assertions.assertThat(주문_테이블.isNotOccupied()).isTrue()
        );
    }

    @Test
    @DisplayName("주문 테이블에 있는 모든 주문이 완료되지 않으면 빈 테이블로 설정되지 않는다.")
    void complete_not_emptyTable() {
        OrderTable 주문_테이블 = tableRepository.save(EatInOrderFixture.sitOrderTableOf("주문_테이블"));
        EatInOrder 매장_식사 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(EatInOrderStatus.SERVED, createDefaultOrderLineItems(), 주문_테이블.getId())
        );
        EatInOrder 매장_식사2 = orderRepository.save(
                EatInOrderFixture.eatInOrderOf(createDefaultOrderLineItems(), 주문_테이블.getId())
        );

        completePolicy.complete(매장_식사);

        Assertions.assertThat(주문_테이블.isOccupied()).isTrue();
    }

    private OrderLineItems createDefaultOrderLineItems() {
        Menu menu = saveMenuBeforeTest(toBeFixtures.메뉴_치킨);

        kitchenpos.eatinorders.tobe.domain.entity.OrderLineItem orderLineItem1 = EatInOrderFixture.orderLineItemOf(
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

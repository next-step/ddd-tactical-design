package kitchenpos.order.eatinorder.domain.model;

import static kitchenpos.TestFixtureFactory.createMenu;
import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static kitchenpos.TestFixtureFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.menu.infra.persistence.FakeMenuRepository;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.common.model.OrderLineItemValidator;
import kitchenpos.order.eatinorder.domain.repository.OrderTableRepository;
import kitchenpos.order.eatinorder.infra.persistence.FakeOrderTableRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EatInOrderFactoryTest {

    @Test
    @DisplayName("주문 내역과 매장 테이블 정보로 매장 주문을 생성한다.")
    void create() {
        // given
        MenuRepository menuRepository = new FakeMenuRepository(new HashMap<>());
        OrderLineItemValidator orderLineItemValidator = new OrderLineItemValidator(menuRepository);
        OrderTableRepository orderTableRepository = new FakeOrderTableRepository(new HashMap<>());
        EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(orderLineItemValidator, orderTableRepository);

        Menu firstMenu = createMenu(createMenuGroup(), createProduct(BigDecimal.valueOf(2000)));
        menuRepository.save(firstMenu);

        OrderTable orderTable = orderTableRepository.save(new OrderTable("1번 테이블", 3, true));
        UUID orderTableId = orderTable.getId();

        OrderLineItem orderLineItem = new OrderLineItem(firstMenu, 2, firstMenu.getId(), BigDecimal.valueOf(8000));

        // when
        EatInOrder eatInOrder = eatInOrderFactory.create(List.of(orderLineItem), orderTableId);

        // then
        assertThat(eatInOrder.getEatInOrderFlow()).isEqualTo(EatInOrderFlow.WAITING);
        assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTableId);
        assertThat(eatInOrder.getOrderLineItems()).isEqualTo(List.of(orderLineItem));
    }
}

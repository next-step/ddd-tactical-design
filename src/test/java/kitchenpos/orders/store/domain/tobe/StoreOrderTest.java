package kitchenpos.orders.store.domain.tobe;

import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.common.domain.OrderType;
import kitchenpos.orders.common.domain.tobe.MenuQuantity;
import kitchenpos.orders.common.domain.tobe.OrderLineItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@DisplayName("StoreOrder")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StoreOrderTest {

    @Test
    void 매장주문을_생성_시_테이블이_점유되어있지않으면_예외를_던진다() {
        assertThatThrownBy(() -> new StoreOrder(createOrderLineItems(), createOrderTable()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 매장주문을_생성한다() {
        OrderTable orderTable = createOrderTable();
        orderTable.sit();

        assertThatNoException().isThrownBy(() -> new StoreOrder(createOrderLineItems(), orderTable));
    }

    @Test
    void 매장주문을_접수한다() {
        StoreOrder storeOrder = createStoreOrder();

        assertThatNoException().isThrownBy(storeOrder::accept);
    }

    @Test
    void 매장주문을_접수시_대기상태가_아니면_예외를_던진다() {
        StoreOrder storeOrder = createStoreOrder();
        storeOrder.accept();

        assertThatThrownBy(storeOrder::accept)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 매장주문을_전달한다() {
        StoreOrder storeOrder = createStoreOrder();
        storeOrder.accept();

        assertThatNoException().isThrownBy(storeOrder::serve);
    }

    @Test
    void 매장주문을_전달시_접수상태가_아니면_예외를_던진다() {
        StoreOrder storeOrder = createStoreOrder();

        assertThatThrownBy(storeOrder::serve)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 매장주문을_완료한다() {
        StoreOrder storeOrder = createStoreOrder();
        storeOrder.accept();
        storeOrder.serve();

        assertThatNoException().isThrownBy(storeOrder::complete);
    }

    @Test
    void 매장주문을_완료하면_테이블은_청소된다() {
        OrderTable targetTable = createOrderTable();
        targetTable.sit();
        StoreOrder storeOrder = new StoreOrder(createOrderLineItems(), targetTable);
        storeOrder.accept();
        storeOrder.serve();

        storeOrder.complete();

        assertThat(targetTable.isOccupied()).isFalse();
    }

    @Test
    void 매장주문을_완료시_전달상태가_아니면_예외를_던진다() {
        StoreOrder storeOrder = createStoreOrder();

        assertThatThrownBy(storeOrder::serve)
                .isInstanceOf(IllegalStateException.class);
    }

    private List<OrderLineItem> createOrderLineItems() {
        Menu menu = MenuFixture.createFriedOnePlusOne(MenuGroupFixture.createChicken(), ProductFixture.createFired());
        return List.of(new OrderLineItem(menu, new MenuQuantity(OrderType.EAT_IN, 2)));
    }

    private OrderTable createOrderTable() {
        return new OrderTable("1번테이블");
    }

    private StoreOrder createStoreOrder() {
        OrderTable orderTable = createOrderTable();
        orderTable.sit();
        return new StoreOrder(createOrderLineItems(), orderTable);
    }
}
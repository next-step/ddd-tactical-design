package kitchenpos.orders.store.domain.tobe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kitchenpos.fixture.OrderTableFixture;
import kitchenpos.fixture.StoreOrderFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("StoreOrder")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StoreOrderTest {

    @Test
    void 매장주문을_생성_시_테이블이_점유되어있지않으면_예외를_던진다() {
        assertThatThrownBy(() -> new StoreOrder(StoreOrderFixture.createOrderLineItems(),
                OrderTableFixture.createNumber1()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 매장주문을_생성한다() {
        OrderTable orderTable = OrderTableFixture.createNumber1();
        orderTable.sit();

        assertThatNoException().isThrownBy(
                () -> new StoreOrder(StoreOrderFixture.createOrderLineItems(), orderTable));
    }

    @Test
    void 매장주문을_접수한다() {
        StoreOrder storeOrder = StoreOrderFixture.createStoreOrder();

        assertThatNoException().isThrownBy(storeOrder::accept);
    }

    @Test
    void 매장주문을_접수시_대기상태가_아니면_예외를_던진다() {
        StoreOrder storeOrder = StoreOrderFixture.createStoreOrder();
        storeOrder.accept();

        assertThatThrownBy(storeOrder::accept)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 매장주문을_전달한다() {
        StoreOrder storeOrder = StoreOrderFixture.createStoreOrder();
        storeOrder.accept();

        assertThatNoException().isThrownBy(storeOrder::serve);
    }

    @Test
    void 매장주문을_전달시_접수상태가_아니면_예외를_던진다() {
        StoreOrder storeOrder = StoreOrderFixture.createStoreOrder();

        assertThatThrownBy(storeOrder::serve)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 매장주문을_완료한다() {
        StoreOrder storeOrder = StoreOrderFixture.createStoreOrder();
        storeOrder.accept();
        storeOrder.serve();

        assertThatNoException().isThrownBy(storeOrder::complete);
    }

    @Test
    void 매장주문을_완료하면_테이블은_청소된다() {
        OrderTable targetTable = OrderTableFixture.createNumber1();
        targetTable.sit();
        StoreOrder storeOrder = new StoreOrder(StoreOrderFixture.createOrderLineItems(),
                targetTable);
        storeOrder.accept();
        storeOrder.serve();

        storeOrder.complete();

        assertThat(targetTable.isOccupied()).isFalse();
    }

    @Test
    void 매장주문을_완료시_전달상태가_아니면_예외를_던진다() {
        StoreOrder storeOrder = StoreOrderFixture.createStoreOrder();

        assertThatThrownBy(storeOrder::serve)
                .isInstanceOf(IllegalStateException.class);
    }
}
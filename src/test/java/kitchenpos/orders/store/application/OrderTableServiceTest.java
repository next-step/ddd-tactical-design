package kitchenpos.orders.store.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.fake.InMemoryMenuGroupRepository;
import kitchenpos.fake.InMemoryMenuRepository;
import kitchenpos.fake.InMemoryOrderTableRepository;
import kitchenpos.fake.InMemoryProductRepository;
import kitchenpos.fake.InMemoryStoreOrderRepository;
import kitchenpos.fixture.MenuFixture;
import kitchenpos.fixture.MenuGroupFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.fixture.StoreOrderFixture;
import kitchenpos.menugroups.domain.MenuGroupRepository;
import kitchenpos.menugroups.domain.tobe.MenuGroup;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.tobe.Menu;
import kitchenpos.orders.store.domain.OrderTableRepository;
import kitchenpos.orders.store.domain.StoreOrderRepository;
import kitchenpos.orders.store.domain.tobe.NumberOfGuests;
import kitchenpos.orders.store.domain.tobe.OrderTable;
import kitchenpos.orders.store.domain.tobe.OrderTableName;
import kitchenpos.orders.store.domain.tobe.StoreOrder;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.domain.tobe.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("OrderTableService")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderTableServiceTest {

    private StoreOrderRepository storeOrderRepository = new InMemoryStoreOrderRepository();

    private MenuRepository menuRepository = new InMemoryMenuRepository();

    private MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();

    private ProductRepository productRepository = new InMemoryProductRepository();

    private OrderTableRepository orderTableRepository = new InMemoryOrderTableRepository();

    private OrderTableService orderTableService;

    @BeforeEach
    void setUp() {
        orderTableService = new OrderTableService(orderTableRepository, storeOrderRepository);
    }

    @Test
    void 테이블을_생성한다() {
        assertThatNoException().isThrownBy(
                () -> orderTableService.create(new OrderTableName("1번테이블")));
    }

    @Test
    void 테이블이름이_비어있으면_예외를던진다() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> orderTableService.create(new OrderTableName("")));
    }

    @Test
    void 먹고가는_손님이_오면_테이블점유를_체크한다() {
        OrderTable saved = orderTableService.create(new OrderTableName("1번테이블"));

        OrderTable actual = orderTableService.sit(saved.getId());

        assertThat(actual.isOccupied()).isTrue();
    }

    @Test
    void 주문이_완료된_테이블을_치울_수_있다() {
        OrderTable saved = orderTableService.create(new OrderTableName("1번테이블"));
        orderTableService.sit(saved.getId());
        createCompleteOrder(saved);

        OrderTable actual = orderTableService.clear(saved.getId());

        assertAll(() -> assertThat(actual.isOccupied()).isFalse(),
                () -> assertThat(actual.getNumberOfGuests()).isZero());
    }

    @Test
    void 주문이_완료되지_않은_테이블을_치우면_예외를던진다() {
        OrderTable saved = orderTableService.create(new OrderTableName("1번테이블"));
        orderTableService.sit(saved.getId());
        createStoreOrder(saved);

        assertThatThrownBy(() -> orderTableService.clear(saved.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    private void createCompleteOrder(OrderTable orderTable) {
        StoreOrder storeOrder = createStoreOrder(orderTable);
        storeOrder.accept();
        storeOrder.serve();
        storeOrder.complete();
    }

    private StoreOrder createStoreOrder(OrderTable orderTable) {
        MenuGroup chickenMenuGroup = menuGroupRepository.save(MenuGroupFixture.createChicken());
        Product friedProduct = productRepository.save(ProductFixture.createFired());
        Menu friedMenu = menuRepository.save(
                MenuFixture.createFriedOnePlusOne(chickenMenuGroup, friedProduct));
        return storeOrderRepository.save(StoreOrderFixture.createStoreOrder(friedMenu, orderTable));
    }

    @Test
    void 점유되어있는_테이블의_손님수를_변경할_수_있다() {
        OrderTable saved = orderTableService.create(new OrderTableName("1번테이블"));
        orderTableService.sit(saved.getId());

        OrderTable actual = orderTableService.changeNumberOfGuests(saved.getId(),
                new NumberOfGuests(4));

        assertThat(actual.getNumberOfGuests()).isEqualTo(4);
    }

    @Test
    void 점유되어있지_않은_테이블의_손님수를_변경하면_예외를던진다() {
        OrderTable saved = orderTableService.create(new OrderTableName("1번테이블"));

        assertThatIllegalStateException().isThrownBy(
                () -> orderTableService.changeNumberOfGuests(saved.getId(), new NumberOfGuests(4)));
    }

    @Test
    void 테이블의_손님수를_마이너스로_변경하면_예외를던진다() {
        OrderTable saved = orderTableService.create(new OrderTableName("1번테이블"));
        orderTableService.sit(saved.getId());

        assertThatIllegalArgumentException().isThrownBy(
                () -> orderTableService.changeNumberOfGuests(saved.getId(),
                        new NumberOfGuests(-10)));
    }

    @Test
    void 모든_테이블_목록을_볼_수_있다() {
        orderTableService.create(new OrderTableName("1번테이블"));
        orderTableService.create(new OrderTableName("2번테이블"));

        List<OrderTable> actual = orderTableService.findAll();

        assertThat(actual).hasSize(2);
    }
}
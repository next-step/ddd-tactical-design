package kitchenpos.eatinorders.tobe.domain.order;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.fake.FakeOrderTableReader;
import kitchenpos.eatinorders.tobe.domain.fake.FakeOrderedMenuReader;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderTable;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderTableReader;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderedMenu;
import kitchenpos.eatinorders.tobe.domain.order.createsupporter.OrderedMenuReader;
import kitchenpos.eatinorders.tobe.domain.order.dto.OrderCreateCommand;
import kitchenpos.eatinorders.tobe.domain.order.dto.OrderLineItemCreateCommand;
import kitchenpos.supports.domain.tobe.OrderType;
import kitchenpos.supports.domain.tobe.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class EatInOrderCreatorTest {

  private EatInOrderCreator eatInOrderCreator;
  private OrderedMenuReader orderedMenuReader;
  private OrderTableReader orderTableReader;

  @BeforeEach
  void setUp() {
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of());
    this.orderTableReader = new FakeOrderTableReader(List.of());
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
  }

  @DisplayName("주문을 생성할 수 있다.")
  @Test
  public void create() throws Exception {
    OrderedMenu orderedMenu = new OrderedMenu(UUID.randomUUID(), true,
        new Price(new BigDecimal(500)));
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), true);
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of(orderedMenu));
    this.orderTableReader = new FakeOrderTableReader(List.of(orderTable));
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
    OrderLineItemCreateCommand orderLineItemCreateCommand = new OrderLineItemCreateCommand(
        orderedMenu.getMenuId(), new EatInOrderLineItemQuantity(2L),
        new Price(new BigDecimal(500)));
    OrderCreateCommand orderCreateCommand = new OrderCreateCommand(orderTable.getId(),
        List.of(orderLineItemCreateCommand));

    EatInOrder eatInOrder = eatInOrderCreator.create(orderCreateCommand);
    List<EatInOrderLineItem> orderLineItems = eatInOrder.getOrderLineItems();

    assertAll(
        () -> assertThat(eatInOrder.getId()).isNotNull(),
        () -> assertThat(eatInOrder.getStatus()).isEqualTo(EatInOrderStatus.WAITING),
        () -> assertThat(eatInOrder.getType()).isEqualTo(OrderType.EAT_IN),
        () -> assertThat(eatInOrder.getOrderTableId()).isEqualTo(orderTable.getId()),
        () -> assertThat(orderLineItems).hasSize(1)
    );
  }

  @DisplayName("등록된 주문테이블이어야한다.")
  @Test
  public void failBecauseNotRegisterOrderTable() throws Exception {
    OrderedMenu orderedMenu = new OrderedMenu(UUID.randomUUID(), true,
        new Price(new BigDecimal(500)));
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), true);
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of(orderedMenu));
    this.orderTableReader = new FakeOrderTableReader(List.of(orderTable));
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
    OrderLineItemCreateCommand orderLineItemCreateCommand = new OrderLineItemCreateCommand(
        orderedMenu.getMenuId(), new EatInOrderLineItemQuantity(2L),
        new Price(new BigDecimal(500)));
    OrderCreateCommand orderCreateCommand = new OrderCreateCommand(UUID.randomUUID(),
        List.of(orderLineItemCreateCommand));

    assertThrows(NoSuchElementException.class, () -> eatInOrderCreator.create(orderCreateCommand));
  }

  @DisplayName("주문테이블은 사용처리되어 있어야한다.")
  @Test
  public void failBecauseOrderTableOccupied() throws Exception {
    OrderedMenu orderedMenu = new OrderedMenu(UUID.randomUUID(), true,
        new Price(new BigDecimal(500)));
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), false);
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of(orderedMenu));
    this.orderTableReader = new FakeOrderTableReader(List.of(orderTable));
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
    OrderLineItemCreateCommand orderLineItemCreateCommand = new OrderLineItemCreateCommand(
        orderedMenu.getMenuId(), new EatInOrderLineItemQuantity(2L),
        new Price(new BigDecimal(500)));
    OrderCreateCommand orderCreateCommand = new OrderCreateCommand(orderTable.getId(),
        List.of(orderLineItemCreateCommand));
    assertThatIllegalStateException()
        .isThrownBy(() -> eatInOrderCreator.create(orderCreateCommand));
  }

  @DisplayName("주문항목은 1개 이상이어야한다.")
  @NullAndEmptySource
  @ParameterizedTest
  public void failBecauseNullAndEmptyOrderLineItem(List<OrderLineItemCreateCommand> command)
      throws Exception {
    OrderedMenu orderedMenu = new OrderedMenu(UUID.randomUUID(), true,
        new Price(new BigDecimal(500)));
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), true);
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of(orderedMenu));
    this.orderTableReader = new FakeOrderTableReader(List.of(orderTable));
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
    OrderCreateCommand orderCreateCommand = new OrderCreateCommand(orderTable.getId(),
        command);
    assertThatIllegalArgumentException()
        .isThrownBy(() -> eatInOrderCreator.create(orderCreateCommand));
  }

  @DisplayName("존재하지않는 메뉴는 주문항목에 담을 수 없다.")
  @Test
  public void failBecauseMenuNotExists() throws Exception {
    OrderedMenu orderedMenu = new OrderedMenu(UUID.randomUUID(), true,
        new Price(new BigDecimal(500)));
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), true);
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of(orderedMenu));
    this.orderTableReader = new FakeOrderTableReader(List.of(orderTable));
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
    OrderLineItemCreateCommand orderLineItemCreateCommand = new OrderLineItemCreateCommand(
        UUID.randomUUID(), new EatInOrderLineItemQuantity(2L),
        new Price(new BigDecimal(500)));
    OrderCreateCommand orderCreateCommand = new OrderCreateCommand(orderTable.getId(),
        List.of(orderLineItemCreateCommand));
    assertThatIllegalArgumentException()
        .isThrownBy(() -> eatInOrderCreator.create(orderCreateCommand));
  }

  @DisplayName("공개된 메뉴만 주문항목에 담을 수 있다.")
  @Test
  public void failBecauseMenuHide() throws Exception {
    OrderedMenu orderedMenu = new OrderedMenu(UUID.randomUUID(), false,
        new Price(new BigDecimal(500)));
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), true);
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of(orderedMenu));
    this.orderTableReader = new FakeOrderTableReader(List.of(orderTable));
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
    OrderLineItemCreateCommand orderLineItemCreateCommand = new OrderLineItemCreateCommand(
        orderedMenu.getMenuId(), new EatInOrderLineItemQuantity(2L),
        new Price(new BigDecimal(500)));
    OrderCreateCommand orderCreateCommand = new OrderCreateCommand(orderTable.getId(),
        List.of(orderLineItemCreateCommand));

    assertThatIllegalStateException()
        .isThrownBy(() -> eatInOrderCreator.create(orderCreateCommand));
  }

  @DisplayName("등록된 메뉴의 가격과 주문항목으로 담은 메뉴의 가격은 같아야한다.")
  @Test
  public void failBecauseMenuPriceIncorrect() throws Exception {
    OrderedMenu orderedMenu = new OrderedMenu(UUID.randomUUID(), true,
        new Price(new BigDecimal(600)));
    OrderTable orderTable = new OrderTable(UUID.randomUUID(), true);
    this.orderedMenuReader = new FakeOrderedMenuReader(List.of(orderedMenu));
    this.orderTableReader = new FakeOrderTableReader(List.of(orderTable));
    this.eatInOrderCreator = new EatInOrderCreator(orderedMenuReader, orderTableReader);
    OrderLineItemCreateCommand orderLineItemCreateCommand = new OrderLineItemCreateCommand(
        orderedMenu.getMenuId(), new EatInOrderLineItemQuantity(2L),
        new Price(new BigDecimal(500)));
    OrderCreateCommand orderCreateCommand = new OrderCreateCommand(orderTable.getId(),
        List.of(orderLineItemCreateCommand));

    assertThatIllegalArgumentException()
        .isThrownBy(() -> eatInOrderCreator.create(orderCreateCommand));
  }
}

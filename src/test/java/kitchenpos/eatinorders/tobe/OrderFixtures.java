package kitchenpos.eatinorders.tobe;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.domain.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.domain.OrderLineItems;
import kitchenpos.eatinorders.tobe.domain.OrderTable;
import kitchenpos.eatinorders.tobe.domain.vo.DisplayedMenu;
import kitchenpos.eatinorders.tobe.domain.vo.NumberOfGuests;
import kitchenpos.eatinorders.tobe.dto.MenuDTO;
import kitchenpos.global.vo.Name;
import kitchenpos.global.vo.Price;
import kitchenpos.global.vo.Quantity;
import kitchenpos.products.application.FakePurgomalumClient;

public final class OrderFixtures {

    private OrderFixtures() {
    }

    public static EatInOrder eatInOrder(OrderLineItems items, OrderTable table) {
        return new EatInOrder(items, table);
    }

    public static OrderLineItem orderLineItem(long quantity, DisplayedMenu menu) {
        return new OrderLineItem(
                quantity(quantity),
                menu
        );
    }

    public static OrderLineItems orderLineItems(OrderLineItem... item) {
        return new OrderLineItems(
                List.of(item)
        );
    }

    public static OrderTable orderTable(String name) {
        return OrderTable.create(name(name));
    }

    public static DisplayedMenu menu(String name, long price) {
        return new DisplayedMenu(
                UUID.randomUUID(),
                name(name),
                price(price)
        );
    }

    public static MenuDTO menuDTO(String name, long price) {
        return new MenuDTO(
                UUID.randomUUID(),
                name(name),
                price(price)
        );
    }

    public static NumberOfGuests numberOfGuests(long value) {
        return new NumberOfGuests(quantity(value));
    }

    private static Quantity quantity(long value) {
        return new Quantity(value);
    }

    public static Name name(String value) {
        return new Name(value, new FakePurgomalumClient());
    }

    public static Price price(long value) {
        return new Price(new BigDecimal(value));
    }
}

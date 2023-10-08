package kitchenpos.order.tobe.eatinorder.domain;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLineItemDto;

@Embeddable
public class EatInOrderLineItems {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
        name = "eat_in_order_id",
        nullable = false,
        columnDefinition = "binary(16)"
    )
    private List<EatInOrderLineItem> value;

    protected EatInOrderLineItems() {
    }

    public EatInOrderLineItems(List<EatInOrderLineItem> value) {
        if (Objects.isNull(value) || value.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.value = value;
    }

    public static EatInOrderLineItems from(List<EatInOrderLineItemDto> orderLineItems, MenuClient menuClient) {
        if (Objects.isNull(orderLineItems) || orderLineItems.isEmpty()) {
            throw new IllegalArgumentException();
        }

        var menus = menuClient.getMenusByMenuIds(orderLineItems)
            .stream()
            .collect(Collectors.toMap(Menu::getId, Function.identity()));

        return orderLineItems.stream()
            .map(it -> EatInOrderLineItem.from(it, menus))
            .collect(Collectors.collectingAndThen(Collectors.toList(), EatInOrderLineItems::new));
    }

    public List<EatInOrderLineItem> getValue() {
        return Collections.unmodifiableList(value);
    }
}

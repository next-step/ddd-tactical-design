package kitchenpos.eatinorders.tobe.domain;

import kitchenpos.common.domain.Quantity;
import kitchenpos.eatinorders.tobe.dto.EatInOrderCreationRequest;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Component
public class EatInOrderFactory {
    private static OrderTableRepository orderTableRepository;
    private static MenuRepository menuRepository;

    @Autowired
    public EatInOrderFactory(final OrderTableRepository orderTableRepository, final MenuRepository menuRepository) {
        this.orderTableRepository = orderTableRepository;
        this.menuRepository = menuRepository;
    }

    public static EatInOrder createEatInOrder(final EatInOrderCreationRequest request) {
        OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(() -> new IllegalArgumentException("cannot find order table."));

        if(orderTable.isEmpty()) {
            throw new IllegalStateException("cannot order - order table is empty");
        }

        List<EatInOrderLineItem> eatInOrderLineItems = request.getEatInOrderLineItems()
                .entrySet()
                .stream()
                .map(e -> createEatInOrderLineItem(e.getKey(), e.getValue()))
                .collect(Collectors.toList());

        for(EatInOrderLineItem eatInOrderLineItem : eatInOrderLineItems) {
            Menu menu = menuRepository.findById(eatInOrderLineItem.getMenuId())
                    .orElseThrow(() -> new IllegalArgumentException("cannot make order - cannot find menu."));

            if(!menu.getPrice().equals(eatInOrderLineItem.getPrice())) {
                throw new IllegalArgumentException();
            }
        }

        return new EatInOrder(orderTable, eatInOrderLineItems);
    }

    private static EatInOrderLineItem createEatInOrderLineItem(final UUID menuId, final Quantity quantity) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new IllegalArgumentException("cannot find menu."));
        return new EatInOrderLineItem(menu, quantity);
    }
}

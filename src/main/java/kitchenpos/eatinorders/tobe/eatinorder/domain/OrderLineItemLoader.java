package kitchenpos.eatinorders.tobe.eatinorder.domain;

import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.MenuResponse;
import kitchenpos.eatinorders.tobe.eatinorder.ui.dto.OrderLineItemCreateRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

@Component
public class OrderLineItemLoader {
    public OrderLineItems loadOrderLineItems(final List<OrderLineItemCreateRequest> requests, final List<MenuResponse> menus) {
        verifyMenuSize(requests, menus);
        requests.forEach(request -> verifyMenu(request, findMenu(request, menus)));

        return new OrderLineItems(requests.stream()
                .map(OrderLineItemCreateRequest::toEntity)
                .collect(toList()));
    }

    private void verifyMenuSize(final List<OrderLineItemCreateRequest> requests, final List<MenuResponse> menus) {
        if (menus.size() != requests.size()) {
            throw new IllegalArgumentException("등록되지 않은 메뉴가 있습니다.");
        }
    }

    private void verifyMenu(final OrderLineItemCreateRequest request, final MenuResponse menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴는 주문할 수 없습니다.");
        }
        if (menu.getPrice().compareTo(request.getPrice()) != 0) {
            throw new IllegalArgumentException("주문한 메뉴의 가격은 실제 메뉴 가격과 일치해야합니다.");
        }
    }

    private MenuResponse findMenu(final OrderLineItemCreateRequest request, final List<MenuResponse> menus) {
        return menus.stream()
                .filter(response -> Objects.equals(response.getMenuId(), request.getMenuId()))
                .findAny().orElseThrow(NoSuchElementException::new);
    }
}

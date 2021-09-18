package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.MenuTranslator;
import kitchenpos.eatinorders.tobe.domain.OrderLineItem;
import kitchenpos.eatinorders.tobe.dto.FilteredMenuRequest;
import kitchenpos.eatinorders.tobe.dto.MenuResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ApiMenuTranslator implements MenuTranslator {
    private final RestTemplate restTemplate;

    public ApiMenuTranslator(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public MenuResponse getMenu(final OrderLineItem orderLineItem) {
        final MenuResponse menu = restTemplate.getForObject("http://localhost:8080/api/tobe/menus/" + orderLineItem.getMenuId(), MenuResponse.class);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴입니다.");
        }
        if (menu.getPrice().compareTo(orderLineItem.getPrice()) != 0) {
            throw new IllegalArgumentException("메뉴의 가격과 주문 항목의 가격은 같아야 합니다.");
        }
        return menu;
    }

    @Override
    public List<MenuResponse> getMenus(final List<UUID> menuIds) {
        return Arrays.asList(restTemplate.postForObject("http://localhost:8080/api/tobe/menus/filtered", new FilteredMenuRequest(menuIds), MenuResponse[].class));
    }

    @Override
    public void validateOrderLineItem(final OrderLineItem orderLineItem) {
        final MenuResponse menu = getMenu(orderLineItem);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴는 주문할 수 없습니다.");
        }
    }
}

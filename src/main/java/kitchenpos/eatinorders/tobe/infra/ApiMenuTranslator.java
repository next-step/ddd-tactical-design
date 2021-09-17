package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.MenuTranslator;
import kitchenpos.eatinorders.tobe.dto.FilteredMenuRequest;
import kitchenpos.menus.domain.Menu;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
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
    public Menu getMenu(final UUID menuId, final BigDecimal orderLineItemPrice) {
        final Menu menu = restTemplate.getForObject("http://localhost:8080/api/tobe/menus/" + menuId, Menu.class);
        if (!menu.isDisplayed()) {
            throw new IllegalStateException("숨겨진 메뉴입니다.");
        }
        if (menu.getPrice().compareTo(orderLineItemPrice) != 0) {
            throw new IllegalArgumentException("메뉴의 가격과 주문 항목의 가격은 같아야 합니다.");
        }
        return menu;
    }

    @Override
    public List<Menu> getMenus(final List<UUID> menuIds) {
        final List<Menu> menus = Arrays.asList(restTemplate.postForObject("http://localhost:8080/api/tobe/menus/filtered", new FilteredMenuRequest(menuIds), Menu[].class));
        if (menus.size() != menuIds.size()) {
            throw new IllegalArgumentException("적절하지 않는 menuId 가 배열에 포함되어 있습니다.");
        }
        return menus;
    }
}

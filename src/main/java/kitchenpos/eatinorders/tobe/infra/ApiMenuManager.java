package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.menu.Menu;
import kitchenpos.eatinorders.tobe.domain.menu.MenuManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class ApiMenuManager implements MenuManager {
    private final RestTemplate restTemplate;

    public ApiMenuManager(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Menu getMenu(final UUID menuId) {
        return restTemplate.getForObject("http://localhost:8080/api/tobe/menus/" + menuId, Menu.class);
    }

    @Override
    public List<Menu> getMenus(final List<UUID> menuIds) {
        return Arrays.asList(restTemplate.postForObject("http://localhost:8080/api/tobe/menus/filtered", menuIds, Menu[].class));
    }
}

package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.MenuTranslator;
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
    public MenuResponse getMenu(final UUID menuId) {
        return restTemplate.getForObject("http://localhost:8080/api/tobe/menus/" + menuId, MenuResponse.class);
    }

    @Override
    public List<MenuResponse> getMenus(final List<UUID> menuIds) {
        return Arrays.asList(restTemplate.postForObject("http://localhost:8080/api/tobe/menus/filtered", menuIds, MenuResponse[].class));
    }
}

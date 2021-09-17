package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.MenuGroupTranslator;
import kitchenpos.menus.tobe.dto.MenuGroupResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ApiMenuGroupTranslator implements MenuGroupTranslator {
    private final RestTemplate restTemplate;

    public ApiMenuGroupTranslator(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public MenuGroupResponse getMenuGroup(final UUID menuGroupId) {
        return restTemplate.getForObject("http://localhost:8080/api/tobe/menu-groups/" + menuGroupId, MenuGroupResponse.class);
    }
}

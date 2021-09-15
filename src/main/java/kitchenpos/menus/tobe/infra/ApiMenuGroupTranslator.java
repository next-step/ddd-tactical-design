package kitchenpos.menus.tobe.infra;

import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menus.tobe.domain.MenuGroupTranslator;
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
    public MenuGroup getMenuGroup(final UUID menuGroupId) {
        return restTemplate.getForObject("http://localhost:8080/api/tobe/menu-groups/" + menuGroupId, MenuGroup.class);
    }
}

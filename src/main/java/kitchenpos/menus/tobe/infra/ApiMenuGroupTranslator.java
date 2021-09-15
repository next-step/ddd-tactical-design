package kitchenpos.menus.tobe.infra;

import kitchenpos.common.infra.Profanities;
import kitchenpos.menugroups.domain.MenuGroup;
import kitchenpos.menugroups.domain.MenuGroupName;
import kitchenpos.menugroups.dto.MenuGroupResponse;
import kitchenpos.menus.tobe.domain.MenuGroupTranslator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ApiMenuGroupTranslator implements MenuGroupTranslator {
    private final Profanities profanities;
    private final RestTemplate restTemplate;

    public ApiMenuGroupTranslator(final Profanities profanities, final RestTemplateBuilder restTemplateBuilder) {
        this.profanities = profanities;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public MenuGroup getMenuGroup(final UUID menuGroupId) {
        final MenuGroupResponse response = restTemplate.getForObject("http://localhost:8080/api/tobe/menu-groups/" + menuGroupId, MenuGroupResponse.class);
        return new MenuGroup(response.getId(), new MenuGroupName(response.getName(), profanities));
    }
}

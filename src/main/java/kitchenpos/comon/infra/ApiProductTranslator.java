package kitchenpos.comon.infra;

import kitchenpos.products.tobe.domain.ProductTranslator;
import kitchenpos.products.tobe.dto.ChangeMenuStatusRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ApiProductTranslator implements ProductTranslator {
    private final RestTemplate restTemplate;

    public ApiProductTranslator(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public void changeMenuStatus(final UUID productId) {
        restTemplate.put("http://localhost:8080/api/menus/status", new ChangeMenuStatusRequest(productId));
    }
}

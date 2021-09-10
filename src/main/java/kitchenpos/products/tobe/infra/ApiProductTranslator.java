package kitchenpos.products.tobe.infra;

import kitchenpos.products.tobe.dto.UpdateMenuStatusRequest;
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
    public void updateMenuStatus(final UUID productId) {
        restTemplate.put("http://localhost:8080/api/menus/status", new UpdateMenuStatusRequest(productId));
    }
}

package kitchenpos.eatinordertables.infra;

import kitchenpos.eatinordertables.domain.OrderTranslator;
import kitchenpos.eatinordertables.dto.OrderCompletedResponse;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ApiOrderTranslator implements OrderTranslator {
    private final RestTemplate restTemplate;

    public ApiOrderTranslator(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean isOrderCompleted(final UUID orderTableId) {
        return restTemplate.getForObject(
                String.format("http://localhost:8080/api/tobe/orders/order-tables/%s/completed", orderTableId),
                OrderCompletedResponse.class
        ).isCompleted();
    }
}

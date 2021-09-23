package kitchenpos.eatinorders.tobe.infra;

import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableTranslator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class ApiOrderTableTranslator implements OrderTableTranslator {
    private final RestTemplate restTemplate;

    public ApiOrderTableTranslator(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public OrderTable getOrderTable(final UUID orderTableId) {
        return restTemplate.getForObject("http://localhost:8080/api/tobe/order-tables/" + orderTableId, OrderTable.class);
    }

    @Override
    public void clearOrderTable(final UUID orderTableId) {
        final String uri = String.format("http://localhost:8080/api/tobe/order-tables/%s/clear", orderTableId);
        restTemplate.put(uri, null);
    }
}

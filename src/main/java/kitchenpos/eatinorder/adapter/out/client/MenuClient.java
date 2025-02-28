package kitchenpos.eatinorder.adapter.out.client;

import kitchenpos.eatinorder.adapter.out.client.response.MenuResponse;
import kitchenpos.eatinorder.application.port.out.MenuEatInOrderLineItemMapper;
import kitchenpos.eatinorder.application.service.model.OrderLineItemRequests;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderLineItem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class MenuClient implements MenuEatInOrderLineItemMapper {
    private final static String MENU_API_PATH = "/api/menus";
    private final String host;
    private final RestTemplate restTemplate;

    public MenuClient(
            final RestTemplateBuilder restTemplateBuilder,
            @Value("${menu.url}") final String host
    ) {
        this.host = host;
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<EatInOrderLineItem> toEatInOrderLines(OrderLineItemRequests orderLineItemRequests) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(host + MENU_API_PATH)
                .queryParam("ids", orderLineItemRequests.getMenuIds())
                .build()
                .toUri();

        MenuResponse[] responses = restTemplate.getForObject(uri, MenuResponse[].class);
        validateMenuResponses(orderLineItemRequests, responses);
        return Stream.of(responses)
                .map(menu -> EatInOrderLineItem.of(
                        null,
                        menu.getId(),
                        orderLineItemRequests.getQuantity(menu.getId()),
                        orderLineItemRequests.getPrice(menu.getId()),
                        menu.getPrice(),
                        menu.isDisplayed()
                ))
                .toList();
    }

    private void validateMenuResponses(OrderLineItemRequests orderLineItemRequests, MenuResponse[] responses) {
        if (responses == null) {
            throw new IllegalStateException("메뉴 정보를 조회할 수 없습니다. ids=" + orderLineItemRequests.getMenuIds());
        }

        if (responses.length != orderLineItemRequests.size()) {
            List<UUID> responseIds = Stream.of(responses)
                    .map(MenuResponse::getId)
                    .toList();
            List<UUID> requestIds = orderLineItemRequests.getMenuIds();
            requestIds.removeAll(responseIds);
            throw new IllegalArgumentException("메뉴 정보를 조회할 수 없습니다. 조회할 수 없는 메뉴 ID=" + requestIds);
        }
    }
}

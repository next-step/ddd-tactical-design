package kitchenpos.menu.adapter.out.client;

import kitchenpos.menu.adapter.out.client.response.ProductResponse;
import kitchenpos.menu.application.port.out.MenuProductMapper;
import kitchenpos.menu.domain.model.MenuProduct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@Component
public class ProductClient implements MenuProductMapper {
    private final static String PRODUCT_API_PATH = "/api/products";
    private final String host;
    private final RestTemplate restTemplate;

    public ProductClient(
            final RestTemplateBuilder restTemplateBuilder,
            @Value("${product.url}") final String host
    ) {
        this.restTemplate = restTemplateBuilder.build();
        this.host = host;
    }

    @Override
    public List<MenuProduct> toMenuProducts(Map<UUID, Long> productQuantities) {
        if (productQuantities.isEmpty()) {
            return List.of();
        }

        URI uri = UriComponentsBuilder
                .fromHttpUrl(host + PRODUCT_API_PATH)
                .queryParam("ids", productQuantities.keySet().stream().map(UUID::toString).toArray())
                .build()
                .toUri();

        ProductResponse[] responses = restTemplate.getForObject(uri, ProductResponse[].class);

        if (responses == null) {
            throw new IllegalStateException("상품 정보를 조회할 수 없습니다.");
        }

        return Stream.of(responses)
                .map(response -> new MenuProduct(response.getUUID(), productQuantities.get(response.getUUID()), response.getBigDecimalPrice()))
                .toList();
    }
}

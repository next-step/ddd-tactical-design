package kitchenpos.products.infra;

import java.net.URI;
import kitchenpos.products.domain.ProductProfanityCheckClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ProductPurgomalumClient implements ProductProfanityCheckClient {
    private final RestTemplate restTemplate;

    public ProductPurgomalumClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean containsProfanity(String text) {
        URI url = UriComponentsBuilder.fromUriString("https://www.purgomalum.com/service/containsprofanity")
            .queryParam("text", text)
            .build()
            .toUri();

        return Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
    }
}

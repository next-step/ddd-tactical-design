package kitchenpos.products.infra;

import kitchenpos.common.external.infra.ProfanityChecker;
import kitchenpos.products.tobe.domain.ProductName;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ProductProfanityChecker implements ProductProfanity {
    private final RestTemplate restTemplate;

    public ProductProfanityChecker(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean containsProfanity(final String text) {
        final URI url = UriComponentsBuilder.fromUriString("https://www.purgomalum.com/service/containsprofanity")
            .queryParam("text", text)
            .build()
            .toUri();
        return Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
    }

    @Override
    public void profanityCheck(final String text) {
        if (containsProfanity(text)) {
            throw new IllegalArgumentException();
        }
    }
}

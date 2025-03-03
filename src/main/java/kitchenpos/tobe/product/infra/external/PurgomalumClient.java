package kitchenpos.tobe.product.infra.external;

import kitchenpos.tobe.product.domain.PurgomalumAgent;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class PurgomalumClient implements PurgomalumAgent {

    private final RestTemplate restTemplate;

    private static final String PURGOMALUM_SERVICE_URL = "https://www.purgomalum.com/service/containsprofanity";

    public PurgomalumClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean containsProfanity(final String text) {
        final URI url = UriComponentsBuilder.fromUriString(PURGOMALUM_SERVICE_URL)
                .queryParam("text", text)
                .build()
                .toUri();
        return Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
    }

}

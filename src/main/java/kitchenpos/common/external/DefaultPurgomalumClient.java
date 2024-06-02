package kitchenpos.common.external;

import java.net.URI;
import kitchenpos.common.purgomalum.PurgomalumClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
class DefaultPurgomalumClient implements PurgomalumClient {
    private final RestTemplate restTemplate;

    public DefaultPurgomalumClient(final RestTemplateBuilder restTemplateBuilder) {
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
}

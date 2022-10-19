package kitchenpos.common.profanity.infra;

import java.net.URI;
import kitchenpos.common.profanity.domain.ProfanityDetectService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DefaultPurgomalumClient implements ProfanityDetectService {

    private static final String PURGOMALUM_ENDPOINT = "https://www.purgomalum.com/service/containsprofanity";

    private final RestTemplate restTemplate;

    public DefaultPurgomalumClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean profanityIn(final String text) {
        final URI url = UriComponentsBuilder.fromUriString(PURGOMALUM_ENDPOINT)
            .queryParam("text", text)
            .build()
            .toUri();
        final String result = restTemplate.getForObject(url, String.class);
        return Boolean.parseBoolean(result);
    }
}

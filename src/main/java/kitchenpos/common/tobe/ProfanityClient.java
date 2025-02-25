package kitchenpos.common.tobe;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class ProfanityClient implements Profanities {
    private final RestTemplate restTemplate;

    public ProfanityClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean contains(String text) {
        final URI url = UriComponentsBuilder.fromUriString("https://www.purgomalum.com/service/containsprofanity")
                .queryParam("text", text)
                .build()
                .toUri();
        return Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
    }
}

package kitchenpos.products.infra;

import java.net.URI;
import kitchenpos.products.domain.ProfanityValidator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class DefaultProfanityValidator implements ProfanityValidator {

    private final RestTemplate restTemplate;

    public DefaultProfanityValidator(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public boolean containsProfanity(final String text) {
        final URI url = UriComponentsBuilder.fromUriString(
                        "https://www.purgomalum.com/service/containsprofanity")
                .queryParam("text", text)
                .build()
                .toUri();
        return Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
    }

    @Override
    public void validate(String text) {
        if (containsProfanity(text)) {
            throw new IllegalArgumentException();
        }
    }
}
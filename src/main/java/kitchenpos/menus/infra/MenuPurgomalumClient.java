package kitchenpos.menus.infra;

import java.net.URI;
import kitchenpos.menus.domain.MenuProfanityCheckClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MenuPurgomalumClient implements MenuProfanityCheckClient {
    private final RestTemplate restTemplate;

    public MenuPurgomalumClient(RestTemplateBuilder restTemplateBuilder) {
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

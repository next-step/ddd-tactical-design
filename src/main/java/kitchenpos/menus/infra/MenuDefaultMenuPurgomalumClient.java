package kitchenpos.menus.infra;

import java.net.URI;
import java.util.Objects;
import kitchenpos.menus.domain.menu.MenuPurgomalumClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MenuDefaultMenuPurgomalumClient implements MenuPurgomalumClient {
  private final RestTemplate restTemplate;

  public MenuDefaultMenuPurgomalumClient(final RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Override
  public boolean containsProfanity(final String text) {
    if (Objects.isNull(text) || text.length() <= 0) {
      throw new IllegalArgumentException();
    }

    final URI url =
        UriComponentsBuilder.fromUriString("https://www.purgomalum.com/service/containsprofanity")
            .queryParam("text", text)
            .build()
            .toUri();
    return Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
  }
}

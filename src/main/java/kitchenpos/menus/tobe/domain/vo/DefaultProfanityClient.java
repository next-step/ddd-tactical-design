package kitchenpos.menus.tobe.domain.vo;

import kitchenpos.menus.tobe.domain.vo.ProfanityClient;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class DefaultProfanityClient implements ProfanityClient {

    @Override
    public boolean containsProfanity(final String name) {
        return false;
    }
}

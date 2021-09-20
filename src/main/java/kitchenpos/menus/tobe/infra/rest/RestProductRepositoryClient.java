package kitchenpos.menus.tobe.infra.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.menus.tobe.domain.model.Product;
import kitchenpos.menus.tobe.domain.repository.ProductRepository;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RestProductRepositoryClient implements ProductRepository {

    private static final String REQUEST_KEY = "productIds";
    private static final String URI_STRING = "127.0.0.1:8080/by-ids";
    private final RestTemplate restTemplate;

    public RestProductRepositoryClient(final RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public List<Product> findAllByIdIn(List<UUID> productIds) {
        final URI url = UriComponentsBuilder
                .fromUriString(URI_STRING)
                .build()
                .toUri();

        final MultiValueMap<String, List<UUID>> requestBody = new LinkedMultiValueMap<>();
        requestBody.add(REQUEST_KEY, productIds);
        HttpEntity<MultiValueMap<String, List<UUID>>> request = new HttpEntity<>(requestBody);

        final ResponseEntity<List<Product>> exchange = restTemplate
                .exchange(url, HttpMethod.POST, request, new ParameterizedTypeReference<List<Product>>() {});

        return exchange.getBody();
    }
}

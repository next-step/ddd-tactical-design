package kitchenpos.products.tobe.infra;

import java.net.URI;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import kitchenpos.products.tobe.domain.Profanities;

@Component
public class DefaultProfanities implements Profanities {
	private final RestTemplate restTemplate;

	public DefaultProfanities(final RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public boolean contains(final String text) {
		final URI url = UriComponentsBuilder.fromUriString("https://www.purgomalum.com/service/containsprofanity")
			.queryParam("text", text)
			.build()
			.toUri();
		return Boolean.parseBoolean(restTemplate.getForObject(url, String.class));
	}
}

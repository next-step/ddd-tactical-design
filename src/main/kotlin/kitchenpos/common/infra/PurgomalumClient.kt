package kitchenpos.common.infra

import kitchenpos.common.domain.Profanities
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class PurgomalumClient(
    restTemplateBuilder: RestTemplateBuilder
) : Profanities {
    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    override fun contains(text: String): Boolean {
        val url = UriComponentsBuilder.fromUriString("https://www.purgomalum.com/service/containsprofanity")
            .queryParam("text", text)
            .build()
            .toUri()
        return restTemplate.getForObject(url, String::class.java).toBoolean()
    }
}

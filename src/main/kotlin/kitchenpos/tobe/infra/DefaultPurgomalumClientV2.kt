package kitchenpos.tobe.infra

import kitchenpos.tobe.menu.domain.MenuPurgomalumClient
import kitchenpos.tobe.product.domain.ProductPurgomalumClient
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class DefaultPurgomalumClientV2(restTemplateBuilder: RestTemplateBuilder) :
    ProductPurgomalumClient,
    MenuPurgomalumClient {
    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    override fun containsProfanity(text: String): Boolean {
        val url =
            UriComponentsBuilder.fromUriString("https://www.purgomalum.com/service/containsprofanity")
                .queryParam("text", text)
                .build()
                .toUri()
        return restTemplate.getForObject(url, String::class.java).toBoolean()
    }
}

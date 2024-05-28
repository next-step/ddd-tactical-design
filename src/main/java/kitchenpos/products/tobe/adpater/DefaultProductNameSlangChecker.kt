package kitchenpos.products.tobe.adpater

import kitchenpos.products.infra.PurgomalumClient
import kitchenpos.products.tobe.port.ProductNameSlangChecker
import org.springframework.stereotype.Component

@Component
class DefaultProductNameSlangChecker(
    private val purgomalumClient: PurgomalumClient,
) : ProductNameSlangChecker {
    override fun containSlang(text: String): Boolean = purgomalumClient.containsProfanity(text)
}

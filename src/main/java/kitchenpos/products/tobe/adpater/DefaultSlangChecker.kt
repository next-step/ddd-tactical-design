package kitchenpos.products.tobe.adpater

import kitchenpos.products.infra.PurgomalumClient
import kitchenpos.products.tobe.port.SlangChecker
import org.springframework.stereotype.Component

@Component
class DefaultSlangChecker(
    private val purgomalumClient: PurgomalumClient,
) : SlangChecker {
    override fun containSlang(text: String): Boolean = purgomalumClient.containsProfanity(text)
}

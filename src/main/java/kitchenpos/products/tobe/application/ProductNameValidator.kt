package kitchenpos.products.tobe.application

import kitchenpos.products.infra.PurgomalumClient
import org.springframework.stereotype.Component

@Component
class ProductNameValidator(
    private val purgomalumClient: PurgomalumClient,
) {
    fun requireNormalName(
        name: String?,
    ) = require(name != null && isNotContainProfanity(name))

    private fun isNotContainProfanity(
        name: String,
    ) = !(purgomalumClient.containsProfanity(name))
}
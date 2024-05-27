package kitchenpos.products.tobe.domain

import kitchenpos.products.infra.PurgomalumClient
import org.springframework.stereotype.Component

@Component
class ProfanityWordValidator(
    private val purgomalumClient: PurgomalumClient,
) : ProductNameValidator {
    override fun validate(name: String) = !purgomalumClient.containsProfanity(name)
}

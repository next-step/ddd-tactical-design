package kitchenpos.menus.tobe.domain

import kitchenpos.menus.tobe.infra.PurgomalumClient
import org.springframework.stereotype.Component

@Component
class ProfanityWordValidator(
    private val purgomalumClient: PurgomalumClient,
) : MenuNameValidator {
    override fun validate(name: String) = !purgomalumClient.containsProfanity(name)
}

package kitchenpos.menus.tode.adapter

import kitchenpos.menus.tode.port.MenuNameSlangChecker
import kitchenpos.products.infra.PurgomalumClient
import org.springframework.stereotype.Component

@Component
class DefaultMenuNameSlangChecker(
    private val purgomalumClient: PurgomalumClient,
) : MenuNameSlangChecker {
    override fun containSlang(text: String): Boolean = purgomalumClient.containsProfanity(text)
}

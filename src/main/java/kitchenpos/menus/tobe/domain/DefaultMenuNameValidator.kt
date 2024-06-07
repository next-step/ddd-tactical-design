package kitchenpos.menus.tobe.domain

import kitchenpos.products.infra.PurgomalumClient
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class DefaultMenuNameValidator(
    private val purgomalumClient: PurgomalumClient
) : MenuNameValidator {
    override fun validate(name: String) {
        if (purgomalumClient.containsProfanity(name)) {
            throw IllegalArgumentException("메뉴 이름에는 욕설 및 비속어가 포함될 수 없습니다")
        }
    }
}

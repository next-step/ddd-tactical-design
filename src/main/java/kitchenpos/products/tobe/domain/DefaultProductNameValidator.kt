package kitchenpos.products.tobe.domain

import kitchenpos.products.infra.PurgomalumClient
import org.springframework.stereotype.Component

@Component
class DefaultProductNameValidator(
    private val purgomalumClient: PurgomalumClient
) : ProductNameValidator {

    override fun validate(name: String) {
        if (purgomalumClient.containsProfanity(name)) {
            throw IllegalArgumentException("상품 이름에는 비속어 및 욕설이 포함될 수 없습니다")
        }
    }
}

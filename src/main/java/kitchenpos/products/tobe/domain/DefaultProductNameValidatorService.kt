package kitchenpos.products.tobe.domain

import kitchenpos.products.infra.PurgomalumClient
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class DefaultProductNameValidatorService(
    private val purgomalumClient: PurgomalumClient
) : ProductNameValidatorService {

    override fun validate(name: String) {
        if (purgomalumClient.containsProfanity(name)) {
            throw IllegalArgumentException("상품 이름에는 비속어 및 욕설이 포함될 수 없습니다")
        }
    }
}

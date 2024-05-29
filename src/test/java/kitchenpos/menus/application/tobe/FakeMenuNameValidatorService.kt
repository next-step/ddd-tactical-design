package kitchenpos.menus.application.tobe

import kitchenpos.menus.tobe.domain.MenuNameValidatorService
import kitchenpos.products.application.FakePurgomalumClient
import java.lang.IllegalArgumentException

object FakeMenuNameValidatorService : MenuNameValidatorService {
    private val purgomalumClient = FakePurgomalumClient()

    override fun validate(name: String) {
        if (purgomalumClient.containsProfanity(name)) {
            throw IllegalArgumentException("메뉴 이름에는 욕설 및 비속어가 포함될 수 없습니다")
        }
    }
}


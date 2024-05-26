package kitchenpos.products.application

import kitchenpos.products.tobe.domain.ProductNameValidatorService

private val fakePurgomalumClient = FakePurgomalumClient()

class FakeProductNameValidatorService : ProductNameValidatorService {
    override fun validate(name: String) {
        fakePurgomalumClient.containsProfanity(name)
    }
}

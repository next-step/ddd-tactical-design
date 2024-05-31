package kitchenpos.products.application

import kitchenpos.products.tobe.domain.ProductNameValidator

private val fakePurgomalumClient = FakePurgomalumClient()

object FakeProductNameValidator : ProductNameValidator {
    override fun validate(name: String) {
        fakePurgomalumClient.containsProfanity(name)
    }
}

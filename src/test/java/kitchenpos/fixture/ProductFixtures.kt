package kitchenpos.fixture

import kitchenpos.common.price
import kitchenpos.products.application.FakeProductNameValidatorService
import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.productPrice
import java.math.BigDecimal


object ProductFixtures {
    fun product(): Product {
        val price = BigDecimal.valueOf(10000L).price().productPrice()
        return Product("후라이드", price, FakeProductNameValidatorService())
    }
}

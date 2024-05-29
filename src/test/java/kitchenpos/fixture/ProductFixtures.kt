package kitchenpos.fixture

import kitchenpos.common.Price
import kitchenpos.common.price
import kitchenpos.products.application.FakeProductNameValidatorService
import kitchenpos.products.tobe.domain.Product
import java.math.BigDecimal


object ProductFixtures {
    fun product(price: BigDecimal): Product {
        return product(price.price())
    }

    fun product(price: Price = BigDecimal.valueOf(10000L).price()): Product {
        return Product("후라이드", price, FakeProductNameValidatorService)
    }
}

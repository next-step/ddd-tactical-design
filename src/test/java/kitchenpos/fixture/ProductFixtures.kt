package kitchenpos.fixture

import kitchenpos.common.Price
import kitchenpos.common.price
import kitchenpos.products.application.FakeProductNameValidator
import kitchenpos.products.tobe.domain.Product
import kitchenpos.products.tobe.domain.ProductName
import java.math.BigDecimal


object ProductFixtures {
    fun product(price: BigDecimal): Product {
        return product(price.price())
    }

    fun product(price: Price = BigDecimal.valueOf(10000L).price()): Product {
        return Product(ProductName.of("후라이드", FakeProductNameValidator), price)
    }
}

package kitchenpos.utils

import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.infra.FakeProfanities

class ProductFixture {
    companion object {
        val PRODUCT_NAME = ProductName(FakeProfanities(), "양념치킨")
    }
}

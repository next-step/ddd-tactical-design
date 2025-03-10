package kitchenpos.menu.tobe.domain

import kitchenpos.product.tobe.domain.ProductPriceChangedEvent

interface MenuProductPriceChangedListener {
    fun handle(event: ProductPriceChangedEvent)
}

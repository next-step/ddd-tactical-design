package kitchenpos.menu.tobe.domain

import kitchenpos.product.tobe.domain.ProductPriceChangedEvent

interface MenuProductPriceChangedEventListener {
    fun handle(event: ProductPriceChangedEvent)
}

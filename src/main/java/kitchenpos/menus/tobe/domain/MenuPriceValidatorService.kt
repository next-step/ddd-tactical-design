package kitchenpos.menus.tobe.domain

import kitchenpos.common.Price
import java.util.UUID

interface MenuPriceValidatorService {
    fun validate(menu: Menu)
    fun validate(menu: Menu, productPriceById: Map<UUID, Price>)
}

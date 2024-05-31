package kitchenpos.tobe.menu.application.dto.request

import java.math.BigDecimal

data class ChangeMenuPriceRequest(
    val price: BigDecimal,
)

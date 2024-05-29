package kitchenpos.tobe.eatinorder.domain.dto

import java.math.BigDecimal
import java.util.*

data class MenuData(
    val id: UUID,
    val displayed: Boolean,
    val price: BigDecimal,
)

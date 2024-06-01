package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.Embeddable
import java.util.*

@Embeddable
data class OrderMenu(
    val menuId: UUID,
    val price: Int,
)

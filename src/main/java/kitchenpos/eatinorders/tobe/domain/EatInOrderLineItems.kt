package kitchenpos.eatinorders.tobe.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import java.util.UUID

@Embeddable
class EatInOrderLineItems(
    @OneToMany(cascade = [CascadeType.PERSIST, CascadeType.MERGE], mappedBy = "eatInOrder", fetch = FetchType.LAZY)
    val items: List<EatInOrderLineItem>
) {
    val menuIds: List<UUID>
        get() = items.map { it.menuId }

    init {
        if (items.isEmpty()) {
            throw IllegalArgumentException("주문항목이 적어도 하나는 필수입니다")
        }
    }

    //연관관계 편의 메소드
    fun apply(eatInOrder: EatInOrder) {
        items.forEach { it.eatInOrder = eatInOrder }
    }
}

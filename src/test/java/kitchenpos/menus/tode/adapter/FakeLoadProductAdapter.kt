package kitchenpos.menus.tode.adapter

import jakarta.transaction.NotSupportedException
import kitchenpos.menus.tode.port.LoadProductPort
import kitchenpos.products.domain.Product
import java.util.*

class FakeLoadProductAdapter : LoadProductPort {
    var emptyListTrigger = false

    override fun findAllByIdIn(ids: List<UUID>): List<Product> {
        if (emptyListTrigger) return emptyList()

        return ids.map {
            Product().apply {
                this.id = it
            }
        }
    }

    override fun findById(id: UUID): Product? {
        throw NotSupportedException()
    }
}

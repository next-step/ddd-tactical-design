package kitchenpos.menus.tode.adapter

import kitchenpos.menus.tode.port.LoadProductPort
import kitchenpos.products.domain.Product
import kitchenpos.products.domain.ProductRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class LoadProductAdapter(
    private val productRepository: ProductRepository,
) : LoadProductPort {
    override fun findAllByIdIn(ids: List<UUID>): List<Product> = productRepository.findAllByIdIn(ids)

    override fun findById(id: UUID): Product? =
        productRepository.findById(id).get()
}

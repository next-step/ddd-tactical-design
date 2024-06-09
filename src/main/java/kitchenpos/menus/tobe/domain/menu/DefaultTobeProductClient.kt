package kitchenpos.menus.tobe.domain.menu

import kitchenpos.products.tobe.domain.TobeProductRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class DefaultTobeProductClient(
    private val tobeProductRepository: TobeProductRepository,
) : TobeProductClient {
    override fun validateAllProductsExists(menuProductIds: List<UUID>): Boolean {
        val existProductsIds =
            tobeProductRepository.findAllByIdIn(menuProductIds)
                .map { it.id }

        return existProductsIds.containsAll(menuProductIds)
    }
}

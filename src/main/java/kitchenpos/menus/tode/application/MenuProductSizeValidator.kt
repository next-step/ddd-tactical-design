package kitchenpos.menus.tode.application

import kitchenpos.menus.tode.port.LoadProductPort
import kitchenpos.products.domain.Product
import org.springframework.stereotype.Service
import java.util.*

@Service
class MenuProductSizeValidator(
    private val loadProductPort: LoadProductPort,
) {
    fun requireProductSizeMatch(
        ids: List<UUID>,
    ): List<Product> = loadProductPort.findAllByIdIn(ids)
        .also {
            require(it.size == ids.size)
        }
}

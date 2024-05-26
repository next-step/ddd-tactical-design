package kitchenpos.menus.tode.application

import kitchenpos.menus.tode.domain.RenewMenuDisplay
import kitchenpos.products.tobe.port.LoadMenuPort
import org.springframework.stereotype.Service
import java.util.*

@Service
class PriceChangeDrivenMenuUpdater(
    private val loadMenuPort: LoadMenuPort,
) {
    fun menuDisplayUpdateByProductId(
        productId: UUID,
    ) = loadMenuPort.findAllByProductId(productId)
        .forEach { menu ->
            RenewMenuDisplay.renewMenusDisplay(menu)
        }
}

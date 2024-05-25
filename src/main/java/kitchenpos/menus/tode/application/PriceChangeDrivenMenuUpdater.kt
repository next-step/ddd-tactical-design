package kitchenpos.menus.tode.application

import kitchenpos.menus.tode.domain.RenewMenuDisplay
import kitchenpos.menus.tode.port.MenuReader
import org.springframework.stereotype.Service
import java.util.*

@Service
class PriceChangeDrivenMenuUpdater(
    private val menuReader: MenuReader,
) {
    fun menuDisplayUpdateByProductId(
        productId: UUID,
    ) = menuReader.findAllByProductId(productId)
        .forEach { menu ->
            RenewMenuDisplay.renewMenusDisplay(menu)
        }
}

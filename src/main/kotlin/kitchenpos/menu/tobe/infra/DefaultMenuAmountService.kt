package kitchenpos.menu.tobe.infra

import java.math.BigDecimal
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.menu.tobe.domain.ProductClient
import org.springframework.stereotype.Component

@Component
class DefaultMenuAmountService(
    private val productClient: ProductClient,
) : MenuAmountService {

    override fun amount(menuProducts: MenuProducts): BigDecimal {
        return menuProducts.amount(productClient)
    }
}

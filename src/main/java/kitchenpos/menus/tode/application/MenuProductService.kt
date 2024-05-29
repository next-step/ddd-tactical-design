package kitchenpos.menus.tode.application

import kitchenpos.menus.domain.MenuProduct
import kitchenpos.menus.tode.domain.MenuPriceValidator
import kitchenpos.menus.tode.domain.MenuProductQuantityValidator
import kitchenpos.menus.tode.port.LoadProductPort
import org.springframework.stereotype.Service

import java.math.BigDecimal

@Service
class MenuProductService(
    private val loadProductPort: LoadProductPort,
) {
    fun createMenuProduct(
        menuPrice: BigDecimal,
        menuProductRequests: List<MenuProduct>,
    ): List<MenuProduct> {
        val menuProducts = mutableListOf<MenuProduct>()
        var sum = BigDecimal.ZERO

        menuProductRequests.forEach { menuProductRequest ->
            val quantity = menuProductRequest.quantity
            MenuProductQuantityValidator.requireNotNegative(quantity)

            val product = loadProductPort.findById(menuProductRequest.productId)
                ?: throw NoSuchElementException()

            sum = sum.add(product.price.multiply(BigDecimal.valueOf(quantity)))

            menuProducts.add(
                MenuProduct().apply {
                    this.product = product
                    this.quantity = quantity
                }
            )
        }

        MenuPriceValidator.requireMenuPriceUnderSum(
            menuPrice = menuPrice,
            sum = sum,
        )

        return menuProducts
    }
}

package kitchenpos.menus.tobe.domain

import kitchenpos.common.Price
import kitchenpos.common.ZERO
import kitchenpos.products.tobe.domain.ProductRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class DefaultMenuPriceValidatorService(
    private val productRepository: ProductRepository
) : MenuPriceValidatorService {
    override fun validate(menu: Menu) {
        val productIds = menu.menuProducts.map { it.productId }

        val productPriceById = productRepository.findAllByIdIn(productIds)
            .associate { it.id to it.price }

        val totalMenuProductPrice = calculateTotalMenuProductPrice(menu, productPriceById)

        if (menu.displayStatus && menu.price > totalMenuProductPrice) {
            throw IllegalArgumentException("전시중인 메뉴의 가격이 메뉴상품 가격의 총합보다 클 수 없습니다")
        }
    }

    override fun validate(menu: Menu, productPriceById: Map<UUID, Price>) {
        val totalMenuProductPrice = calculateTotalMenuProductPrice(menu, productPriceById)

        if (menu.displayStatus && menu.price > totalMenuProductPrice) {
            throw IllegalArgumentException("전시중인 메뉴의 가격이 메뉴상품 가격의 총합보다 클 수 없습니다")
        }
    }

    private fun calculateTotalMenuProductPrice(menu: Menu, productPriceById: Map<UUID, Price>): Price =
        menu.menuProducts.map {
            val menuProductPrice = productPriceById[it.productId]
                ?: throw IllegalArgumentException("메뉴 상품의 가격이 존재하지 않습니다")

            menuProductPrice * it.quantity.value
        }.foldRight(ZERO) { acc, price -> acc + price }
}

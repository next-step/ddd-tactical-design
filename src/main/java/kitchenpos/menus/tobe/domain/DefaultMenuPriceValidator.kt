package kitchenpos.menus.tobe.domain

import kitchenpos.products.tobe.domain.ProductRepository
import org.springframework.stereotype.Component

@Component
class DefaultMenuPriceValidator(
    private val productRepository: ProductRepository
) : MenuPriceValidator {
    override fun validate(menu: Menu) {
        val productIds = menu.menuProducts.productIds

        val productPriceById = productRepository.findAllByIdIn(productIds)
            .associate { it.id to it.price }

        val totalMenuProductPrice = menu.menuProducts.calculateTotalPrice(productPriceById)

        if (menu.price > totalMenuProductPrice) {
            throw IllegalArgumentException("메뉴의 가격이 메뉴상품 가격의 총합보다 클 수 없습니다")
        }
    }

    override fun isValid(menu: Menu): Boolean {
        val productIds = menu.menuProducts.productIds

        val productPriceById = productRepository.findAllByIdIn(productIds)
            .associate { it.id to it.price }

        return menu.price <= menu.menuProducts.calculateTotalPrice(productPriceById)
    }
}

package kitchenpos.menu.tobe.infra

import java.math.BigDecimal
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuProducts
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.stereotype.Component

@Component
class DefaultMenuAmountService(
    private val productRepository: ProductRepository
) : MenuAmountService {


    override fun amount(menuProducts: MenuProducts): BigDecimal {
        var sum = BigDecimal.ZERO
        menuProducts.menuProducts.forEach { menuProduct ->
            val product = productRepository.findById(menuProduct.productId!!)
                .orElseThrow { NoSuchElementException("상품을 찾을 수 없습니다.") }
            sum += product.productPrice.price * menuProduct.quantity.toBigDecimal()
        }
        return sum
    }
}

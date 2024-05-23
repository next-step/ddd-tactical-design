package kitchenpos.products.tobe.application

import kitchenpos.products.domain.Product
import kitchenpos.products.infra.PurgomalumClient
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.*

@Component
class CreateProductService(
    private val purgomalumClient: PurgomalumClient,
) {
    fun createProduct(
        price: BigDecimal?,
        name: String?,
    ): Product {
        require(isNormalPrice(price))
        require(isNormalName(name))

        return Product().apply {
            this.id = UUID.randomUUID()
            this.name = name
            this.price = price
        }
    }

    private fun isNormalPrice(
        price: BigDecimal?,
    ) = price != null && isNotNegativePrice(price)

    private fun isNormalName(
        name: String?,
    ) = name != null && isNotContainProfanity(name)

    private fun isNotNegativePrice(
        price: BigDecimal,
    ) = price >= BigDecimal.ZERO

    private fun isNotContainProfanity(
        name: String,
    ) = !(purgomalumClient.containsProfanity(name))
}
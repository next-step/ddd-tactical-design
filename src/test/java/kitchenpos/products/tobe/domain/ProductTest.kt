package kitchenpos.products.tobe.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ProductTest : BehaviorSpec({
    Given("상품 가격을 변경할 떄") {
        val name = ProductName("테스트 상품") { true }
        val price = ProductPrice.valueOf(1000)
        val product = Product(name, price)

        When("상품 가격을 변경하면") {
            val newPrice = ProductPrice.valueOf(2000)
            product.changePrice(newPrice)

            Then("상품 가격이 변경된다") {
                product.price shouldBe newPrice
            }

            Then("도메인 이벤트가 발생한다") {
                val domainEvents = product.getAndClearDomainEvents()

                domainEvents.size shouldBe 1
                domainEvents[0].shouldBeInstanceOf<ProductPriceChangedEvent>()
            }
        }
    }
})

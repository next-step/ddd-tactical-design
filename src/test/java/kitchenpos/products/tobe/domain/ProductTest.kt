package kitchenpos.products.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class ProductTest : BehaviorSpec({
    Given("상품을 생성할 떄") {
        When("상품 이름 유효성 검증을 통과하지 못하면") {
            val name = ProductName("테스트 상품")
            val price = ProductPrice.valueOf(1000)
            val nameValidator = ProductNameValidator { false }

            Then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    Product(name, price, nameValidator)
                }
            }
        }

        When("상품 이름 유효성 검증을 통과하면") {
            val name = ProductName("테스트 상품")
            val price = ProductPrice.valueOf(1000)
            val nameValidator = ProductNameValidator { true }

            Then("정상적으로 생성된다") {
                val product = Product(name, price, nameValidator)
                product.name shouldBe name
                product.price shouldBe price
            }
        }
    }

    Given("상품 가격을 변경할 떄") {
        val name = ProductName("테스트 상품")
        val price = ProductPrice.valueOf(1000)
        val nameValidator = ProductNameValidator { true }
        val product = Product(name, price, nameValidator)

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

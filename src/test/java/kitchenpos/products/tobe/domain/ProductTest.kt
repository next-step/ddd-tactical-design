package kitchenpos.products.tobe.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ProductTest : BehaviorSpec({
    given("상품을 생성할 떄") {
        `when`("상품 이름 유효성 검증을 통과하지 못하면") {
            val name = ProductName("테스트 상품")
            val price = ProductPrice.valueOf(1000)
            val nameValidator = ProductNameValidator { false }

            then("예외가 발생한다") {
                shouldThrow<IllegalArgumentException> {
                    Product(name, price, nameValidator)
                }
            }
        }

        `when`("상품 이름 유효성 검증을 통과하면") {
            val name = ProductName("테스트 상품")
            val price = ProductPrice.valueOf(1000)
            val nameValidator = ProductNameValidator { true }

            then("정상적으로 생성된다") {
                val product = Product(name, price, nameValidator)
                product.name shouldBe name
                product.price shouldBe price
            }
        }
    }
})

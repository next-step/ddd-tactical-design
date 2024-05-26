package kitchenpos.products.tobe.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kitchenpos.products.domain.Product
import java.math.BigDecimal

class UpdateProductPriceServiceTest : BehaviorSpec({
    Given("가격 변경 시") {
        When("입력한 가격으로") {
            Then("업데이트 되어야 한다.") {
                val product = Product()
                val renewPrice = BigDecimal.ONE

                val result = UpdateProductPriceService.updatePrice(
                    product = product,
                    renewPrice = renewPrice,
                )

                result.price shouldBe renewPrice
            }
        }
    }
})

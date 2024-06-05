package kitchenpos.tobe.domain.product.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kitchenpos.tobe.product.domain.vo.ProductPrice
import java.math.BigDecimal

class ProductPriceTest : DescribeSpec() {
    init {
        describe("ProductPrice 클래스의") {
            describe("of 메서드는") {
                context("0원 이하의 상품 가격이 주어졌을 때") {
                    it("ProductPriceException 던진다") {
                        shouldThrow<IllegalArgumentException> {
                            ProductPrice.from(BigDecimal.valueOf(-1))
                        }.message shouldBe "상품 가격은 0원 이상이어야 합니다."
                    }
                }
            }
        }
    }
}

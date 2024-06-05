package kitchenpos.tobe.domain.product.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kitchenpos.tobe.domain.product.FakeProductPugomalumClient
import kitchenpos.tobe.product.domain.vo.ProductName

class ProductNameTest : DescribeSpec() {
    init {
        describe("ProductName 클래스의") {
            describe("of 메서드는") {
                context("비속어 탐지 시스템이 주어졌을 때") {
                    val productPurgomalumClient = FakeProductPugomalumClient()
                    context("정상적인 상품 이름이 주어졌을 때") {
                        it("ProductName 객체를 생성한다") {
                            val productName = ProductName.of("테스트 상품 이름", productPurgomalumClient)
                            productName.name shouldBe "테스트 상품 이름"
                        }
                    }

                    context("비속어가 포함된 상품 이름이 주어졌을 때") {
                        it("ProductNameException을 던진다") {
                            shouldThrow<IllegalArgumentException> {
                                ProductName.of("욕1", productPurgomalumClient)
                            }.message shouldBe "상품명에 욕설이 포함되어 있습니다."
                        }
                    }

                    context("공백 상품 이름이 주어졌을 때") {
                        it("ProductNameException을 던진다") {
                            shouldThrow<IllegalArgumentException> {
                                ProductName.of("", productPurgomalumClient)
                            }.message shouldBe "상품명은 공백이 될 수 없습니다."
                        }
                    }
                }
            }
        }
    }
}

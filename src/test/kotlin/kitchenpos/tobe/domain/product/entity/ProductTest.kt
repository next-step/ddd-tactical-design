package kitchenpos.tobe.domain.product.entity

import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kitchenpos.tobe.domain.product.FakeProductPugomalumClient
import kitchenpos.tobe.product.domain.entity.ProductV2
import java.math.BigDecimal

class ProductTest() : DescribeSpec() {
    init {
        describe("Product 클래스의") {
            describe("of 메서드는") {
                context("정상적인 상품 요청이 주어졌을 때") {
                    it("Product 객체를 생성한다") {
                        val pugomalumClient = FakeProductPugomalumClient()
                        val productV2 = ProductV2.from("테스트 상품 이름", BigDecimal.valueOf(1000), pugomalumClient)

                        assertSoftly {
                            productV2.getName() shouldBe "테스트 상품 이름"
                            productV2.getPrice() shouldBe BigDecimal.valueOf(1000)
                        }
                    }
                }

                context("비속어가 포함된 상품 이름이 주어졌을 때") {
                    it("예외를 던진다") {
                        val pugomalumClient = FakeProductPugomalumClient()
                        assertSoftly {
                            shouldThrow<IllegalArgumentException> {
                                ProductV2.from("욕1", BigDecimal.valueOf(1000), pugomalumClient)
                            }.message shouldBe "상품명에 욕설이 포함되어 있습니다."
                        }
                    }
                }

                context("공백 상품 이름이 주어졌을 때") {
                    it("ProductNameException 던진다") {
                        val pugomalumClient = FakeProductPugomalumClient()
                        assertSoftly {
                            shouldThrow<IllegalArgumentException> {
                                ProductV2.from("", BigDecimal.valueOf(1000), pugomalumClient)
                            }.message shouldBe "상품명은 공백이 될 수 없습니다."
                        }
                    }
                }

                context("0원 이하의 상품 가격이 주어졌을 때") {
                    it("ProductPriceException 던진다") {
                        val pugomalumClient = FakeProductPugomalumClient()
                        assertSoftly {
                            shouldThrow<IllegalArgumentException> {
                                ProductV2.from("테스트 상품 이름", BigDecimal.valueOf(-1), pugomalumClient)
                            }
                        }
                    }
                }
            }
        }
    }
}

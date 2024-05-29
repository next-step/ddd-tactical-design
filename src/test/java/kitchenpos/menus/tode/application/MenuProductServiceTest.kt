package kitchenpos.menus.tode.application

import io.kotest.assertions.throwables.shouldThrowExactly
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import kitchenpos.menus.domain.MenuProduct
import kitchenpos.menus.tode.adapter.FakeLoadProductAdapter
import kitchenpos.products.domain.Product
import java.math.BigDecimal
import java.util.*

class MenuProductServiceTest : BehaviorSpec({
    val productAdapter = FakeLoadProductAdapter()
    val menuProductService = MenuProductService(loadProductPort = productAdapter)

    beforeTest {
        productAdapter.returnValueFindById = null
    }

    fun createProduct(
        price: BigDecimal = BigDecimal.ONE,
        productId: UUID = UUID.randomUUID(),
    ) = Product().apply {
        this.id = productId
        this.price = price
    }

    fun createMenuProductRequest(
        quantity: Long = 1,
        productId: UUID = UUID.randomUUID(),
        productPrice: BigDecimal = BigDecimal.ONE,
        product: Product = createProduct(price = productPrice),
    ): MenuProduct = MenuProduct().apply {
        this.quantity = quantity
        this.productId = productId
        this.product = product
    }

    Given("메뉴에 속한 상품 생성 시") {
        When("상품의 수량이 음수라면") {
            Then("IllegalArgumentException 예외 처리한다.") {
                shouldThrowExactly<IllegalArgumentException> {
                    menuProductService.createMenuProduct(
                        menuPrice = BigDecimal.TWO, menuProductRequests = listOf(
                            createMenuProductRequest(quantity = -1)
                        )
                    )
                }
            }
        }
        When("상품이 존재하지 않는다면") {
            Then("NoSuchElementException 예외 처리 한다.") {
                shouldThrowExactly<NoSuchElementException> {
                    menuProductService.createMenuProduct(
                        menuPrice = BigDecimal.TWO, menuProductRequests = listOf(
                            createMenuProductRequest(productId = UUID.randomUUID())
                        )
                    )
                }
            }
        }
        When("메뉴 가격이 더 크다면") {
            Then("IllegalArgumentException 예외 처리 한다.") {
                val product = Product().apply {
                    this.price = BigDecimal.ONE
                }
                productAdapter.returnValueFindById = product

                shouldThrowExactly<IllegalArgumentException> {
                    val element = createMenuProductRequest(
                        productId = UUID.randomUUID(),
                        product = product,
                    )
                    menuProductService.createMenuProduct(
                        menuPrice = BigDecimal.TWO,
                        menuProductRequests = listOf(
                            element
                        )
                    )
                }
            }
        }
        When("메뉴 가격이 더 작다면") {
            Then("메뉴에 속한 상품 객체를 생성해서 반환한다.") {
                val product = Product().apply {
                    this.price = BigDecimal.TWO
                }
                productAdapter.returnValueFindById = product

                val menuProductRequest = createMenuProductRequest(
                    productId = UUID.randomUUID(),
                    product = product,
                    quantity = 1L,
                )
                val result = menuProductService.createMenuProduct(
                    menuPrice = BigDecimal.ONE,
                    menuProductRequests = listOf(
                        menuProductRequest
                    )
                )

                result.size shouldBe 1
                result.first().quantity shouldBe 1L
            }
        }
    }
})

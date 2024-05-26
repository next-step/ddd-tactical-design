package kitchenpos.products.tobe.application

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.springframework.context.ApplicationEventPublisher
import java.math.BigDecimal

val Int.won: BigDecimal get() = BigDecimal(this)

class ProductServiceTest : BehaviorSpec({
    val productRepository = InMemoryProductRepository()
    val eventPublisher = mockk<ApplicationEventPublisher>()
    val productService =
        ProductService(
            productRepository = productRepository,
            productNameValidator = { listOf("유효하지 않은 이름").contains(it).not() },
            eventPublisher = eventPublisher,
        )

    val validName = "유효한 이름"
    val invalidName = "유효하지 않은 이름"
    val validPrice = 1000.won
    val invalidPrice = (-1000).won

    justRun { eventPublisher.publishEvent(any()) }

    Given("상품을 생성할 떄") {
        When("상품 이름과 가격이 유효하면") {
            val newProduct = productService.create(validName, validPrice)

            Then("상품이 성공적으로 생성된다.") {
                newProduct.name shouldBe validName
                newProduct.price shouldBe validPrice
            }
        }

        When("상품 가격이 올바르지 않으면") {
            Then("예외가 발생한다.") {
                shouldThrow<IllegalArgumentException> {
                    productService.create(validName, invalidPrice)
                }
            }
        }

        When("상품 이름이 유효하지 않으면") {
            Then("예외가 발생한다.") {
                shouldThrow<IllegalArgumentException> {
                    productService.create(invalidName, validPrice)
                }
            }
        }
    }

    Given("상품 가격을 변경할 때") {
        When("상품이 존재하는 경우") {
            val existingProduct = productService.create(validName, validPrice)
            val updateProduct = productService.changePrice(existingProduct.id, 2000.won)

            Then("상품 가격이 업데이트된다.") {
                updateProduct.price shouldBe 2000.won
            }

            Then("이벤트가 발행된다.") {
                verify { eventPublisher.publishEvent(any()) }
            }
        }
    }
})

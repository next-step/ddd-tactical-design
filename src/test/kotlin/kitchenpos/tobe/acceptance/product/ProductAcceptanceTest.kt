package kitchenpos.tobe.acceptance.product

import io.kotest.matchers.shouldBe
import kitchenpos.acceptance.CommonAcceptanceTest
import kitchenpos.tobe.product.domain.repository.ProductRepository
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.transaction.annotation.Transactional

@Transactional
class ProductAcceptanceTest(
    @LocalServerPort
    override var port: Int,
    private val productRepository: ProductRepository,
) : CommonAcceptanceTest() {
    init {
        given("가게 사장님이 로그인한 뒤에") {
            `when`("상품을 생성하면") {
                then("상품이 생성된다.") {
                    val response =
                        commonRequestSpec()
                            .given()
                            .body(
                                """
                                {
                                    "name": "후라이드 치킨",
                                    "price": 16000
                                }
                                """.trimIndent(),
                            )
                            .post("/api/v2/products")
                            .then()
                            .log().everything()
                            .extract().response()

                    val jsonPathEvaluator = response.jsonPath()

                    jsonPathEvaluator.getString("name") shouldBe "후라이드 치킨"
                    jsonPathEvaluator.getInt("price") shouldBe 16000
                    response.statusCode shouldBe 201
                }
            }

            `when`("가격을 변경하면") {
                then("상품의 가격이 변경된다.") {
                    val createProductResponse =
                        commonRequestSpec()
                            .given()
                            .body(
                                """
                                {
                                    "name": "후라이드 치킨",
                                    "price": 16000
                                }
                                """.trimIndent(),
                            )
                            .post("/api/v2/products")
                            .then()
                            .log().everything()
                            .extract().response()

                    val createdProductId = createProductResponse.jsonPath().getString("id")

                    val response =
                        commonRequestSpec()
                            .given()
                            .body(
                                """
                                {
                                    "price": 15000
                                }
                                """.trimIndent(),
                            )
                            .put("/api/products/$createdProductId/price")
                            .then()
                            .log().everything()
                            .extract().response()

                    val jsonPathEvaluator = response.jsonPath()
                    jsonPathEvaluator.getInt("price") shouldBe 15000
                }
            }
        }
    }
}

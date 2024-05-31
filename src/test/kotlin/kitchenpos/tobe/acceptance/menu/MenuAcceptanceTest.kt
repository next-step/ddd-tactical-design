package kitchenpos.tobe.acceptance.menu

import io.kotest.matchers.shouldBe
import kitchenpos.acceptance.CommonAcceptanceTest
import kitchenpos.tobe.menu.domain.repository.MenuGroupRepositoryV2
import kitchenpos.tobe.menu.domain.repository.MenuRepositoryV2
import kitchenpos.tobe.product.domain.repository.ProductRepositoryV2
import org.springframework.boot.test.web.server.LocalServerPort
import tobe.domain.MenuGroupFixtures.createMenuGroup
import tobe.domain.ProductFixtures.createProduct

class MenuAcceptanceTest(
    @LocalServerPort
    override val port: Int,
    private val productRepository: ProductRepositoryV2,
    private val menuGroupRepository: MenuGroupRepositoryV2,
    private val menuRepository: MenuRepositoryV2,
) : CommonAcceptanceTest() {
    init {
        given("가게 사장님이 로그인한 뒤에") {
            `when`("메뉴를 생성하면") {
                then("메뉴가 생성된다.") {
                    val product = productRepository.save(createProduct())
                    val menuGroup = menuGroupRepository.save(createMenuGroup())

                    val response =
                        commonRequestSpec()
                            .given()
                            .body(
                                mapOf(
                                    "name" to "후라이드 치킨",
                                    "price" to 16000,
                                    "menuGroupId" to menuGroup.id.toString(),
                                    "menuProducts" to
                                        listOf(
                                            mapOf(
                                                "productId" to product.id.toString(),
                                                "quantity" to 1,
                                            ),
                                        ),
                                ),
                            )
                            .post("/api/v2/menus")
                            .then()
                            .log().everything()
                            .extract().response()

                    val jsonPathEvaluator = response.jsonPath()

                    jsonPathEvaluator.getString("name") shouldBe "후라이드 치킨"
                    jsonPathEvaluator.getInt("price") shouldBe 16000
                    response.statusCode shouldBe 201
                }
            }

//            `when`("메뉴의 가격을 변경하면") {
//                then("가격이 변경된다.") {
//                    productRepository.save(createProduct())
//                    val menuGroup = menuGroupRepository.save(createMenuGroup())
//                    val menu = menuRepository.save(createMenu(menuGroup))
//
//                    val response =
//                        commonRequestSpec()
//                            .given()
//                            .body(
//                                mapOf(
//                                    "price" to 15000,
//                                ),
//                            )
//                            .put("/api/v2/menus/${menu.id}/price")
//                            .then()
//                            .log().everything()
//                            .extract().response()
//
//                    val jsonPathEvaluator = response.jsonPath()
//
//                    jsonPathEvaluator.getInt("price") shouldBe 15000
//                    response.statusCode shouldBe 200
//                }
//
//                then("메뉴의 가격이 메뉴 상품들의 가격과 개수보다 작으면 가격을 변경할 수 없다.") {
//                    productRepository.save(createProduct())
//                    menuGroupRepository.save(createMenuGroup())
//                    val menu = menuRepository.save(createMenu())
//
//                    val response =
//                        commonRequestSpec()
//                            .given()
//                            .body(
//                                mapOf(
//                                    "price" to 17000,
//                                ),
//                            )
//                            .put("/api/v2/menus/${menu.id}/price")
//                            .then()
//                            .log().everything()
//                            .extract().response()
//
//                    response.statusCode shouldBe 500
//                }
//            }
        }
    }
}

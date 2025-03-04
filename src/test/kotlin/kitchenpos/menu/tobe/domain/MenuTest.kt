package kitchenpos.menu.tobe.domain

import kitchenpos.utils.Fixtures
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuTest {

    @Test
    @DisplayName("Menu를 생성한다")
    fun create() {
        // given
        val 추천메뉴 = Fixtures.menuGroup(name = "추천메뉴")

        val 양념치킨 = Fixtures.product(name = "양념치킨", price = 16000)
        val 후라이드치킨 = Fixtures.product(name = "후라이드치킨", price = 17000)
    }
}

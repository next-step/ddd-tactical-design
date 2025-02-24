package kitchenpos.product.tobe.domain

import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.NullAndEmptySource

class ProductNameTest {

    @Test
    @DisplayName("상품이름 생성 / 성공")
    fun createProductName() {
        val name = "양념치킨"
        val productName = ProductName(profanities = FakeProfanities(), name = name)

        assertEquals(name, productName.name)
    }

    @DisplayName("빈 이름 / 상품이름 생성 / 실패")
    @NullAndEmptySource
    @ParameterizedTest
    fun createProductNameFail(name: String) {
        assertThatIllegalArgumentException().isThrownBy {
            ProductName(profanities = FakeProfanities(), name = name)
        }
    }

    @DisplayName("욕설 / 상품이름 생성 / 실패")
    @ParameterizedTest
    @CsvSource(value = ["욕설상품", "비속어"])
    fun createProductNameProfanityFail(name: String) {
        assertThatIllegalArgumentException().isThrownBy {
            ProductName(profanities = FakeProfanities(listOf("욕설", "비속어")), name = name)
        }
    }

}


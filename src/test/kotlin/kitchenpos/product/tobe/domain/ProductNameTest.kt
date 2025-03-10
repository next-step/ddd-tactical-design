package kitchenpos.product.tobe.domain

import kitchenpos.product.tobe.infra.DefaultProductNamePolicy
import kitchenpos.product.tobe.infra.FakeProfanities
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EmptySource

class ProductNameTest {

    @Test
    @DisplayName("`ProductName`을 생성한다")
    fun createProductName() {
        val name = "양념치킨"
        val productName = ProductName(productNamePolicy = DefaultProductNamePolicy(FakeProfanities()), name = name)

        assertEquals(name, productName.name)
    }

    @DisplayName("`ProductName`은 필수값이다")
    @EmptySource
    @ParameterizedTest
    fun createProductNameFail(name: String) {
        assertThatIllegalArgumentException().isThrownBy {
            ProductName(productNamePolicy = DefaultProductNamePolicy(FakeProfanities()), name = name)
        }
    }

    @DisplayName("`Profanities`의 `Profanity`가 포함된 `ProductName`은 생성할 수 없다")
    @ParameterizedTest
    @CsvSource(value = ["욕설상품", "비속어"])
    fun createProductNameProfanityFail(name: String) {
        assertThatIllegalArgumentException().isThrownBy {
            ProductName(productNamePolicy = DefaultProductNamePolicy(FakeProfanities(listOf("욕설", "비속어"))), name = name)
        }
    }

}


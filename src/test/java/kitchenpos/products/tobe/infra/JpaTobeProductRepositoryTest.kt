package kitchenpos.products.tobe.infra

import kitchenpos.products.tobe.domain.TobeProduct
import kitchenpos.share.domain.FakeProfanities
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import java.util.*

@DataJpaTest
class JpaTobeProductRepositoryTest
    @Autowired
    constructor(
        private val tobeProductRepository: JpaTobeProductRepository,
    ) {
        companion object {
            private val profanities = FakeProfanities("비속어")
        }

        @DisplayName("tobeProductRepository is not null")
        @Test
        fun case_1() {
            assertThat(tobeProductRepository).isNotNull()
        }

        @Test
        fun case_2() {
            // given
            val tobeProduct = TobeProduct(UUID.randomUUID(), "후라이드", 10_000, profanities)

            // when
            tobeProductRepository.save(tobeProduct)
            val count = tobeProductRepository.count()

            // then
            assertThat(count).isEqualTo(1)
        }
    }

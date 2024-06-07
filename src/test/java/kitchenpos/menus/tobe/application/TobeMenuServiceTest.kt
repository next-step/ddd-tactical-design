package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup
import kitchenpos.menus.tobe.domain.menuproduct.TobeMenuProduct
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Transactional
@SpringBootTest
class TobeMenuServiceTest
    @Autowired
    constructor(
        private val sut: TobeMenuService,
        private val tobeMenuGroupService: TobeMenuGroupService,
    ) {
        companion object {
            private lateinit var menuGroup: TobeMenuGroup
        }

        @BeforeEach
        fun setUp() {
            menuGroup = tobeMenuGroupService.create(TobeMenuGroup(UUID.randomUUID(), "후라이드"))
        }

        @DisplayName("Create Menu Test")
        @Test
        fun case_1() {
            // given
            val createMenuRequest =
                CreateMenuRequest(
                    name = "후라이드 치킨",
                    price = 10_000,
                    groupId = menuGroup.id,
                    displayed = true,
                    menuProducts = listOf(TobeMenuProduct(1, 10_000, UUID.randomUUID())),
                )

            // when
            val savedMenu = sut.create(createMenuRequest)

            // then
            assertThat(savedMenu).isNotNull()
        }
    }

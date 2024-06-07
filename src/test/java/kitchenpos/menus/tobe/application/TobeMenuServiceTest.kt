package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.application.dto.MenuProductRequest
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*
import kotlin.NoSuchElementException

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
                    menuProducts = listOf(MenuProductRequest(1, 10_000, UUID.randomUUID())),
                )

            // when
            val savedMenu = sut.create(createMenuRequest)

            // then
            assertThat(savedMenu).isNotNull()
        }

        @DisplayName("메뉴의 이름에는 비속어가 포함될 수 없다.")
        @Test
        fun case_2() {
            // given
            val createMenuRequest =
                CreateMenuRequest(
                    name = "비속어",
                    price = 10_000,
                    groupId = menuGroup.id,
                    displayed = true,
                    menuProducts = listOf(MenuProductRequest(1, 10_000, UUID.randomUUID())),
                )

            // when
            // then
            assertThatIllegalArgumentException().isThrownBy { sut.create(createMenuRequest) }
        }

        @DisplayName("메뉴의 가격은 0원 이상이어야 한다.")
        @Test
        fun case_3() {
            // given
            val createMenuRequest =
                CreateMenuRequest(
                    name = "후라이드 치킨",
                    price = -1,
                    groupId = menuGroup.id,
                    displayed = true,
                    menuProducts = listOf(MenuProductRequest(1, 10_000, UUID.randomUUID())),
                )

            // when
            // then
            assertThatIllegalArgumentException().isThrownBy { sut.create(createMenuRequest) }
        }

        @DisplayName("메뉴에 속한 상품의 수량은 0 이상이어야 한다.")
        @Test
        fun case_4() {
            // given
            val createMenuRequest =
                CreateMenuRequest(
                    name = "후라이드 치킨",
                    price = -1,
                    groupId = menuGroup.id,
                    displayed = true,
                    menuProducts = listOf(MenuProductRequest(-1, 10_000, UUID.randomUUID())),
                )

            // when
            // then
            assertThatIllegalArgumentException().isThrownBy { sut.create(createMenuRequest) }
        }

        @DisplayName("메뉴는 특정 메뉴 그룹에 속해야 한다.")
        @Test
        fun case_5() {
            // given
            val notExistsMenuGroupId = UUID.randomUUID()
            val createMenuRequest =
                CreateMenuRequest(
                    name = "후라이드 치킨",
                    price = 10_000,
                    groupId = notExistsMenuGroupId,
                    displayed = true,
                    menuProducts = listOf(MenuProductRequest(1, 10_000, UUID.randomUUID())),
                )

            // when
            // then
            assertThrows<NoSuchElementException> { sut.create(createMenuRequest) }
        }
    }

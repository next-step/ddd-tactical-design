package kitchenpos.menu.tobe.application

import kitchenpos.menu.tobe.application.dto.CreateMenuGroupReq
import kitchenpos.menu.tobe.domain.MenuGroup
import kitchenpos.menu.tobe.domain.MenuGroupRepository
import kitchenpos.menu.tobe.infra.FakeMenuGroupRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class MenuGroupServiceTest {

    private lateinit var menuGroupRepository: MenuGroupRepository
    private lateinit var menuGroupService: MenuGroupService

    @BeforeEach
    fun setUp() {
        menuGroupRepository = FakeMenuGroupRepository()
        menuGroupService = MenuGroupService(menuGroupRepository)
    }

    @DisplayName("MenuGroup을 등록한다")
    @Test
    fun createMenuGroup() {
        // given
        val request = CreateMenuGroupReq(name = "추천 메뉴")

        // when
        val menuGroup = menuGroupService.create(request)

        // then
        assertEquals(menuGroup.name, "추천 메뉴")
    }

    @DisplayName("MenuGroup을 전체 조회한다")
    @Test
    fun findAll() {
        menuGroupRepository.save(MenuGroup(name = "사장님 추천 메뉴"))
        menuGroupRepository.save(MenuGroup(name = "알바생 추천 메뉴"))

        val menuGroups = menuGroupService.findAll()

        assertThat(menuGroups).hasSize(2)
    }
}


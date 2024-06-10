package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuGroupRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import java.util.*

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@SpringBootTest
class TobeMenuGroupServiceTest(
    private val sut: TobeMenuGroupService,
) {
    @DisplayName("TobeMenuGroupService 는 null 이 아니다.")
    @Test
    fun case_1() {
        assertThat(sut).isNotNull()
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    fun case_2() {
        // given
        val id = UUID.randomUUID()
        val name = "후라이드 세트"
        val request = CreateMenuGroupRequest(id, name)

        // when
        val savedMenuGroup = sut.create(request)

        // then
        assertThat(savedMenuGroup).isNotNull()
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    fun case_3() {
        // given
        val id = UUID.randomUUID()
        val name = "후라이드 세트"
        sut.create(CreateMenuGroupRequest(id, name))

        // when
        val menuGroups = sut.findAll()

        // then
        assertThat(menuGroups.size).isEqualTo(1)
    }
}

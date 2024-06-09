package kitchenpos.menus.tobe.infra

import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatIllegalArgumentException
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.TestConstructor
import java.util.*

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DataJpaTest
class JpaTobeMenuGroupRepositoryTest(
    private val sut: JpaTobeMenuGroupRepository,
) {
    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    fun case_1() {
        // given
        val id = UUID.randomUUID()
        val name = "후라이드"
        val menuGroup = TobeMenuGroup(id, name)

        // when
        val savedMenuGroup = sut.save(menuGroup)

        // then
        assertThat(savedMenuGroup).isNotNull()
    }

    @DisplayName("메뉴 그룹의 이름은 비워 둘 수 없다.")
    @Test
    fun case_2() {
        // given
        val id = UUID.randomUUID()
        val name = ""

        // when
        // then
        assertThatIllegalArgumentException().isThrownBy { sut.save(TobeMenuGroup(id, name)) }
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    fun case_3() {
        // given
        sut.save(TobeMenuGroup(UUID.randomUUID(), "후라이드"))
        sut.save(TobeMenuGroup(UUID.randomUUID(), "양념"))

        // when
        val menuGroups = sut.findAll()

        // then
        assertThat(menuGroups.size).isEqualTo(2)
    }
}

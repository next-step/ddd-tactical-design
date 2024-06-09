package kitchenpos.menus.tobe.application

import kitchenpos.menus.tobe.application.dto.CreateMenuRequest
import kitchenpos.menus.tobe.application.dto.MenuProductRequest
import kitchenpos.menus.tobe.domain.TobeMenuRepository
import kitchenpos.menus.tobe.domain.menu.TobeMenu
import kitchenpos.menus.tobe.domain.menu.TobeMenuProduct
import kitchenpos.menus.tobe.domain.menu.TobeProductClient
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup
import kitchenpos.share.domain.FakeProfanities
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.BDDMockito.anyList
import org.mockito.BDDMockito.given
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.TestConstructor
import org.springframework.transaction.annotation.Transactional
import java.util.*

@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@SpringBootTest
class TobeMenuServiceTest(
    private val sut: TobeMenuService,
    private val tobeMenuGroupService: TobeMenuGroupService,
    @MockBean
    private val tobeProductClient: TobeProductClient,
    private val tobeMenuRepository: TobeMenuRepository,
) {
    companion object {
        private lateinit var menuGroup: TobeMenuGroup
    }

    @BeforeEach
    fun setUp() {
        menuGroup = tobeMenuGroupService.create(TobeMenuGroup(UUID.randomUUID(), "후라이드"))
    }

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다. (등록된 상품 o)")
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
        given(tobeProductClient.validateAllProductsExists(anyList())).willReturn(true)

        // when
        val savedMenu = sut.create(createMenuRequest)

        // then
        assertThat(savedMenu).isNotNull()
    }

    @DisplayName("1 개 이상의 등록된 상품으로 메뉴를 등록할 수 있다. (등록된 상품 x)")
    @Test
    fun case_1_1() {
        // given
        val createMenuRequest =
            CreateMenuRequest(
                name = "후라이드 치킨",
                price = 10_000,
                groupId = menuGroup.id,
                displayed = true,
                menuProducts = listOf(MenuProductRequest(1, 10_000, UUID.randomUUID())),
            )
        given(tobeProductClient.validateAllProductsExists(anyList())).willReturn(false)

        // when
        // then
        assertThatIllegalArgumentException().isThrownBy { sut.create(createMenuRequest) }
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

    @DisplayName("메뉴의 목록을 조회할 수 있다.")
    @Test
    fun case_6() {
        // given
        val menu = createMenu()
        tobeMenuRepository.save(menu)

        // when
        val allMenus = sut.findAll()

        // then
        assertThat(allMenus.size).isEqualTo(1)
    }

    @DisplayName("메뉴를 숨길 수 있다.")
    @Test
    fun case_7() {
        // given
        val menu = createMenu()
        val savedMenu = tobeMenuRepository.save(menu)
        val menuId = savedMenu.id

        // when
        val hiddenMenu = sut.hide(menuId)

        // then
        assertThat(hiddenMenu.isDisplayed).isFalse()
    }

    @DisplayName("메뉴를 노출할 수 있다. (메뉴의 가격 <= 메뉴에 속한 상품 금액의 합)")
    @Test
    fun case_8() {
        // given
        val menu = createMenu(displayed = false)
        val savedMenu = tobeMenuRepository.save(menu)
        val menuId = savedMenu.id

        // when
        val displayedMenu = sut.display(menuId)

        // then
        assertThat(displayedMenu.isDisplayed).isTrue()
    }

    @DisplayName("메뉴의 가격이 메뉴에 속한 상품 금액의 합보다 높을 경우 메뉴를 노출할 수 없다.")
    @Test
    fun case_9() {
        // given
        val menu = createMenu(menuPrice = 14_000, menuProductPrice = 15_000, displayed = false)
        val savedMenu = tobeMenuRepository.save(menu)
        val menuId = savedMenu.id

        // when
        val changeDisplayedMenu = sut.display(menuId)

        // then
        assertThat(changeDisplayedMenu.isDisplayed).isTrue()
    }

    @DisplayName("메뉴의 가격을 변경할 수 있다. (메뉴의 가격 <= 메뉴에 속한 상품 금액의 합)")
    @Test
    fun case_10() {
        // given
        val menu = createMenu(menuProductPrice = 14_000, displayed = false)
        val savedMenu = tobeMenuRepository.save(menu)
        val menuId = savedMenu.id
        val newPrice = 14_000

        // when
        val priceChangedMenu = sut.changePrice(menuId, newPrice)

        // then
        assertThat(priceChangedMenu.price).isEqualTo(newPrice)
    }

    @DisplayName("메뉴의 가격을 변경할 수 없다. (메뉴의 가격 > 메뉴에 속한 상품 금액의 합)")
    @Test
    fun case_11() {
        // given
        val menu = createMenu(displayed = false)
        val savedMenu = tobeMenuRepository.save(menu)
        val menuId = savedMenu.id

        // when
        // then
        assertThatIllegalStateException().isThrownBy { sut.changePrice(menuId, 14_000) }
    }

    private fun createMenu(
        menuPrice: Int = 10_000,
        menuProductPrice: Int = 10_000,
        displayed: Boolean = true,
    ): TobeMenu {
        val name = "후라이드"
        val menuProducts = listOf(TobeMenuProduct(1, menuProductPrice, UUID.randomUUID()))
        val menu = TobeMenu(name, menuPrice, displayed, menuGroup.id, menuProducts, FakeProfanities())
        return menu
    }
}

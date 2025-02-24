package kitchenpos.menu.tobe.application;



import kitchenpos.menu.tobe.fake.InMemoryMenuGroupRepository;
import kitchenpos.menu.tobe.fixture.MenuGroupFixture;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menu.tobe.domain.menugroup.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class MenuGroupServiceTest {
    private MenuGroupRepository menuGroupRepository;
    private MenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        menuGroupService = new MenuGroupService(menuGroupRepository);
    }

    @Nested
    class CreateMenuGroup {
        @Test
        @DisplayName("메뉴 그룹을 생성한다")
        void createMenuGroup() {
            // given
            MenuGroup request = MenuGroupFixture.menuGroup("나의 메뉴 그룹");

            // when
            MenuGroup created = menuGroupService.create(request);

            // then
            assertAll(
                    () -> assertThat(created.getId()).isNotNull(),
                    () -> assertThat(created.getNameValue()).isEqualTo("나의 메뉴 그룹")
            );
        }

        @DisplayName("이름이 없는 메뉴 그룹은 생성할 수 없다")
        @NullAndEmptySource
        @ParameterizedTest
        void cannotCreateMenuGroupWithoutName(String name) {
            // given
            assertThatThrownBy(() -> MenuGroupFixture.menuGroup(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("메뉴 그룹 이름은 비어있을 수 없습니다");
        }

        @DisplayName("이름이 공백 문자로만 이루어진 경우 메뉴 그룹을 생성할 수 없다")
        @ParameterizedTest
        @ValueSource(strings = {" ", "  ", "\t", "\n"})
        void cannotCreateMenuGroupWithBlankName(String blankName) {
            // given
            assertThatThrownBy(() -> MenuGroupFixture.menuGroup(blankName))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("메뉴 그룹 이름은 비어있을 수 없습니다");
        }
    }

    @Test
    @DisplayName("메뉴 그룹 목록을 조회한다")
    void findAllMenuGroups() {
        // given
        MenuGroup group1 = MenuGroupFixture.menuGroup("메뉴 1");
        MenuGroup group2 = MenuGroupFixture.menuGroup("메뉴 2");
        menuGroupRepository.save(group1);
        menuGroupRepository.save(group2);

        // when
        List<MenuGroup> result = menuGroupService.findAll();

        // then
        assertThat(result).hasSize(2)
                .extracting(MenuGroup::getNameValue)
                .containsExactlyInAnyOrder("메뉴 1", "메뉴 2");
    }
}
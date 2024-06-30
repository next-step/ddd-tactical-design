package kitchenpos.menus.tobe.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.tobe.MenuFixtures;
import kitchenpos.menus.application.dto.MenuGroupCreationResponseDto;
import kitchenpos.menus.application.dto.MenuGroupsCreationResponseDto;
import kitchenpos.menus.domain.menugroup.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class MenuGroupServiceTest {
  private MenuGroupRepository menuGroupRepository;
  private MenuGroupService menuGroupService;

  @BeforeEach
  void setUp() {
    menuGroupRepository = new InMemoryMenuGroupRepository();
    menuGroupService = new MenuGroupService(menuGroupRepository);
  }

  @DisplayName("메뉴 그룹을 등록할 수 있다.")
  @Test
  void create() {
    final String expected = "두마리메뉴";
    final MenuGroupCreationResponseDto actual = menuGroupService.create(expected);
    assertThat(actual).isNotNull();
    assertAll(
        () -> assertThat(actual.getId()).isNotNull(),
        () -> assertThat(actual.getName()).isEqualTo(expected));
  }

  @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
  @NullAndEmptySource
  @ParameterizedTest
  void create(final String name) {
    assertThatThrownBy(() -> menuGroupService.create(name))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
  @Test
  void findAll() {
    menuGroupRepository.save(MenuFixtures.menuGroup("두마리메뉴"));
    MenuGroupsCreationResponseDto actual = menuGroupService.findAll();
    assertThat(actual.getList()).hasSize(1);
  }
}

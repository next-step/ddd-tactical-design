package kitchenpos.menus.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.List;
import kitchenpos.fake.InMemoryMenuGroupRepository;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroup;
import kitchenpos.menus.domain.tobe.menugroup.MenuGroupName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("MenuGroupService")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuGroupServiceTest {

    private MenuGroupRepository menuGroupRepository = new InMemoryMenuGroupRepository();

    private MenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupService = new MenuGroupService(menuGroupRepository);
    }

    @Test
    void 메뉴그룹을_생성한다() {
        MenuGroup actual = menuGroupService.create(new MenuGroupName("치킨"));

        assertThat(actual.getId()).isNotNull();
    }

    @Test
    void 메뉴그룹이름이_비어있으면_예외를던진다() {
        assertThatIllegalArgumentException().isThrownBy(
                () -> menuGroupService.create(new MenuGroupName("")));
    }

    @Test
    void 메뉴그룹을_조회한다() {
        menuGroupService.create(new MenuGroupName("치킨"));
        menuGroupService.create(new MenuGroupName("피자"));

        List<MenuGroup> actual = menuGroupService.findAll();

        assertThat(actual).hasSize(2);
    }
}
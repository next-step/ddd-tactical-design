package kitchenpos.menu.application;

import static kitchenpos.menu.Fixtures.menuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import kitchenpos.common.name.NameFactory;
import kitchenpos.common.profanity.FakeProfanityDetectService;
import kitchenpos.common.profanity.domain.ProfanityDetectService;
import kitchenpos.menu.InMemoryMenuGroupRepository;
import kitchenpos.menu.tobe.application.dto.CreateMenuGroupCommand;
import kitchenpos.menu.tobe.domain.entity.MenuGroup;
import kitchenpos.menu.tobe.domain.repository.MenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuGroupServiceTest {

    private MenuGroupRepository menuGroupRepository;

    private MenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryMenuGroupRepository();
        final ProfanityDetectService profanityDetectService = new FakeProfanityDetectService();
        final NameFactory nameFactory = new NameFactory(profanityDetectService);
        menuGroupService = new MenuGroupService(menuGroupRepository, nameFactory);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final CreateMenuGroupCommand command = createMenuGroupCommand("두마리메뉴");
        final MenuGroup actual = menuGroupService.create(command);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.id).isNotNull(),
            () -> assertThat(actual.name.value).isEqualTo(command.name)
        );
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        menuGroupRepository.save(menuGroup("두마리메뉴"));
        final List<MenuGroup> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    private CreateMenuGroupCommand createMenuGroupCommand(final String name) {
        return new CreateMenuGroupCommand(name);
    }
}

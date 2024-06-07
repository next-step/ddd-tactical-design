package kitchenpos.menus.tobe.domain.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import kitchenpos.menus.tobe.domain.repository.InMemoryMenuGroupRepository;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.dto.MenuGroupCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CreateMenuGroupTest {
    private CreateMenuGroup createMenuGroup;

    @BeforeEach
    public void setup() {
        createMenuGroup = new CreateMenuGroupTestFixture(new InMemoryMenuGroupRepository());
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        final MenuGroupCreateDto request = createMenuGroupRequest("두마리메뉴");
        final MenuGroup actual = createMenuGroup.execute(request);
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getName()).isEqualTo(request.getName())
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        final MenuGroupCreateDto request = createMenuGroupRequest(name);
        assertThatThrownBy(() -> createMenuGroup.execute(request))
            .isInstanceOf(IllegalArgumentException.class);
    }


    private MenuGroupCreateDto createMenuGroupRequest(final String name) {
        return new MenuGroupCreateDto(name);
    }

}

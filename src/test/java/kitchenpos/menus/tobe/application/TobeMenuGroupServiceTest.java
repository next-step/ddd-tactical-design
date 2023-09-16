package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.application.dto.TobeMenuGroupResponse;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupName;
import kitchenpos.menus.tobe.domain.menugroup.TobeMenuGroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeMenuGroupServiceTest {
    private TobeMenuGroupRepository tobeMenuGroupRepository;
    private TobeMenuGroupService tobeMenuGroupService;

    @BeforeEach
    void setUp() {
        tobeMenuGroupRepository = new InMemoryTobeMenuGroupRepository();
        tobeMenuGroupService = new TobeMenuGroupService(tobeMenuGroupRepository);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create() {
        TobeMenuGroupResponse actual = tobeMenuGroupService.create("두마리메뉴");
        assertThat(actual).isNotNull();
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo("두마리메뉴")
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create(final String name) {
        assertThatThrownBy(() -> tobeMenuGroupService.create(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        tobeMenuGroupRepository.save(tobeMenuGroup("두마리메뉴"));
        final List<TobeMenuGroupResponse> actual = tobeMenuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }

    public static TobeMenuGroup tobeMenuGroup(final String name) {
        return new TobeMenuGroup(UUID.randomUUID(), new TobeMenuGroupName(name));
    }
}

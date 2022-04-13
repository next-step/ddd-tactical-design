package kitchenpos.menus.application;

import kitchenpos.menus.domain.tobe.domain.TobeMenuGroupRepository;
import kitchenpos.menus.dto.MenuGroupDto;
import kitchenpos.menus.dto.MenuGroupRegisterRequest;
import kitchenpos.menus.dto.MenuGroupRegisterResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static kitchenpos.Fixtures.tobeMenuGroup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class TobeMenuGroupServiceTest {

    private TobeMenuGroupRepository menuGroupRepository;
    private TobeMenuGroupService menuGroupService;

    @BeforeEach
    void setUp() {
        menuGroupRepository = new InMemoryTobeMenuGroupRepository();
        menuGroupService = new TobeMenuGroupService(menuGroupRepository);
    }

    @DisplayName("메뉴 그룹을 등록할 수 있다.")
    @Test
    void create_success() {
        //given
        final MenuGroupRegisterRequest 등록요청 = new MenuGroupRegisterRequest("후라이드그룹");

        //when
        final MenuGroupRegisterResponse 등록된메뉴 = menuGroupService.create(등록요청);

        //then
        assertThat(등록된메뉴).isNotNull();
        assertAll(
                () -> assertThat(등록된메뉴.getId()).isNotNull(),
                () -> assertThat(등록된메뉴.getName()).isEqualTo(등록요청.getName())
        );
    }

    @DisplayName("메뉴 그룹의 이름이 올바르지 않으면 등록할 수 없다.")
    @NullAndEmptySource
    @ParameterizedTest
    void create_fail(final String name) {
        //given
        final MenuGroupRegisterRequest 등록요청 = new MenuGroupRegisterRequest(name);

        //when&&then
        assertThatThrownBy(() -> menuGroupService.create(등록요청))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 그룹의 목록을 조회할 수 있다.")
    @Test
    void findAll() {
        //given
        menuGroupRepository.save(tobeMenuGroup("두마리메뉴"));

        //when&&then
        final List<MenuGroupDto> actual = menuGroupService.findAll();
        assertThat(actual).hasSize(1);
    }
}

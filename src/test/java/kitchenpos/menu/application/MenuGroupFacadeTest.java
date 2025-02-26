package kitchenpos.menu.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.infrastructure.external.FakeProfanityClient;
import kitchenpos.menu.application.dto.MenuGroupRequest;
import kitchenpos.menu.application.dto.MenuGroupResponse;
import kitchenpos.menu.application.facade.MenuGroupFacade;
import kitchenpos.menu.domain.entity.MenuGroup;
import kitchenpos.menu.domain.exception.MenuGroupNameException;
import kitchenpos.menu.domain.fixture.MenuGroupFixture;
import kitchenpos.menu.domain.repository.MenuGroupRepository;
import kitchenpos.menu.domain.service.DefaultMenuGroupService;
import kitchenpos.menu.domain.service.MenuGroupCommandService;
import kitchenpos.menu.domain.service.MenuGroupQueryService;
import kitchenpos.menu.domain.service.MenuPurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuGroupFacadeTest {

    private MenuGroupFacade menuGroupFacade;
    private MenuGroupQueryService menuGroupQueryService;
    private MenuGroupCommandService menuGroupCommandService;
    private final MenuPurgomalumClient purgomalumClient = new FakeProfanityClient(List.of("나쁜", "XXX"));
    @Mock
    private MenuGroupRepository menuGroupRepository;

    private MenuGroupRequest.Create menuGroup;

    private MenuGroup menuGroupEntity;

    @BeforeEach
    void setUp() {
        menuGroupQueryService = new DefaultMenuGroupService(menuGroupRepository, purgomalumClient);
        menuGroupCommandService = new DefaultMenuGroupService(menuGroupRepository, purgomalumClient);
        menuGroupFacade = new MenuGroupFacade(menuGroupQueryService, menuGroupCommandService);
        menuGroup = MenuGroupFixture.init().create();
        menuGroupEntity = MenuGroupFixture.init().toEntity();
    }

    @Nested
    @DisplayName("메뉴 그룹 조회")
    class 메뉴_그룹_조회 {

        @Test
        @DisplayName("성공 : 특정 조건 없이 상품의 모든 목록을 조회할 수 있다.")
        void 메뉴그룹목록_조회() {
            when(menuGroupRepository.findAll()).thenReturn(List.of(menuGroupEntity));

            List<MenuGroupResponse.GetGroup> result = menuGroupFacade.findAll();

            assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertEquals(result.size(), 1)
            );
        }
    }

    @Nested
    @DisplayName("메뉴 그룹 등록")
    class 메뉴그룹_등록 {

        @Test
        @DisplayName("성공")
        void 메뉴그룹_등록_성공() {
            when(menuGroupRepository.save(Mockito.any(MenuGroup.class))).thenReturn(menuGroupEntity);

            var result = menuGroupFacade.create(menuGroup);

            assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(result.name(), menuGroup.name())
            );

        }

        @ParameterizedTest
        @DisplayName("메뉴 그룹명을 반드시 가진다.")
        @NullAndEmptySource
        @ValueSource(strings = {" ", "   ", "\t", "\n"})
        void 메뉴그룹명_유효성_검사(final String name) {
            menuGroup = MenuGroupFixture.test(name).create();

            assertThatExceptionOfType(MenuGroupNameException.class)
                .isThrownBy(() -> menuGroupFacade.create(menuGroup))
                .withMessage(ErrorCode.MENU_GROUP_NAME_NOT_ALLOWED.toString());
        }
    }

}

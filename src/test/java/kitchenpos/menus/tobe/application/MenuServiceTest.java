package kitchenpos.menus.tobe.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.application.dto.MenuRequestDto;
import kitchenpos.menus.tobe.domain.MenuManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuManager menuManager;

    @DisplayName("메뉴 생성한다.")
    @Test
    void create() {
        MenuRequestDto requestDto = new MenuRequestDto("후라이드 + 후라이드", BigDecimal.valueOf(19_000L), Fixtures.twoChickens().getId(),
            Arrays.asList(Fixtures.menuProduct()));

        when(menuManager.create(any())).thenReturn(Fixtures.twoFriedChickens());

        assertThat(menuService.create(requestDto)).isEqualTo(Fixtures.twoFriedChickens().getId());
    }

    @DisplayName("메뉴 리스트를 가져온다.")
    @Test
    void list() {
        when(menuManager.list()).thenReturn(Arrays.asList(Fixtures.twoFriedChickens()));

        assertThat(menuService.list())
            .containsExactlyInAnyOrderElementsOf(Arrays.asList(Fixtures.twoFriedChickens()));
    }

}

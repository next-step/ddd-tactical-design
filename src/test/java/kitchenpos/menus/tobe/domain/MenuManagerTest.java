package kitchenpos.menus.tobe.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import kitchenpos.menus.tobe.Fixtures;
import kitchenpos.menus.tobe.application.dto.MenuRequestDto;
import kitchenpos.menus.tobe.infrastructure.ProductPriceManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuManagerTest {

    @InjectMocks
    private MenuManager menuManager;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuProductRepository menuProductRepository;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @Mock
    private ProductPriceManager productPriceManager;

    @DisplayName("메뉴를 등록한다.")
    @Test
    void create() {
        MenuRequestDto requestDto = new MenuRequestDto("후라이드 + 후라이드", BigDecimal.valueOf(19_000L),
            Fixtures.twoChickens().getId(),
            Arrays.asList(Fixtures.menuProduct()));

        when(menuGroupRepository.findById(any())).thenReturn(Optional.of(Fixtures.twoChickens()));
        when(productPriceManager.getPrice(any())).thenReturn(BigDecimal.valueOf(9_500L));
        when(menuRepository.save(any())).thenReturn(Fixtures.twoFriedChickens());
        when(menuProductRepository.findByMenuId(any()))
            .thenReturn(Arrays.asList(Fixtures.menuProduct()));

        Menu created = menuManager.create(requestDto);

        assertMenu(created, Fixtures.twoFriedChickens());
    }

    @DisplayName("메뉴 리스트를 가져온다.")
    @Test
    void list() {
        when(menuRepository.findAll()).thenReturn(Arrays.asList(Fixtures.twoFriedChickens()));
        when(menuProductRepository.findByMenuId(any()))
            .thenReturn(Arrays.asList(Fixtures.menuProduct()));

        assertThat(menuManager.list())
            .containsExactlyInAnyOrderElementsOf(Arrays.asList(Fixtures.twoFriedChickens()));
    }

    private void assertMenu(final Menu expected, final Menu actual) {
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice()),
            () -> assertThat(actual.getMenuGroup()).isEqualTo(expected.getMenuGroup()),
            () -> assertThat(actual.getMenuProducts())
                .containsExactlyInAnyOrderElementsOf(expected.getMenuProducts())
        );
    }

}

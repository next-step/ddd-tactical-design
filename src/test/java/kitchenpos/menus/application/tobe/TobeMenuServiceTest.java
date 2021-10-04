package kitchenpos.menus.application.tobe;

import kitchenpos.menus.tobe.application.TobeMenuService;
import kitchenpos.menus.tobe.domain.*;
import kitchenpos.menus.tobe.infra.MenuAdaptor;
import kitchenpos.menus.tobe.ui.MenuForm;
import kitchenpos.tobeinfra.TobePurgomalumClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;

import static kitchenpos.menus.application.tobe.TobeMenusFixtures.*;
import static kitchenpos.menus.application.tobe.TobeMenusFixtures.menuForm;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class TobeMenuServiceTest {

    @Mock
    private TobePurgomalumClient purgomalumClient;

    @Mock
    private MenuAdaptor menuTranslator;

    @Mock
    private TobeMenuRepository menuRepository;

    @Mock
    private TobeMenuGroupRepository menuGroupRepository;

    @InjectMocks
    private TobeMenuService menuService;

    @DisplayName("메뉴 생성")
    @Test
    void create() {
        // given
        MenuForm menuForm = menuForm();
        TobeMenuGroup menuGroup = tobeMenuGroup();
        given(menuGroupRepository.findById(any())).willReturn(Optional.of(menuGroup));
        given(menuTranslator.productFindAllByIdIn(any())).willReturn(menuProducts().getMenuProducts());
        given(menuRepository.save(any())).willReturn(menu());

        // when
        MenuForm save = menuService.create(menuForm);

        // then
        assertThat(save).isNotNull();
    }

    @DisplayName("메뉴는 메뉴그룹에 포함안되면 예외처리")
    @Test
    void negativeEmptyMenuGroup() {
        MenuForm menuForm = menuForm();

        assertThatThrownBy(() -> menuService.create(menuForm))
                .isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("메뉴에 상품이 없으면 예외처리")
    @Test
    void negativeEmptyMenuProduct() {
        MenuForm menuForm = menuForm();
        menuForm.setMenuProducts(null);

        given(menuGroupRepository.findById(any())).willReturn(Optional.of(tobeMenuGroup()));

        assertThatIllegalArgumentException()
                .isThrownBy(() -> menuService.create(menuForm));
    }

    @DisplayName("메뉴 가격이 메뉴의 상품 가격이 보다 높으면 예외처리")
    @Test
    void negativeMenuProductPrice() {
        // given
        MenuForm menuForm = menuForm();
        menuForm.setPrice(BigDecimal.valueOf(34_000L));

        given(menuGroupRepository.findById(any())).willReturn(Optional.of(tobeMenuGroup()));
        given(menuTranslator.productFindAllByIdIn(any())).willReturn(menuProducts().getMenuProducts());

        // when, then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> menuService.create(menuForm));
    }

    @DisplayName("메뉴 가격은 변경 가능")
    @Test
    void changeMenuPrice() {
        // given
        MenuPrice menuPrice = new MenuPrice(BigDecimal.valueOf(30_000L));
        TobeMenu menu = menu();
        menu.changePrice(menuPrice);

        menu.getMenuPrice();
        MenuForm menuForm = MenuForm.of(menu);

        given(menuRepository.findById(menuForm.getId())).willReturn(Optional.of(menu));

        // when
        MenuForm changeMenu = menuService.changePrice(menuForm.getId(), menuForm);

        assertThat(changeMenu.getPrice()).isEqualTo(menuForm.getPrice());
    }

    @DisplayName("메뉴 노출")
    @Test
    void menuDisplay() {
        // given
        TobeMenu menu = menu();
        MenuForm menuForm = MenuForm.of(menu);
        menuForm.setDisplayed(false);

        given(menuRepository.findById(menuForm.getId())).willReturn(Optional.of(menu));

        // when
        MenuForm displayMenu = menuService.display(menu.getId());

        // then
        assertThat(displayMenu.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴 숨김")
    @Test
    void menuHide() {
        // given
        TobeMenu menu = menu();
        MenuForm menuForm = MenuForm.of(menu);
        menuForm.setDisplayed(true);

        given(menuRepository.findById(menuForm.getId())).willReturn(Optional.of(menu));

        // when
        MenuForm displayMenu = menuService.hide(menu.getId());

        // then
        assertThat(displayMenu.isDisplayed()).isFalse();
    }
}

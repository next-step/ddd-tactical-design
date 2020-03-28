package kitchenpos.menus.tobe.domain.menu.application;

import kitchenpos.menus.tobe.domain.menu.dto.MenuResponseDto;
import kitchenpos.menus.tobe.domain.menu.infra.MenuEntity;
import kitchenpos.menus.tobe.domain.menu.infra.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.service.MenuRegisterService;
import kitchenpos.menus.tobe.domain.menu.service.ProductService;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProductVO;
import kitchenpos.menus.tobe.domain.menu.vo.MenuProducts;
import kitchenpos.menus.tobe.domain.menu.vo.MenuVO;
import kitchenpos.menus.tobe.domain.menu.vo.WrongMenuPriceException;
import kitchenpos.menus.tobe.domain.menugroup.application.MenuGroupService;
import kitchenpos.menus.tobe.domain.menugroup.exception.MenuGroupNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class MenuRegisterServiceTest {

    @Mock private MenuRepository menuRepository;
    @Mock private MenuGroupService menuGroupService;
    @Mock private ProductService productService;

    @InjectMocks
    private MenuRegisterService menuRegisterService;

    @DisplayName("등록된 MenuGroup을 선택해야 한다.")
    @Test
    void menuGroupNotExist (){
        MenuVO menuVO = new MenuVO(1L, BigDecimal.valueOf(16000L), "간장세트", 1L);
        MenuProducts menuProducts = new MenuProducts();

        given(menuGroupService.isExist(menuVO.getMenuGroupId()))
            .willThrow(new MenuGroupNotFoundException());

        assertThatExceptionOfType(MenuGroupNotFoundException.class)
            .isThrownBy(() -> menuRegisterService.register(menuVO, menuProducts));
    }

    @DisplayName("메뉴가격은 금액보다 클 수 없다.")
    @Test
    void ComparePriceToAcount (){
        MenuVO menuVO = new MenuVO(1L, BigDecimal.valueOf(26000L), "간장세트", 1L);

        MenuProducts menuProducts = new MenuProducts();
        menuProducts.add(new MenuProductVO(1L, 1L));
        menuProducts.add(new MenuProductVO(2L, 1L));

        MenuProducts findMenuProducts = new MenuProducts();
        findMenuProducts.add(new MenuProductVO(1L, 1L, BigDecimal.valueOf(10000L)));
        findMenuProducts.add(new MenuProductVO(2L, 1L, BigDecimal.valueOf(15000L)));

        given(menuGroupService.isExist(menuVO.getMenuGroupId())).willReturn(true);
        given(productService.findAllPrice(menuProducts)).willReturn(findMenuProducts);

        assertThatExceptionOfType(WrongMenuPriceException.class)
            .isThrownBy(() -> menuRegisterService.register(menuVO, menuProducts));
    }

    @DisplayName("메뉴를 등록 할 수 있다.")
    @Test
    void register (){
        MenuVO menuVO = new MenuVO(1L, BigDecimal.valueOf(24000L), "간장세트", 1L);

        MenuProducts menuProducts = new MenuProducts();
        menuProducts.add(new MenuProductVO(1L, 1L));
        menuProducts.add(new MenuProductVO(2L, 1L));

        MenuProducts findMenuProducts = new MenuProducts();
        findMenuProducts.add(new MenuProductVO(1L, 1L, BigDecimal.valueOf(10000L)));
        findMenuProducts.add(new MenuProductVO(2L, 1L, BigDecimal.valueOf(15000L)));

        given(menuGroupService.isExist(menuVO.getMenuGroupId())).willReturn(true);
        given(productService.findAllPrice(menuProducts)).willReturn(findMenuProducts);

        MenuEntity menu = new MenuEntity(menuVO);
        menu.addMenuProducts(findMenuProducts);

        MenuEntity savedMenu = new MenuEntity(menuVO);
        savedMenu.addMenuProducts(findMenuProducts);

        given(menuRepository.save(menu)).willReturn(menu);

        MenuResponseDto responseDto = new MenuResponseDto(savedMenu);
        assertThat(menuRegisterService.register(menuVO, menuProducts))
            .isEqualTo(responseDto);
    }

}

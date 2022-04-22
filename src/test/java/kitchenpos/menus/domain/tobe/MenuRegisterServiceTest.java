package kitchenpos.menus.domain.tobe;

import kitchenpos.menus.domain.tobe.menugroup.MenuGroupNotFoundException;
import kitchenpos.menus.dto.MenuProductRequest;
import kitchenpos.menus.dto.MenuRequest;
import kitchenpos.menus.dto.ProductResponse;
import kitchenpos.support.StubBanWordFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MenuRegisterServiceTest {
    private MenuRegisterService menuRegisterService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    private MenuRequest menuRequest;

    private ProductResponse productResponse;

    private MenuProductRequest menuProduct1;
    private MenuProductRequest menuProduct2;

    private List<MenuProductRequest> menuProductsRequest;

    @BeforeEach
    void setUp() {
        menuProduct1 = new MenuProductRequest(UUID.randomUUID(), 1);
        menuProduct2 = new MenuProductRequest(UUID.randomUUID(), 1);
        menuProductsRequest = Arrays.asList(menuProduct1, menuProduct2);
        menuRegisterService = new MenuRegisterService(eventPublisher, new StubBanWordFilter(false));
    }

    @Disabled
    @DisplayName("Menu는 식별자와 Menu Name, 가격, MenuProducts를 가진다.")
    @Test
    void create() {
        menuRequest = new MenuRequest("반반치킨", BigDecimal.valueOf(10_000), UUID.randomUUID(), menuProductsRequest, true);
        productResponse = new ProductResponse(null, BigDecimal.valueOf(16_000));

        final Menu actual = menuRegisterService.registerMenu(menuRequest, productResponse);

        assertAll(
                () -> assertThat(actual).extracting("name").isNotNull(),
                () -> assertThat(actual).extracting("price").isNotNull(),
                () -> assertThat(actual).extracting("menuProducts").isNotNull()
        );
        verify(eventPublisher).publishEvent(ArgumentMatchers.any());
    }

    @Disabled
    @DisplayName("Menu는 MenuGroup에 존재해야한다.")
    @Test
    void create_with_not_exist_menu_group() {
        menuRequest = new MenuRequest("반반치킨", BigDecimal.valueOf(10_000), UUID.randomUUID(), menuProductsRequest, true);
        productResponse = new ProductResponse(null, BigDecimal.valueOf(16_000));
        doThrow(MenuGroupNotFoundException.class).when(eventPublisher).publishEvent(ArgumentMatchers.any());

        assertThatCode(() -> {
            menuRegisterService.registerMenu(menuRequest, productResponse);
        }).isInstanceOf(MenuGroupNotFoundException.class);
    }

    @Disabled
    @DisplayName("`Menu`의 가격은 `MenuProducts`의 금액의 합보다 적거나 같아야 한다.")
    @Test
    void create_with_invalid_price() {
        menuRequest = new MenuRequest("반반치킨", BigDecimal.valueOf(20_000), UUID.randomUUID(), menuProductsRequest, true);
        productResponse = new ProductResponse(null, BigDecimal.valueOf(10_000));

        assertThatCode(() -> {
            menuRegisterService.registerMenu(menuRequest, productResponse);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}

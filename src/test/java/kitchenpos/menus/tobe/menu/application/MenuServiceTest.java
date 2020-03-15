package kitchenpos.menus.tobe.menu.application;

import kitchenpos.menus.tobe.menu.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    MenuRepository menuRepository;
    @Mock
    Products products;

    @InjectMocks
    MenuService menuService;

    @DisplayName("메뉴를 생성할 수 있다.")
    @ParameterizedTest
    @MethodSource(value = "provideValidPrice")
    void create(final BigDecimal price) {
        // given
        final String name = "새로운 메뉴";
        final Long menuGroupId = 1L;
        final List<ProductQuantityDto> productQuantityDtos = Arrays.asList(
                new ProductQuantityDto(1L, 1L),
                new ProductQuantityDto(2L, 2L)
        );
        MenuCreationRequestDto menuCreationRequestDto = new MenuCreationRequestDto(name, price, menuGroupId, productQuantityDtos);

        given(products.getProductPricesByProductIds(any())).willReturn(Arrays.asList(
                new ProductPriceDto(1L, BigDecimal.valueOf(1000L)),
                new ProductPriceDto(2L, BigDecimal.valueOf(2000L))
        ));

        given(menuRepository.save(any(Menu.class))).willAnswer(invocation -> {
            final Menu menu = new Menu(name, price, menuGroupId, Arrays.asList(
                    new MenuProduct(1L, BigDecimal.valueOf(1000), 1L),
                    new MenuProduct(2L, BigDecimal.valueOf(2000), 2L)
            ));
            ReflectionTestUtils.setField(menu, "id", 1L);
            return menu;
        });

        // when
        final MenuCreationResponseDto result = menuService.create(menuCreationRequestDto);

        // then
        assertThat(result.getId()).isEqualTo(1L);
    }

    private static Stream provideValidPrice() {
        return Stream.of(
                BigDecimal.valueOf(0),
                BigDecimal.valueOf(1000),
                BigDecimal.valueOf(5000)
        );
    }

    @DisplayName("메뉴 생성 시, 메뉴그룹이 존재해야한다.")
    @Test
    void createFailsWhenMenuGroupNotFound() {
        // given
        // when
        // then

        fail("메뉴 생성 시, 메뉴그룹이 존재해야한다.");
    }

    @DisplayName("메뉴 생성 시, 제품이 중복될 수 없다.")
    @Test
    void createFailsWhenProductsDuplicated() {
        // given
        final String name = "새로운 메뉴";
        final BigDecimal price = BigDecimal.valueOf(1000);
        final Long menuGroupId = 1L;
        final List<ProductQuantityDto> productQuantityDtos = Arrays.asList(
                new ProductQuantityDto(1L, 1L),
                new ProductQuantityDto(1L, 2L)
        );
        MenuCreationRequestDto menuCreationRequestDto = new MenuCreationRequestDto(name, price, menuGroupId, productQuantityDtos);

        // when
        // then
        assertThatThrownBy(() -> {
            menuService.create(menuCreationRequestDto);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 생성 시, 메뉴명을 입력해야한다.")
    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"", "  "})
    void createFailsWhenNameIsBlank(final String name) {
        // given
        final BigDecimal price = BigDecimal.valueOf(1000);
        final Long menuGroupId = 1L;
        final List<ProductQuantityDto> productQuantityDtos = Arrays.asList(
                new ProductQuantityDto(1L, 1L),
                new ProductQuantityDto(2L, 2L)
        );
        MenuCreationRequestDto menuCreationRequestDto = new MenuCreationRequestDto(name, price, menuGroupId, productQuantityDtos);

        given(products.getProductPricesByProductIds(any())).willReturn(Arrays.asList(
                new ProductPriceDto(1L, BigDecimal.valueOf(1000L)),
                new ProductPriceDto(2L, BigDecimal.valueOf(2000L))
        ));

        // when
        // then
        assertThatThrownBy(() -> {
            menuService.create(menuCreationRequestDto);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 생성 시, 메뉴가격은 0원 이상이여야한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideInvalidPrice")
    void createFailsWhenPriceIsNegative(final BigDecimal price) {
        // given
        final String name = "새로운 메뉴";
        final Long menuGroupId = 1L;
        final List<ProductQuantityDto> productQuantityDtos = Arrays.asList(
                new ProductQuantityDto(1L, 1L),
                new ProductQuantityDto(2L, 2L)
        );
        MenuCreationRequestDto menuCreationRequestDto = new MenuCreationRequestDto(name, price, menuGroupId, productQuantityDtos);

        given(products.getProductPricesByProductIds(any())).willReturn(Arrays.asList(
                new ProductPriceDto(1L, BigDecimal.valueOf(1000L)),
                new ProductPriceDto(2L, BigDecimal.valueOf(2000L))
        ));

        // when
        // then
        assertThatThrownBy(() -> {
            menuService.create(menuCreationRequestDto);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidPrice() {
        return Stream.of(
                BigDecimal.valueOf(-1),
                BigDecimal.valueOf(-1000),
                BigDecimal.valueOf(-1000000000)
        );
    }

    @DisplayName("메뉴 생성 시, 제품을 1개 이상 지정해야한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource(value = "provideInvalidProductQuantityDto")
    void createFailsWhenNumberOfProductIsZero(List<ProductQuantityDto> productQuantityDtos) {
        // given
        final String name = "새로운 메뉴";
        final BigDecimal price = BigDecimal.valueOf(1000);
        final Long menuGroupId = 1L;
        MenuCreationRequestDto menuCreationRequestDto = new MenuCreationRequestDto(name, price, menuGroupId, productQuantityDtos);

        // when
        // then
        assertThatThrownBy(() -> {
            menuService.create(menuCreationRequestDto);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream provideInvalidProductQuantityDto() {
        return Stream.of(
                Arrays.asList()
        );
    }

    @DisplayName("메뉴 생성 시, 메뉴가격은 구성된 메뉴 제품들의 가격 총합을 초과할 수 없습니다.")
    @Test
    void createFailsWhenPriceIsOverTotalPriceOfProducts() {
        // given
        final String name = "새로운 메뉴";
        final BigDecimal price = BigDecimal.valueOf(5001);
        final Long menuGroupId = 1L;
        final List<ProductQuantityDto> productQuantityDtos = Arrays.asList(
                new ProductQuantityDto(1L, 1L),
                new ProductQuantityDto(2L, 2L)
        );
        MenuCreationRequestDto menuCreationRequestDto = new MenuCreationRequestDto(name, price, menuGroupId, productQuantityDtos);

        given(products.getProductPricesByProductIds(any())).willReturn(Arrays.asList(
                new ProductPriceDto(1L, BigDecimal.valueOf(1000L)),
                new ProductPriceDto(2L, BigDecimal.valueOf(2000L))
        ));

        // when
        // then
        assertThatThrownBy(() -> {
            menuService.create(menuCreationRequestDto);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}

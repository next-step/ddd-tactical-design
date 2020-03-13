package kitchenpos.menus.tobe.v2.application;

import kitchenpos.menus.tobe.v2.Fixtures;
import kitchenpos.menus.tobe.v2.domain.*;
import kitchenpos.menus.tobe.v2.dto.MenuProductRequestDto;
import kitchenpos.menus.tobe.v2.dto.MenuRequestDto;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {
    private static final Long PRODUCT_ID_ONE = 1L;
    private static final Long MENU_GROUP_ID_ONE = 1L;

    @InjectMocks
    private MenuService menuService;

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuProductRepository menuProductRepository;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("메뉴 리스트 조회")
    void list() {
        Menu menu = Fixtures.friedChikenMenu();

        List<Menu> expected = Arrays.asList(menu);

        given(menuRepository.findMenuAll())
                .willReturn(expected);

        List<Menu> actual = menuService.list();

        assertAll(
                () -> assertThat(actual.size()).isEqualTo(expected.size()),
                () -> assertThat(actual).containsExactlyInAnyOrderElementsOf(expected)
        );
    }

    @Test
    @DisplayName("메뉴 생성 정상 동작")
    void create() {
        MenuRequestDto menuRequestDto = generateMenuRequestDto();

        menuRequestDto.getMenuProducts()
                .forEach(menuProduct -> {
                    Product product = new Product("후라이드", BigDecimal.TEN);
                    given(productRepository.findById(menuProduct.getProductId())).willReturn(Optional.of(product));
                });

        MenuGroup friedMenuGroup = Fixtures.friedMenuGroup();

        given(menuGroupRepository.findById(menuRequestDto.getMenuGroupId())).willReturn(Optional.of(friedMenuGroup));
        given(menuRepository.save(any(Menu.class))).willReturn(menuRequestDto.toEntity());

        Menu savedMenu = menuService.create(menuRequestDto);

        assertAll(
                () -> assertThat(savedMenu.getPrice()).isEqualTo(menuRequestDto.getPrice()),
                () -> assertThat(savedMenu.getName()).isEqualTo(menuRequestDto.getName()),
                () -> assertThat(savedMenu.getMenuProducts().size()).isEqualTo(menuRequestDto.getMenuProducts().size())
        );
    }

    @DisplayName("메뉴 가격이 올바르지 않을때 생성 실패")
    @ParameterizedTest
    @ValueSource(strings = "-10")
    @NullSource
    void create_fail_by_price(BigDecimal price) {
        MenuRequestDto menuRequestDto = generateMenuRequestDto();
        menuRequestDto.setPrice(price);

        assertThrows(IllegalArgumentException.class, () -> menuService.create(menuRequestDto));
    }

    @DisplayName("메뉴 그룹 정보가 없을때 메뉴 생성 실패")
    @ParameterizedTest
    @NullSource
    @ValueSource(longs = 9999)
    void createFailByNotExsistMenugroup(Long groupId) {
        MenuRequestDto menuRequestDto = generateMenuRequestDto();
        menuRequestDto.setMenuGroupId(groupId);

        assertThrows(IllegalArgumentException.class, () -> menuService.create(menuRequestDto));
    }

    @DisplayName("등록되지 않은 상품을 포함 시 생성 실패")
    @ParameterizedTest
    @ValueSource(longs = {999, 888})
    void createFailByNotExistProduct(Long productId) {
        MenuRequestDto menuRequestDto = generateMenuRequestDto();
        List<MenuProductRequestDto> menuProducts = menuRequestDto.getMenuProducts();
        menuProducts.stream()
                .forEach(menuProductRequestDto -> menuProductRequestDto.setProductId(productId));

        MenuGroup friedMenuGroup = Fixtures.friedMenuGroup();

        given(menuGroupRepository.findById(menuRequestDto.getMenuGroupId())).willReturn(Optional.of(friedMenuGroup));

        assertThrows(IllegalArgumentException.class, () -> menuService.create(menuRequestDto));
    }

    @DisplayName("메뉴 가격이 상품의 가격 합보다 클때 생성 실패")
    @ParameterizedTest
    @ValueSource(strings = "100")
    void createFailByPriceGreaterThanProductPrice(BigDecimal price) {
        MenuRequestDto menuRequestDto = generateMenuRequestDto();
        menuRequestDto.setPrice(price);

        menuRequestDto.getMenuProducts()
                .forEach(menuProduct -> {
                    Product product = new Product("후라이드", BigDecimal.TEN);
                    given(productRepository.findById(menuProduct.getProductId())).willReturn(Optional.of(product));
                });

        MenuGroup friedMenuGroup = Fixtures.friedMenuGroup();

        given(menuGroupRepository.findById(menuRequestDto.getMenuGroupId())).willReturn(Optional.of(friedMenuGroup));

        assertThrows(IllegalArgumentException.class, () -> menuService.create(menuRequestDto));
    }

    private MenuRequestDto generateMenuRequestDto() {
        String name = "후라이드";
        BigDecimal price = BigDecimal.TEN;

        MenuProductRequestDto menuProductRequestDto = new MenuProductRequestDto();
        menuProductRequestDto.setProductId(PRODUCT_ID_ONE);
        menuProductRequestDto.setQuantity(3L);

        MenuRequestDto menuRequestDto = new MenuRequestDto();
        menuRequestDto.setMenuGroupId(MENU_GROUP_ID_ONE);
        menuRequestDto.setMenuProducts(Arrays.asList(menuProductRequestDto));
        menuRequestDto.setName(name);
        menuRequestDto.setPrice(price);

        return menuRequestDto;
    }
}
package kitchenpos.menus.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.application.dto.ChangeMenuPriceRequest;
import kitchenpos.menus.application.dto.CreateMenuRequest;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuPricePolicy;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.PurgomalumClient;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.service.ProductFixture;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@Transactional
public class MenuServiceTest {

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MenuRepository menuRepository;

    @MockBean
    private PurgomalumClient purgomalumClient;

    @SpyBean
    private MenuPricePolicy menuPricePolicy;

    @Autowired
    private MenuService menuService;

    @Autowired
    private ProductService productService;

    private MenuGroup 추천메뉴;
    private Product 강정치킨;
    private Product 양념치킨;
    private Menu 오늘의치킨;

    @BeforeEach
    void init() {
        추천메뉴 = MenuGroupFixture.builder().build();
        menuGroupRepository.save(추천메뉴);

        강정치킨 = ProductFixture.Data.강정치킨();
        productRepository.save(강정치킨);

        양념치킨 = ProductFixture.Data.양념치킨();
        productRepository.save(양념치킨);

        오늘의치킨 = MenuFixture.builder(추천메뉴)
                .menuProducts(List.of(
                        MenuProductFixture.builder(강정치킨).build())
                )
                .name("오늘의 치킨").build();
        menuRepository.save(오늘의치킨);
    }

    @Test
    void 메뉴_생성_실패__가격이_null() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴)
                .price(null)
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__가격이_음수() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴)
                .price(-1L)
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__메뉴그룹이_존재하지_않음() {
        CreateMenuRequest request = MenuRequestFixture.builder()
                .menuGroupId(MenuGroupFixture.builder()
                        .name("존재하지 않는 메뉴그룹")
                        .build().getId())
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 메뉴_생성_실패__메뉴상품이_null() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴)
                .menuProducts(null)
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__메뉴상품이_0개() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴)
                .menuProducts(List.of())
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__메뉴_생성_요청의_메뉴상품이_존재하지_않음() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴)
                .menuProducts(
                        List.of(MenuProductDtoFixture.builder().build())
                )
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__메뉴상품의_갯수가_음수() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴)
                .menuProducts(
                        List.of(
                                MenuProductDtoFixture.builder(강정치킨)
                                        .quantity(-1)
                                        .build()
                        )
                )
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__구성메뉴상품의_가격_총합이_메뉴_가격_보다_초과일_수_없다() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴)
                .price(52001L)
                .menuProducts(
                        List.of(
                                MenuProductDtoFixture.builder(강정치킨)
                                        .build(),
                                MenuProductDtoFixture.builder(양념치킨)
                                        .quantity(2)
                                        .build()
                        )
                )
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__이름이_null() {
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴, 강정치킨)
                .name(null)
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_생성_실패__이름에_욕설_포함() {
        when(purgomalumClient.containsProfanity("abuse name")).thenReturn(true);
        CreateMenuRequest request = MenuRequestFixture.builder(추천메뉴, 강정치킨)
                .name("abuse name")
                .buildCreateRequest();

        assertThatThrownBy(() -> menuService.create(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_가격_변경_실패__가격이_null() {
        UUID menuId = 오늘의치킨.getId();
        ChangeMenuPriceRequest request = MenuRequestFixture.builder()
                .price(null)
                .buildChangePriceRequest();

        assertThatThrownBy(() -> menuService.changePrice(menuId, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_가격_변경_실패__가격이_음수() {
        UUID menuId = 오늘의치킨.getId();
        ChangeMenuPriceRequest request = MenuRequestFixture.builder()
                .price(-1L)
                .buildChangePriceRequest();

        assertThatThrownBy(() -> menuService.changePrice(menuId, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_가격_변경_실패__메뉴가_존재하지_않음() {
        UUID menuId = UUID.randomUUID();
        ChangeMenuPriceRequest request = MenuRequestFixture.builder()
                .price(20000L)
                .buildChangePriceRequest();

        assertThatThrownBy(() -> menuService.changePrice(menuId, request))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 메뉴_가격_변경_실패__메뉴_가격은_속한_메뉴상품_가격의_총합보다_클_수_없음() {
        UUID menuId = 오늘의치킨.getId();
        ChangeMenuPriceRequest request = MenuRequestFixture.builder()
                .price(20000L)
                .buildChangePriceRequest();

        assertThatThrownBy(() -> menuService.changePrice(menuId, request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 메뉴_보임_설정_실패__메뉴가_존재하지_않음() {
        UUID menuId = UUID.randomUUID();

        assertThatThrownBy(() -> menuService.display(menuId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 메뉴_보임_설정_실패__메뉴_가격은_속한_메뉴상품_가격의_총합보다_클_수_없음() {
        UUID menuId = 오늘의치킨.getId();
        doThrow(new IllegalStateException()).when(menuPricePolicy).follow(any(), any());

        assertThatThrownBy(() -> menuService.display(menuId))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 메뉴_숨김_설정_실패__메뉴가_존재하지_않음() {
        UUID menuId = UUID.randomUUID();

        assertThatThrownBy(() -> menuService.hide(menuId))
                .isInstanceOf(NoSuchElementException.class);
    }
}

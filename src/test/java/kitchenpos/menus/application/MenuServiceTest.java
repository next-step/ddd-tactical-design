package kitchenpos.menus.application;

import static java.math.BigDecimal.valueOf;
import static kitchenpos.menus.MenuGroupFixture.menuGroup;
import static kitchenpos.products.ProductFixture.product;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.MenuFixture;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuGroupRepository;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.ProductFixture;
import kitchenpos.products.application.FakePurgomalumClient;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.application.ProductService;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MenuServiceTest {

    private ProductService productService;

    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private PurgomalumClient purgomalumClient;
    private MenuService menuService;
    private UUID menuGroupId;
    private Product friedChicken;
    private Product garlicChicken;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        purgomalumClient = new FakePurgomalumClient();
        productService = new ProductService(productRepository, menuRepository, purgomalumClient);
        menuService = new MenuService(menuRepository, menuGroupRepository, productRepository, purgomalumClient);
        menuGroupId = menuGroupRepository.save(menuGroup()).getId();
        friedChicken = productRepository.save(product("후라이드", 16_000L));
        garlicChicken = productRepository.save(product("갈릭치킨", 17_000L));
    }

    @DisplayName("메뉴의 가격은 0원 이상이여야 한다")
    @ParameterizedTest
    @ValueSource(longs = {-1, -2, -3})
    void test1(long price) {
        //given
        Menu request = new Menu();
        request.setPrice(valueOf(price));

        //when && then
        assertThatThrownBy(
            () -> menuService.create(request)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴를 등록할수 있다")
    @Test
    void test2() {
        //given
        Menu menuRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 1L)
            .menuProduct(garlicChicken, 2L)
            .name("치킨세트메뉴2+1")
            .build();

        //when
        Menu result = menuService.create(menuRequest);

        //then
        assertThat(result.getId()).isNotNull();
    }

    @DisplayName("메뉴는 반드시 메뉴그룹이 존재해야 한다")
    @Test
    void test3() {
        //given
        UUID notSavedMenuGroupUuid = UUID.randomUUID();
        Menu menuRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(notSavedMenuGroupUuid)
            .menuProduct(friedChicken, 1L)
            .menuProduct(garlicChicken, 2L)
            .name("치킨세트메뉴2+1")
            .build();

        //when && then
        assertThatThrownBy(
            () -> menuService.create(menuRequest)
        ).isInstanceOf(NoSuchElementException.class);
    }

    @DisplayName("없는 상품은 메뉴에 추가할수 없다")
    @Test
    void test4() {
        //given
        Product invalidProduct = product("INVALID_PRODUCT", 50_000);
        Menu menuRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 1L)
            .menuProduct(garlicChicken, 2L)
            .menuProduct(invalidProduct, 1L)
            .name("이상한메뉴")
            .build();

        //when && then
        assertThatThrownBy(
            () -> menuService.create(menuRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴는 최소 하나 이상의 상품을 가지고 있어야 한다")
    @Test
    void test5() {
        //given
        Menu menuRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .name("menuName")
            .build();

        //when && then
        assertThatThrownBy(
            () -> menuService.create(menuRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴에 포함된 상품은 각각 0개 이상 등록해야 한다")
    @Test
    void test6() {
        //given
        Menu menuRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, -1L)
            .name("menuName")
            .build();

        //when && then
        assertThatThrownBy(
            () -> menuService.create(menuRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴의 가격은 포함된 상품들의 총가격(단가 * 수량)보다 클수 없다")
    @Test
    void test7() {
        //given
        Menu menuRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 1L)
            .menuProduct(garlicChicken, 1L)
            .name("간장후라이드세트")
            .addPrice(1L)
            .build();

        //when && then
        assertThatThrownBy(
            () -> menuService.create(menuRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 가격을 변경할수 있다")
    @Test
    void test8() {
        //given 0번 상품을 2개로 묶은 메뉴를 생성한다.
        Menu createRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 2L)
            .name("menuMame")
            .build();
        Menu savedMenu = menuService.create(createRequest);
        //given 0번 상품을 반값 행사를 할수 있게 Menu의 가격을 절반으로 변경한다.
        Menu updateRequest = MenuFixture.updateRequestBuilder()
            .price(savedMenu.getPrice().longValue() / 2)
            .build();

        //when
        Menu result = menuService.changePrice(savedMenu.getId(), updateRequest);

        //then
        assertThat(result.getPrice()).isEqualTo(updateRequest.getPrice());
    }

    @DisplayName("변경된 메뉴 가격 또한 포함된 상품들의 총가격(단가 * 수량)보다 클수 없다")
    @Test
    void test9() {
        //given 0번 상품을 2개로 묶은 메뉴를 생성한다.
        Menu createRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 2L)
            .name("menuMame")
            .build();
        Menu savedMenu = menuService.create(createRequest);
        //given 0번 상품을 반값 행사를 하려고 했으나 실수로 2배 가격을 설정해버렸다.
        Menu updateRequest = MenuFixture.updateRequestBuilder()
            .price(savedMenu.getPrice().longValue() * 2)
            .build();

        //when && then
        assertThatThrownBy(
            () -> menuService.changePrice(savedMenu.getId(), updateRequest)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("메뉴 가격이, 포함된 상품들의 총 가격(단가 * 수량)보다 적은 메뉴는 노출시킬수 있다")
    @Test
    void test10() {
        //given 0번 상품을 2개로 묶은 메뉴를 생성한다.
        Menu createRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 2L)
            .name("후라이드세트")
            .build();
        Menu savedMenu = menuService.create(createRequest);

        //when
        Menu result = menuService.display(savedMenu.getId());

        //then
        assertThat(result.isDisplayed()).isTrue();
    }

    @DisplayName("메뉴를 숨길수 있다")
    @Test
    void test11() {
        //given 0번 상품을 2개로 묶은 메뉴를 생성한다.
        Menu createRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 2L)
            .name("후라이드세트")
            .build();
        Menu savedMenu = menuService.create(createRequest);

        //when
        Menu result = menuService.hide(savedMenu.getId());

        //then
        assertThat(result.isDisplayed()).isFalse();
    }

    @DisplayName("메뉴의 모든 정보를 조회할수 있다")
    @Test
    void test12() {
        //given
        //given 0번 상품을 2개로 묶은 메뉴를 생성한다.
        Menu createRequest1 = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 1L)
            .name("후라이드")
            .build();
        Menu createRequest2 = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(garlicChicken, 1L)
            .name("갈릭")
            .build();
        Menu savedMenu1 = menuService.create(createRequest1);
        Menu savedMenu2 = menuService.create(createRequest2);

        //when
        List<Menu> menus = menuService.findAll();

        //then
        assertThat(menus).extracting("id")
            .containsExactlyInAnyOrder(savedMenu1.getId(), savedMenu2.getId());
    }

    @DisplayName("상품 가격을 수정한 상품이 포함된 메뉴의 가격이, 메뉴에 포함된 상품들의 총가격(단가 * 수량)보다 크다면 해당 메뉴를 노출시키지 않는다")
    @Test
    void test13() {
        //given
        Menu createRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 1L)
            .name("menu1")
            .build();
        menuService.create(createRequest);

        //when
        //Product 0번의 가격을 10배 줄인다.
        productService.changePrice(friedChicken.getId(),
            ProductFixture.updateRequestBuilder()
                .name(friedChicken.getName())
                .price(friedChicken.getPrice().divide(BigDecimal.TEN))
                .build());
        Menu menu = menuService.findAll().get(0);

        //then 10개 할인된 Product 0번의 가격보다 Menu의 가격이 크기때문에 Display는 false이다.
        assertThat(menu.isDisplayed()).isFalse();

    }

    @DisplayName("원가보다 1000원 적은 가격으로 메뉴의 가격을 변경할수 있다 <에러 발생! 버그 픽스 필요>")
    @Test
    void test14() {
        //given 0번 상품을 2개로 묶은 메뉴를 생성한다.
        Menu createRequest = MenuFixture.createRequestBuilder()
            .menuGroupId(menuGroupId)
            .menuProduct(friedChicken, 1L)
            .menuProduct(garlicChicken, 1L)
            .name("menuMame")
            .build();
        Menu savedMenu = menuService.create(createRequest);
        BigDecimal updatePrice = friedChicken.getPrice().add(garlicChicken.getPrice()).add(valueOf(-1000));
        Menu updateRequest = MenuFixture.updateRequestBuilder()
            .price(updatePrice.longValue())
            .build();

        //when
        Menu result = menuService.changePrice(savedMenu.getId(), updateRequest);

        //then
        assertThat(result.getPrice()).isEqualTo(updateRequest.getPrice());
    }
}

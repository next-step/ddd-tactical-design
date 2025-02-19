package kitchenpos.menu.application.service;

import kitchenpos.menu.adapter.out.persistance.MenuEntityRepository;
import kitchenpos.menu.adapter.out.persistance.MenuGroupEntityRepository;
import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;
import kitchenpos.menu.adapter.out.persistance.entity.MenuProductEntity;
import kitchenpos.menu.application.service.model.ChangeMenuPriceRequest;
import kitchenpos.menu.application.service.model.CreateMenuRequest;
import kitchenpos.menu.domain.exception.MenuPriceValidationException;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.shared.domain.Profanities;
import kitchenpos.product.adapter.out.persistance.ProductEntityRepository;
import kitchenpos.product.adapter.out.persistance.entity.ProductEntity;
import kitchenpos.product.domain.model.Product;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class MenuServiceTest {
    private final MenuService menuService;
    private final ProductEntityRepository productEntityRepository;
    private final MenuGroupEntityRepository menuGroupEntityRepository;
    private final MenuEntityRepository menuEntityRepository;
    private final Profanities profanities;

    public MenuServiceTest(MenuService menuService, ProductEntityRepository productEntityRepository, MenuGroupEntityRepository menuGroupEntityRepository, MenuEntityRepository menuEntityRepository, Profanities profanities) {
        this.menuService = menuService;
        this.productEntityRepository = productEntityRepository;
        this.menuGroupEntityRepository = menuGroupEntityRepository;
        this.menuEntityRepository = menuEntityRepository;
        this.profanities = Mockito.mock(Profanities.class);
    }

    @DisplayName("메뉴 등록하기")
    @Nested
    class MenuRegisterTest {

        private static final UUID 후라이드치킨_PRODUCT_UUID = UUID.randomUUID();
        private static final UUID 치킨류_MENU_GROUP_UUID = UUID.randomUUID();
        private static final String 후라이드치킨_PRODUCT_NAME = "후라이드치킨";
        private static final String 치킨류_MENU_GROUP_NAME = "치킨류";
        private static final BigDecimal 후라이드치킨_DEFAULT_PRICE = new BigDecimal(20000);

        @BeforeEach
        void setup() {
            ProductEntity product = createProduct(후라이드치킨_PRODUCT_UUID, 후라이드치킨_PRODUCT_NAME, 후라이드치킨_DEFAULT_PRICE);
            productEntityRepository.save(product);

            MenuGroupEntity menuGroup = createMenuGroup(치킨류_MENU_GROUP_UUID, 치킨류_MENU_GROUP_NAME);
            menuGroupEntityRepository.save(menuGroup);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴를 정상적으로 등록한다")
        @Test
        void create_menu_successfully() {
            // given
            List<MenuProduct> menuProducts = List.of(createMenuProductRequest(후라이드치킨_PRODUCT_UUID, 2));
            CreateMenuRequest request = createMenuRequest("후라이드치킨", 16000, 치킨류_MENU_GROUP_UUID, menuProducts);
            request.setDisplayed(true);

            // when
            Menu menu = menuService.create(request);

            // then
            assertAll(
                    () -> assertThat(menu.getId()).isNotNull(),
                    () -> assertThat(menu.getName()).isEqualTo(request.getName()),
                    () -> assertThat(menu.getPrice()).isEqualTo(request.getPrice()),
                    () -> assertThat(menu.isDisplayed()).isEqualTo(request.isDisplayed()),
                    () -> assertThat(menu.getMenuGroup().getId()).isEqualTo(request.getMenuGroupId()),
                    () -> assertThat(menu.getMenuProducts()).hasSize(menuProducts.size())
            );
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("이름에는 비속어와 같이 부적절한 단어는 사용할 수 없다")
        @Test
        void registration_menu_with_profanity() {
            // given
            String menuName = "holy shit 후라이드치킨";
            MenuProduct menuProduct = createMenuProductRequest(후라이드치킨_PRODUCT_UUID, 2);
            CreateMenuRequest request = createMenuRequest(menuName, 16000, 치킨류_MENU_GROUP_UUID, List.of(menuProduct));
            Mockito.when(profanities.contains(menuName)).thenReturn(Boolean.TRUE);

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.create(request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("이름은 반드시 입력해야 합니다")
        @Test
        void name_must_be_input() {
            // given
            MenuProduct menuProduct = createMenuProductRequest(후라이드치킨_PRODUCT_UUID, 2);
            CreateMenuRequest request = createMenuRequest(null, 16000, 치킨류_MENU_GROUP_UUID, List.of(menuProduct));

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.create(request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴는 적어도 하나 이상의 상품을 포함해야 한다")
        @Test
        void create_menu_without_products() {
            // given
            CreateMenuRequest request = createMenuRequest("후라이드치킨", 16000, 치킨류_MENU_GROUP_UUID, Collections.emptyList());

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.create(request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("상품의 갯수는 0개 이상 입력해야 한다")
        @Test
        void product_quantity_must_be_positive() {
            // given
            MenuProduct menuProduct = createMenuProductRequest(후라이드치킨_PRODUCT_UUID, -1);
            CreateMenuRequest request = createMenuRequest("후라이드치킨", 16000, 치킨류_MENU_GROUP_UUID, List.of(menuProduct));

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.create(request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴 가격은 0 이상이어야 한다")
        @Test
        void create_menu_with_invalid_price() {
            // given
            MenuProduct menuProduct = createMenuProductRequest(후라이드치킨_PRODUCT_UUID, 1);
            CreateMenuRequest request = createMenuRequest("후라이드치킨", -100, 치킨류_MENU_GROUP_UUID, List.of(menuProduct));

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.create(request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴 가격은 상품 가격 총합을 초과할 수 없다")
        @Test
        void create_menu_with_price_exceeding_product_total() {
            // given
            MenuProduct menuProduct = createMenuProductRequest(후라이드치킨_PRODUCT_UUID, 1);
            CreateMenuRequest request = createMenuRequest("후라이드치킨", 50000, 치킨류_MENU_GROUP_UUID, List.of(menuProduct));

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.create(request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴는 하나의 메뉴그룹에 속한다")
        @Test
        void create_menu_without_menu_group() {
            // given
            MenuProduct menuProduct = createMenuProductRequest(후라이드치킨_PRODUCT_UUID, 1);
            CreateMenuRequest request = createMenuRequest("후라이드치킨", 16000, UUID.randomUUID(), List.of(menuProduct));

            // when
            Executable executable = () -> menuService.create(request);

            // then
            assertThrows(NoSuchElementException.class, executable);
        }
    }

    @DisplayName("메뉴 가격 변경하기")
    @Nested
    class ChangeMenuPriceTest {

        private static final UUID MENU_UUID = UUID.randomUUID();
        private static final UUID PRODUCT_UUID = UUID.randomUUID();
        private static final UUID MENU_GROUP_UUID = UUID.randomUUID();

        @BeforeEach
        void setup() {
            ProductEntity product = createProduct(PRODUCT_UUID, "양념치킨", new BigDecimal(20000));
            productEntityRepository.save(product);

            MenuGroupEntity menuGroup = createMenuGroup(MENU_GROUP_UUID, "치킨류");
            menuGroupEntityRepository.save(menuGroup);

            List<MenuProductEntity> menuProducts = List.of(createMenuProduct(PRODUCT_UUID, product, 1));
            MenuEntity menu = createMenu(MENU_UUID, "양념치킨", new BigDecimal(20000), MENU_GROUP_UUID, menuGroup, menuProducts);
            menuEntityRepository.save(menu);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴 가격을 변경한다")
        @Test
        void change_menu_price() {
            // given
            ChangeMenuPriceRequest request = new ChangeMenuPriceRequest();
            request.setPrice(new BigDecimal(18000));

            // when
            Menu updatedMenu = menuService.changePrice(MENU_UUID, request);

            // then
            assertThat(updatedMenu.getPrice()).isEqualTo(request.getPrice());
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("가격은 0원 이상이어야 한다")
        @Test
        void price_must_be_positive() {
            // given
            ChangeMenuPriceRequest request = new ChangeMenuPriceRequest();
            request.setPrice(new BigDecimal(-1000));

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.changePrice(MENU_UUID, request);

            // then
            assertThatThrownBy(throwingCallable)
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴의 가격은 등록된 상품의 총합보다 높을 수 없다")
        @Test
        void price_must_be_lower_than_sum_of_products() {
            // given
            ChangeMenuPriceRequest request = new ChangeMenuPriceRequest();
            request.setPrice(new BigDecimal(30000));

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.changePrice(MENU_UUID, request);

            // then
            assertThatIllegalArgumentException()
                    .isThrownBy(throwingCallable);
        }
    }

    @DisplayName("메뉴 전시 및 내리기")
    @Nested
    class DisplayAndHideMenuTest {

        private static final UUID MENU_UUID = UUID.randomUUID();
        private static final UUID PRODUCT_UUID = UUID.randomUUID();
        private static final UUID MENU_GROUP_UUID = UUID.randomUUID();

        @BeforeEach
        void setup() {
            ProductEntity product = createProduct(PRODUCT_UUID, "간장치킨", new BigDecimal(19000));
            productEntityRepository.save(product);

            MenuGroupEntity menuGroup = createMenuGroup(MENU_GROUP_UUID, "치킨류");
            menuGroupEntityRepository.save(menuGroup);

            List<MenuProductEntity> menuProducts = List.of(createMenuProduct(PRODUCT_UUID, product, 1));
            MenuEntity menu = createMenu(MENU_UUID, "간장치킨", new BigDecimal(19000), MENU_GROUP_UUID, menuGroup, menuProducts);
            menuEntityRepository.save(menu);
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴를 전시한다")
        @Test
        void display_menu() {
            // when
            Menu displayedMenu = menuService.display(MENU_UUID);

            // then
            assertThat(displayedMenu.isDisplayed()).isTrue();
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("전시될 메뉴의 가격은 등록된 상품의 총합보다 높을 수 없다")
        @Test
        void price_must_be_lower_than_sum_of_products() {
            // given
            MenuEntity menu = menuEntityRepository.findById(MENU_UUID)
                    .orElseThrow(NoSuchElementException::new);
            menu.setPrice(new BigDecimal(60000));
            menuEntityRepository.save(menu);

            // when
            ThrowableAssert.ThrowingCallable throwingCallable = () -> menuService.display(MENU_UUID);

            // then
            assertThatThrownBy(throwingCallable)
                    .isInstanceOf(MenuPriceValidationException.class)
                    .hasMessage("메뉴 가격은 메뉴 상품 가격의 총합보다 작거나 같아야 합니다.");
        }

        @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        @DisplayName("메뉴를 내린다")
        @Test
        void hide_menu() {
            // when
            Menu hiddenMenu = menuService.hide(MENU_UUID);

            // then
            assertThat(hiddenMenu.isDisplayed()).isFalse();
        }
    }

    @DisplayName("메뉴 조회하기")
    @Nested
    class FindAllMenusTest {
        private static final int TOTAL_MENU_COUNT = 6;

        @SqlGroup({
                @Sql(value = "/setup.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
                @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
        })
        @DisplayName("모든 메뉴를 조회한다")
        @Test
        void find_all_menus() {
            // when
            List<Menu> menus = menuService.findAll();

            // then
            assertThat(menus).hasSize(TOTAL_MENU_COUNT);
        }
    }

    private static MenuEntity createMenu(String name, int price, UUID menuGroupId, List<MenuProductEntity> menuProducts) {
        return createMenu(null, name, new BigDecimal(price), menuGroupId, null, menuProducts);
    }

    private static CreateMenuRequest createMenuRequest(String name, int price, UUID menuGroupId, List<MenuProduct> menuProducts) {
        return createMenuRequest(null, name, new BigDecimal(price), menuGroupId, null, menuProducts);
    }

    private static MenuEntity createMenu(UUID id, String name, BigDecimal price, UUID menuGroupId, MenuGroupEntity menuGroup, List<MenuProductEntity> menuProducts) {
        MenuEntity menu = new MenuEntity();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroupId(menuGroupId);
        menu.setMenuGroup(menuGroup);
        menu.setMenuProducts(menuProducts);
        return menu;
    }

    private static CreateMenuRequest createMenuRequest(UUID id, String name, BigDecimal price, UUID menuGroupId, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        CreateMenuRequest menu = new CreateMenuRequest();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroupId(menuGroupId);
        menu.setMenuGroup(menuGroup);
        menu.setMenuProducts(menuProducts);
        return menu;
    }

    private static MenuProductEntity createMenuProduct(UUID productId, int quantity) {
        return createMenuProduct(productId, null, quantity);
    }

    private static MenuProduct createMenuProductRequest(UUID productId, int quantity) {
        return createMenuProductRequest(productId, null, quantity);
    }

    private static MenuProductEntity createMenuProduct(UUID productId, ProductEntity product, int quantity) {
        MenuProductEntity menuProduct = new MenuProductEntity();
        menuProduct.setProductId(productId);
        if (product != null) {
            menuProduct.setProduct(product);
        }
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }

    private static MenuProduct createMenuProductRequest(UUID productId, Product product, int quantity) {
        MenuProduct menuProduct = new MenuProduct();
        menuProduct.setProductId(productId);
        if (product != null) {
            menuProduct.setProduct(product);
        }
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }

    private static ProductEntity createProduct(UUID id, String name, BigDecimal price) {
        return new ProductEntity(id, name, price);
    }

    private static MenuGroupEntity createMenuGroup(UUID id, String name) {
        MenuGroupEntity menuGroup = new MenuGroupEntity();
        menuGroup.setId(id);
        menuGroup.setName(name);
        return menuGroup;
    }
}

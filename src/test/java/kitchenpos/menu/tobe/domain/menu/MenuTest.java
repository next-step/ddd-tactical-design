package kitchenpos.menu.tobe.domain.menu;

import kitchenpos.common.exception.MenuException;
import kitchenpos.common.tobe.Profanities;
import kitchenpos.product.tobe.domain.Product;
import kitchenpos.product.tobe.domain.ProductRepository;
import kitchenpos.product.tobe.fake.InMemoryProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuTest {

    private ProductRepository productRepository;
    private MenuValidator menuValidator;
    private final Profanities profanities = text -> text.equals("바보"); // 테스트용 비속어 설정

    private final UUID productId = UUID.randomUUID();
    private final UUID productId2 = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuValidator = new MenuPriceValidator(productRepository);
        // 상품 생성 및 저장
        Product product1 = new Product( "상품1", 5000L, profanities);
        ReflectionTestUtils.setField(product1, "id", productId);
        Product product2 = new Product("상품2", 8000L, profanities);
        ReflectionTestUtils.setField(product2, "id", productId2);

        productRepository.save(product1);
        productRepository.save(product2);
    }

    @Test
    @DisplayName("메뉴를 생성할 수 있다")
    void createMenu() {
        // given
        String menuName = "맛있는 메뉴";
        Long menuPrice = 10000L;
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, productId),
                new MenuProduct(1L, 1, 5000L, productId2));

        boolean displayed = true;

        // when
        Menu menu = Menu.of(menuName, menuPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator);

        // then
        assertThat(menu).isNotNull();
        assertThat(menu.getName()).isEqualTo(menuName);
        assertThat(menu.getMenuPrice()).isEqualTo(menuPrice);
        assertThat(menu.isDisplayed()).isEqualTo(displayed);
        assertThat(menu.getMenuProducts()).hasSize(2);
    }
    @Test
    @DisplayName("메뉴 가격이 상품 가격의 합보다 작으면 예외가 발생한다")
    void createMenuWithInvalidPrice() {
        // given
        String menuName = "메뉴";
        // 상품 가격 합계: 5000 * 2 + 8000 * 1 = 18000
        Long menuPrice = 17000L;  // 18000보다 작은 가격
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, productId),
                new MenuProduct(1L, 1, 5000L, productId2));

        boolean displayed = true;

        // when & then
        assertThatThrownBy(() -> Menu.of(menuName, menuPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator))
                .isInstanceOf(MenuException.class);
    }
    @Test
    @DisplayName("비속어가 포함된 메뉴 이름으로는 메뉴를 생성할 수 없다")
    void createMenuWithProfanity() {
        // given
        String menuName = "바보";  // 비속어 포함
        Long menuPrice = 10000L;
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, productId),
                new MenuProduct(1L, 1, 5000L, productId2));

        boolean displayed = true;

        // when & then
        assertThatThrownBy(() -> Menu.of(menuName, menuPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("비속어가 포함되어 있습니다.");
    }

    @Test
    @DisplayName("메뉴 가격을 변경할 수 있다")
    void changeMenuPrice() {
        // given
        String menuName = "메뉴";
        Long initialPrice = 1000L;
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, productId),
                new MenuProduct(1L, 1, 5000L, productId2));
        boolean displayed = true;

        Menu menu = Menu.of(menuName, initialPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator);

        // when
        Long newPrice = 8000L;
        menu.changeMenuPrice(newPrice, menuValidator);

        // then
        assertThat(menu.getMenuPrice()).isEqualTo(newPrice);
    }

    @Test
    @DisplayName("메뉴 가격을 상품 가격의 합보다 작게 변경하면 예외가 발생한다")
    void changeMenuPriceWithInvalidPrice() {
        // given
        String menuName = "메뉴";
        Long initialPrice = 1000L;
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, productId),
                new MenuProduct(2L, 1, 8000L, productId2));

        boolean displayed = true;

        Menu menu = Menu.of(menuName, initialPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator);

        // when & then
        Long invalidPrice = 16000L;
        System.out.println("here");
        assertThatThrownBy(() -> menu.changeMenuPrice(invalidPrice, menuValidator))
                .isInstanceOf(MenuException.class);
    }
    @Test
    @DisplayName("메뉴를 display 상태로 변경할 수 있다")
    void showMenu() {
        // given
        String menuName = "메뉴";
        Long menuPrice = 10000L;
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, productId),
                new MenuProduct(2L, 1, 8000L, productId2));
        boolean displayed = false;
        Menu menu = Menu.of(menuName, menuPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator);

        // when
        menu.show(menuValidator);

        // then
        assertThat(menu.isDisplayed()).isTrue();
    }
    @Test
    @DisplayName("메뉴를 hide 상태로 변경할 수 있다")
    void hideMenu() {
        // given
        String menuName = "메뉴";
        Long menuPrice = 15000L;
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(1L, 1, 5000L, productId),
                new MenuProduct(2L, 1, 8000L, productId2));
        boolean displayed = true;
        Menu menu = Menu.of(menuName, menuPrice, UUID.randomUUID(), menuProducts, displayed, profanities, menuValidator);

        // when
        menu.hide();

        // then
        assertThat(menu.isDisplayed()).isFalse();
    }


}
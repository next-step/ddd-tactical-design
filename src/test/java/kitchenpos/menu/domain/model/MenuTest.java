package kitchenpos.menu.domain.model;

import static kitchenpos.TestFixtureFactory.createMenuGroup;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;
import kitchenpos.common.infra.external.FakePurgomalumClient;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductNameCreationService;
import kitchenpos.product.domain.model.ProductPrice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MenuTest {

    private static Stream<List<MenuProduct>> invalidMenuProductProvider() {
        return Stream.of(
                null,
                List.of()
        );
    }

    @Test
    @DisplayName("메뉴가 생성될 때, 메뉴 그룹이 포함되지 않으면 예외가 발생한다.")
    void validate_menu_group_exists_exception() {
        // given
        ProductName productName = new ProductNameCreationService(new FakePurgomalumClient()).createName("배추");
        Product product = new Product(productName, new ProductPrice(BigDecimal.ONE));
        MenuProduct menuProduct = new MenuProduct(
                product,
                new MenuProductQuantity(1),
                UUID.randomUUID()
        );

        // when // then
        assertThatThrownBy(() -> new Menu(
                new MenuName("김치"),
                new MenuPrice(BigDecimal.ONE),
                null,
                false,
                List.of(menuProduct),
                UUID.randomUUID()
        )).isInstanceOf(NoSuchElementException.class)
                .hasMessage("메뉴 그룹이 존재하지 않습니다!");
    }

    @ParameterizedTest
    @MethodSource("invalidMenuProductProvider")
    @DisplayName("메뉴가 생성될 때, 메뉴 상품들이 포함되지 않으면 예외가 발생한다.")
    void validate_menu_products_exists_exception(List<MenuProduct> menuProducts) {
        // given
        MenuGroup menuGroup = createMenuGroup();

        // when // then
        assertThatThrownBy(() -> new Menu(
                new MenuName("김치"),
                new MenuPrice(BigDecimal.ONE),
                menuGroup,
                false,
                menuProducts,
                menuGroup.getId()
        )).isInstanceOf(NoSuchElementException.class)
                .hasMessage("메뉴 상품이 존재하지 않습니다!");
    }
}

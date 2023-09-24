package kitchenpos.eatinorders.domain.orders;

import kitchenpos.eatinorders.infrastructure.MenuClientImpl;
import kitchenpos.menus.application.InMemoryMenuRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static kitchenpos.Fixtures.menu;
import static kitchenpos.eatin_orders.EatInOrderFixtures.eatInOrderLineItemMaterial;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class EatInOrderLineItemsTest {

    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private MenuClient menuClient;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryProductRepository();
        menuRepository = new InMemoryMenuRepository();
        menuClient = new MenuClientImpl(menuRepository);
    }

    @DisplayName("EatInOrderLineItems를 생성할 수 있다.")
    @Test
    void create() {
        final Menu menu = menuRepository.save(menu(true, productRepository));
        final EatInOrderLineItems eatInOrderLineItems = EatInOrderLineItems.from(
                List.of(eatInOrderLineItemMaterial(menu.getId())),
                menuClient
        );
        assertAll(
                () -> assertNotNull(eatInOrderLineItems),
                () -> assertThat(eatInOrderLineItems.getOrderLineItems()).hasSize(1)
        );
    }

    @DisplayName("EatInOrderLineItems 생성 시 EatInOrderLineMaterial이 비어있으면 예외를 던진다.")
    @NullAndEmptySource
    @ParameterizedTest
    void createWithEmptyMaterials(List<EatInOrderLineItemMaterial> eatInOrderLineItemMaterials) {
        assertThatThrownBy(() -> EatInOrderLineItems.from(
                eatInOrderLineItemMaterials,
                menuClient
        )).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("EatInOrderLineItems 생성 시 Menu가 존재하지 않으면 예외를 던진다.")
    @Test
    void createWithNotExistsMenu() {
        assertThatThrownBy(() -> EatInOrderLineItems.from(
                List.of(eatInOrderLineItemMaterial(UUID.randomUUID())),
                menuClient
        )).isInstanceOf(IllegalArgumentException.class);
    }
}

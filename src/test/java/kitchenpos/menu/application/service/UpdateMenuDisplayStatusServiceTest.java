package kitchenpos.menu.application.service;

import kitchenpos.menu.adapter.out.persistance.JpaMenuEntityEntityRepository;
import kitchenpos.menu.adapter.out.persistance.JpaMenuGroupEntityRepository;
import kitchenpos.menu.adapter.out.persistance.entity.MenuEntity;
import kitchenpos.menu.adapter.out.persistance.entity.MenuGroupEntity;
import kitchenpos.menu.adapter.out.persistance.entity.MenuProductEntity;
import kitchenpos.menu.application.port.in.UpdateMenuDisplayStatusUseCase;
import kitchenpos.product.application.port.out.SaveProductPort;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UpdateMenuDisplayStatusServiceTest {
    private final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase;
    private final SaveProductPort saveProductPort;
    private final JpaMenuGroupEntityRepository menuGroupEntityRepository;
    private final JpaMenuEntityEntityRepository menuEntityRepository;

    public UpdateMenuDisplayStatusServiceTest(
            final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase,
            final SaveProductPort saveProductPort,
            final JpaMenuGroupEntityRepository menuGroupEntityRepository,
            final JpaMenuEntityEntityRepository menuEntityRepository
    ) {
        this.updateMenuDisplayStatusUseCase = updateMenuDisplayStatusUseCase;
        this.saveProductPort = saveProductPort;
        this.menuGroupEntityRepository = menuGroupEntityRepository;
        this.menuEntityRepository = menuEntityRepository;
    }


    private static final UUID MENU_UUID = UUID.randomUUID();
    private static final UUID PRODUCT_UUID = UUID.randomUUID();
    private static final UUID MENU_GROUP_UUID = UUID.randomUUID();

    @BeforeEach
    void setup() {
        Product product = createProduct(PRODUCT_UUID, "간장치킨", new BigDecimal(19000));
        saveProductPort.save(product);

        MenuGroupEntity menuGroup = createMenuGroup(MENU_GROUP_UUID, "치킨류");
        menuGroupEntityRepository.save(menuGroup);

        List<MenuProductEntity> menuProducts = List.of(createMenuProduct(PRODUCT_UUID, product, 1));
        MenuEntity menu = createMenu(MENU_UUID, "간장치킨", new BigDecimal(19000), MENU_GROUP_UUID, menuGroup, menuProducts);
        menu.setDisplayed(true);
        menuEntityRepository.save(menu);
    }

    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("`Product Price`가 변경되어 `Menu Price`가 `Total Product Price` 보다 크게되면 `Menu`는 `Hide Menu`가 된다.")
    @Test
    void update_menu_display_status_based_on_product_price() {
        // given
        MenuEntity menuEntity = menuEntityRepository.findAllByProductId(PRODUCT_UUID).stream().findFirst()
                .orElseThrow(NoSuchElementException::new);
        menuEntity.getMenuProducts().forEach(menuProduct -> {
            menuProduct.setProductPrice(new BigDecimal(18000));
        });
        menuEntityRepository.save(menuEntity);

        // when
        updateMenuDisplayStatusUseCase.execute(PRODUCT_UUID);

        // then
        MenuEntity menu = menuEntityRepository.findById(MENU_UUID)
                .orElseThrow(NoSuchElementException::new);
        assertThat(menu.isDisplayed()).isFalse();
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

    private static MenuProductEntity createMenuProduct(UUID productId, Product product, int quantity) {
        MenuProductEntity menuProduct = new MenuProductEntity();
        menuProduct.setProductId(productId);
        if (product != null) {
            menuProduct.setProductPrice(product.getPrice());
            menuProduct.setProductId(product.getId());
        }
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }

    private static Product createProduct(UUID id, String name, BigDecimal price) {
        ProductName productName = ProductName.of(name, nm -> false);
        ProductPrice productPrice = ProductPrice.of(price);
        return new Product(id, productName, productPrice);
    }

    private static MenuGroupEntity createMenuGroup(UUID id, String name) {
        MenuGroupEntity menuGroup = new MenuGroupEntity();
        menuGroup.setId(id);
        menuGroup.setName(name);
        return menuGroup;
    }
}
package kitchenpos.menu.application.service;

import kitchenpos.ClientTestConfiguration;
import kitchenpos.menu.application.port.in.UpdateMenuDisplayStatusUseCase;
import kitchenpos.menu.application.port.out.MenuGroupRepository;
import kitchenpos.menu.application.port.out.MenuRepository;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuProduct;
import kitchenpos.product.adapter.out.persistance.ProductEntityRepository;
import kitchenpos.product.adapter.out.persistance.entity.ProductEntity;
import kitchenpos.product.application.port.out.SaveProductPort;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.shared.port.out.PurgomalumClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(ClientTestConfiguration.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class UpdateMenuDisplayStatusServiceTest {
    private final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase;
    private final SaveProductPort saveProductPort;
    private final ProductEntityRepository productEntityRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuRepository menuRepository;

    public UpdateMenuDisplayStatusServiceTest(
            final UpdateMenuDisplayStatusUseCase updateMenuDisplayStatusUseCase,
            final SaveProductPort saveProductPort,
            final ProductEntityRepository productEntityRepository,
            final MenuGroupRepository menuGroupRepository,
            final MenuRepository menuRepository
    ) {
        this.updateMenuDisplayStatusUseCase = updateMenuDisplayStatusUseCase;
        this.saveProductPort = saveProductPort;
        this.productEntityRepository = productEntityRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuRepository = menuRepository;
    }


    private static final UUID MENU_UUID = UUID.randomUUID();
    private static final UUID PRODUCT_UUID = UUID.randomUUID();
    private static final UUID MENU_GROUP_UUID = UUID.randomUUID();

    @BeforeEach
    void setup() {
        Product product = createProduct(PRODUCT_UUID, "간장치킨", new BigDecimal(19000));
        saveProductPort.save(product);

        MenuGroup menuGroup = createMenuGroup(MENU_GROUP_UUID, "치킨류");
        menuGroupRepository.save(menuGroup);

        List<MenuProduct> menuProducts = List.of(createMenuProduct(PRODUCT_UUID, product, 1));
        Menu menu = createMenu(MENU_UUID, "간장치킨", new BigDecimal(19000), MENU_GROUP_UUID, menuGroup, menuProducts);
        menu.setDisplayed(true);
        menuRepository.save(menu);
    }

    @Sql(value = "/delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("`Product Price`가 변경되어 `Menu Price`가 `Total Product Price` 보다 크게되면 `Menu`는 `Hide Menu`가 된다.")
    @Test
    void update_menu_display_status_based_on_product_price() {
        // given
        ProductEntity productEntity = productEntityRepository.findById(PRODUCT_UUID)
                .orElseThrow(NoSuchElementException::new);
        productEntity.setPrice(new BigDecimal(18000));
        productEntityRepository.save(productEntity);

        // when
        updateMenuDisplayStatusUseCase.execute(PRODUCT_UUID);

        // then
        Menu menu = menuRepository.findById(MENU_UUID)
                .orElseThrow(NoSuchElementException::new);
        assertThat(menu.isDisplayed()).isFalse();
    }

    private static Menu createMenu(UUID id, String name, BigDecimal price, UUID menuGroupId, MenuGroup menuGroup, List<MenuProduct> menuProducts) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setName(name);
        menu.setPrice(price);
        menu.setMenuGroupId(menuGroupId);
        menu.setMenuGroup(menuGroup);
        menu.setMenuProducts(menuProducts);
        return menu;
    }

    private static MenuProduct createMenuProduct(UUID productId, Product proudct, int quantity) {
        MenuProduct menuProduct = new MenuProduct();
        menuProduct.setProductId(productId);
        if (proudct != null) {
            menuProduct.setProduct(ProductEntity.of(proudct));
        }
        menuProduct.setQuantity(quantity);
        return menuProduct;
    }

    private static Product createProduct(UUID id, String name, BigDecimal price) {
        ProductName productName = ProductName.of(name, nm -> {});
        ProductPrice productPrice = ProductPrice.of(price);
        return new Product(id, productName, productPrice);
    }

    private static MenuGroup createMenuGroup(UUID id, String name) {
        MenuGroup menuGroup = new MenuGroup();
        menuGroup.setId(id);
        menuGroup.setName(name);
        return menuGroup;
    }
}
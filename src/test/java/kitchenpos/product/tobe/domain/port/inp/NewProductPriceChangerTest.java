package kitchenpos.product.tobe.domain.port.inp;

import kitchenpos.fixture.MenuProductFixture;
import kitchenpos.fixture.ProductFixture;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.domain.MenuRepository;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.product.domain.Product;
import kitchenpos.product.domain.ProductRepository;
import kitchenpos.product.service.ProductService;
import kitchenpos.product.tobe.domain.NewProduct;
import kitchenpos.product.tobe.domain.Price;
import kitchenpos.product.tobe.domain.port.outp.NewProductRepository;
import kitchenpos.profanity.domain.PurgomalumChecker;
import kitchenpos.support.BaseServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static kitchenpos.fixture.MenuFixture.createMenu;
import static kitchenpos.fixture.MenuGroupFixture.createMenuGroup;
import static kitchenpos.fixture.NewProductFixture.createNewProduct;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class NewProductPriceChangerTest extends BaseServiceTest {
    private final NewProductPriceChanger newProductPriceChanger;
    private final ProductRepository productRepository;
    private final NewProductRepository newProductRepository;
    private final MenuGroupRepository menuGroupRepository;
    private final MenuRepository menuRepository;

    public NewProductPriceChangerTest(final NewProductPriceChanger newProductPriceChanger, final ProductRepository productRepository, final NewProductRepository newProductRepository, final MenuGroupRepository menuGroupRepository, final MenuRepository menuRepository) {
        this.newProductPriceChanger = newProductPriceChanger;
        this.productRepository = productRepository;
        this.newProductRepository = newProductRepository;
        this.menuGroupRepository = menuGroupRepository;
        this.menuRepository = menuRepository;
    }

    @DisplayName("상품은 가격 수정이 가능하다")
    @Test
    void test1() {
        final NewProduct product = newProductRepository.save(createNewProduct(UUID.randomUUID(), "상품", BigDecimal.TEN));
        final NewProductPriceChangerCommand command = new NewProductPriceChangerCommand(product.getId(), new Price(BigDecimal.valueOf(1000)));

        final NewProduct changedPriceProduct = newProductPriceChanger.change(command);

        assertAll(
                () -> assertThat(changedPriceProduct.getId()).isEqualTo(product.getId()),
                () -> assertThat(changedPriceProduct.getPrice()).isEqualTo(command.getPrice())
        );
    }

    @DisplayName("상품 가격이 수정 되었을 때 해당 상품을 포함하여 판매중인 메뉴의 가격이 더 비싸질 경우 해당 메뉴는 미표출 한다.")
    @Test
    void test2() {
        final NewProduct newProduct = newProductRepository.save(createNewProduct(UUID.randomUUID(), "치킨", BigDecimal.TEN));
        final MenuGroup menuGroup = menuGroupRepository.save(createMenuGroup(UUID.randomUUID()));
        final Product product = productRepository.findAll().get(0);
        final MenuProduct menuProduct = MenuProductFixture.createMenuProductWithDefaultId(product);
        final Menu cheapMenu = menuRepository.save(createMenu(UUID.randomUUID(), "치킨", new BigDecimal(0), menuGroup, true, List.of(menuProduct)));
        final Menu expensiveMenu = menuRepository.save(createMenu(UUID.randomUUID(), "치킨", new BigDecimal(100000000), menuGroup, true, List.of(menuProduct)));
        final NewProductPriceChangerCommand command = new NewProductPriceChangerCommand(product.getId(), new Price(BigDecimal.ONE));

        final NewProduct changedPriceProduct = newProductPriceChanger.change(command);

        assertAll(
                () -> assertThat(changedPriceProduct.getId()).isEqualTo(product.getId()),
                () -> assertThat(changedPriceProduct.getPrice()).isEqualTo(command.getPrice()),
                () -> assertThat(cheapMenu.isDisplayed()).isTrue(),
                () -> assertThat(expensiveMenu.isDisplayed()).isFalse()
        );
    }
}

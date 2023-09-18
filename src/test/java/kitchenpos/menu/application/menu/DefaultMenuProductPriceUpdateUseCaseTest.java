package kitchenpos.menu.application.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kitchenpos.menu.Fixtures;
import kitchenpos.menu.application.menu.port.in.MenuProductPriceUpdateUseCase;
import kitchenpos.menu.application.menu.port.out.MenuRepository;
import kitchenpos.menu.application.menu.port.out.Product;
import kitchenpos.menu.application.menu.port.out.ProductFindPort;
import kitchenpos.menu.domain.menu.MenuNew;
import kitchenpos.menu.domain.menu.MenuProductNew;
import kitchenpos.menu.domain.menu.MenuProducts;
import kitchenpos.menu.domain.menu.ProductPrice;
import kitchenpos.menu.domain.menu.Quantity;
import kitchenpos.menu.fakerepository.MenuFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class DefaultMenuProductPriceUpdateUseCaseTest {

    @Mock
    private ProductFindPort mockProductFindPort;

    private MenuRepository menuRepository;

    private MenuProductPriceUpdateUseCase useCase;

    @BeforeEach
    void setUp() {
        menuRepository = new MenuFakeRepository();

        useCase = new DefaultMenuProductPriceUpdateUseCase(menuRepository, mockProductFindPort);
    }

    @Test
    void update_productId를_가진_menu의_가격을_모두_변경한다() {
        // given
        final UUID productId = UUID.randomUUID();
        final MenuNew menu = menuRepository.save(create(productId, 2_000));
        configFindProductsSuccess(productId, 3_000);

        // when
        useCase.update(productId);

        // then
        final MenuNew actual = menuRepository.findById(menu.getId()).get();
        assertThat(extractProductPrice(actual)).isEqualTo(3_000);
    }

    private int extractProductPrice(final MenuNew menu) {
        return menu.getMenuProducts()
            .getValues()
            .get(0)
            .getProductPrice()
            .getValue();
    }

    private MenuNew create(final UUID productId, final int price) {
        return (MenuNew.create(
            Fixtures.TEST_MENU_NAME,
            Fixtures.TEST_MENU_PRICE,
            MenuProducts.create(List.of(
                MenuProductNew.create(productId, ProductPrice.create(price), Quantity.create(2)))),
            Fixtures.TEST_MENU_GROUP));
    }


    private void configFindProductsSuccess(final UUID productId, final int price) {
        doReturn(Optional.of(new Product(productId, price)))
            .when(mockProductFindPort)
            .find(productId);
    }
}
package kitchenpos.menu.application.menu;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

import java.util.List;
import java.util.UUID;
import kitchenpos.menu.Fixtures;
import kitchenpos.menu.application.menu.port.in.MenuDTO;
import kitchenpos.menu.application.menu.port.in.MenuRegistrationCommand;
import kitchenpos.menu.application.menu.port.in.MenuRegistrationUseCase;
import kitchenpos.menu.application.menu.port.out.MenuRepository;
import kitchenpos.menu.application.menu.port.out.Product;
import kitchenpos.menu.application.menu.port.out.ProductFindPort;
import kitchenpos.menu.application.menu.port.out.Products;
import kitchenpos.menu.application.menugroup.port.out.MenuGroupRepository;
import kitchenpos.menu.domain.menu.MenuNameFactory;
import kitchenpos.menu.domain.menu.Quantity;
import kitchenpos.menu.domain.menugroup.MenuGroupNew;
import kitchenpos.menu.fakerepository.MenuFakeRepository;
import kitchenpos.menu.fakerepository.MenuGroupFakeRepository;
import kitchenpos.support.vo.Name;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class DefaultMenuRegistrationUseCaseTest {

    @Mock
    private ProductFindPort mockProductFindPort;

    @Mock
    private MenuNameFactory mockMenuNameFactory;

    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;

    private MenuRegistrationUseCase useCase;

    @BeforeEach
    void setUp() {
        menuRepository = new MenuFakeRepository();
        menuGroupRepository = new MenuGroupFakeRepository();

        useCase = new DefaultMenuRegistrationUseCase(menuRepository, menuGroupRepository,
            mockMenuNameFactory, mockProductFindPort);
    }

    @Test
    void register_요청을_받아서_메뉴를_등록한_후_반환한다() {
        // given
        final MenuGroupNew menuGroup = menuGroupRepository.save(Fixtures.TEST_MENU_GROUP);
        configCreateNameFactorySuccess();
        final UUID productId = UUID.randomUUID();
        configFindProductsSuccess(productId);

        final MenuRegistrationCommand command = new MenuRegistrationCommand(
            Name.create(Fixtures.TEST_RAW_NAME),
            Fixtures.TEST_MENU_PRICE,
            List.of(Pair.of(productId, Quantity.create(3))),
            menuGroup.getId());

        // when
        final MenuDTO actual = useCase.register(command);

        // then
        assertThat(actual.getId()).isNotNull();
    }


    private void configCreateNameFactorySuccess() {
        doReturn(Fixtures.TEST_MENU_NAME)
            .when(mockMenuNameFactory)
            .create(Name.create(Fixtures.TEST_RAW_NAME));
    }

    private void configFindProductsSuccess(final UUID productId) {
        doReturn(new Products(List.of(new Product(productId, 1_000))))
            .when(mockProductFindPort)
            .find(anyList());
    }
}
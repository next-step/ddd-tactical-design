package kitchenpos.menus.tobe.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import kitchenpos.common.domain.DisplayedName;
import kitchenpos.common.domain.FakeProfanities;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.ProductId;
import kitchenpos.common.domain.Profanities;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.MenuValidator;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.tobe.ui.MenuProductRequest;
import kitchenpos.menus.tobe.ui.MenuRegisterRequest;
import kitchenpos.products.tobe.application.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MenuServiceTest {

    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private ProductRepository productRepository;
    private Profanities profanities;
    private MenuValidator menuValidator;
    private MenuService menuService;

    private UUID menuGroupUUID;
    private MenuGroup menuGroup;

    private UUID productUUID;
    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        profanities = new FakeProfanities();
        menuValidator = new MenuValidator(menuGroupRepository, productRepository);
        menuService = new MenuService(menuValidator, menuRepository, profanities);

        menuGroupUUID = UUID.randomUUID();
        menuGroup = menuGroupRepository.save(
            new MenuGroup(
                new MenuGroupId(menuGroupUUID),
                new DisplayedName("두마리메뉴", profanities)
            )
        );

        productUUID = UUID.randomUUID();
        product = productRepository.save(
            new Product(
                new ProductId(productUUID),
                new DisplayedName("후라이드", profanities),
                new Price(BigDecimal.valueOf(16_000L))
            )
        );
    }

    @DisplayName("1개 이상의 등록된 상품으로 메뉴를 등록할 수 있다.")
    @Test
    void register() {
        // given
        final MenuProductRequest menuProductRequest = new MenuProductRequest(
            new Random().nextLong(),
            productUUID,
            BigDecimal.valueOf(16_000L),
            2L
        );

        final MenuRegisterRequest menuCreateRequest = new MenuRegisterRequest(
            "후라이드+후라이드",
            BigDecimal.valueOf(19_000L),
            true,
            menuGroupUUID,
            Arrays.asList(menuProductRequest)
        );

        // when
        final Menu actual = menuService.register(menuCreateRequest);

        //then
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getDisplayedName()).isEqualTo(
                new DisplayedName("후라이드+후라이드", profanities)
            ),
            () -> assertThat(actual.getPrice()).isEqualTo(
                new Price(BigDecimal.valueOf(19_000L))
            ),
            () -> assertThat(actual.getMenuGroupId()).isEqualTo(
                new MenuGroupId(menuGroupUUID)
            ),
            () -> assertThat(actual.isDisplayed()).isTrue()
        );
    }
}

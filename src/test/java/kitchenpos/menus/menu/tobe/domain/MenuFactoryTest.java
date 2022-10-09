package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.FakeProfanity;
import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.common.domain.vo.Price;
import kitchenpos.common.domain.vo.exception.InvalidDisplayedNameException;
import kitchenpos.common.domain.vo.exception.InvalidPriceException;
import kitchenpos.menus.menu.tobe.domain.vo.MenuProductSpecification;
import kitchenpos.menus.menu.tobe.domain.vo.MenuSpecification;
import kitchenpos.menus.menu.tobe.domain.vo.exception.InvalidQuantityException;
import kitchenpos.menus.menu.tobe.infra.ProductContextRepositoryClient;
import kitchenpos.menus.menugroup.tobe.domain.InMemoryMenuGroupRepository;
import kitchenpos.menus.menugroup.tobe.domain.MenuGroup;
import kitchenpos.menus.menugroup.tobe.domain.MenuGroupRepository;
import kitchenpos.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MenuFactoryTest {

    private MenuFactory menuFactory;
    private ProductRepository productRepository;
    private MenuRepository menuRepository;
    private MenuGroupRepository menuGroupRepository;
    private Profanity profanity;
    private ProductContextClient productContextClient;

    private MenuGroup menuGroup;
    private Product product;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        profanity = new FakeProfanity();
        productContextClient = new ProductContextRepositoryClient(productRepository);
        menuFactory = new MenuFactory(productContextClient, menuGroupRepository, profanity);

        menuGroup = MenuGroup.create("추천메뉴");
        menuGroupRepository.save(menuGroup);

        final DisplayedName productName = DisplayedName.valueOf("치킨", profanity);
        product = Product.create(productName, 15_000L);
        productRepository.save(product);
    }

    @Nested
    class CreateTest {

        @DisplayName("성공")
        @Test
        void success() {
            final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(product.id(), 1L);
            final MenuSpecification menuSpecification = new MenuSpecification("치킨메뉴", 15_000L, menuGroup.id(), true, List.of(menuProductSpecification));

            final Menu menu = menuFactory.create(menuSpecification);

            assertAll(
                    () -> assertThat(menu.id()).isNotNull(),
                    () -> assertThat(menu.displayedName().value()).isEqualTo("치킨메뉴"),
                    () -> assertThat(menu.price()).isEqualTo(Price.valueOf(15_000L)),
                    () -> assertThat(menu.menuGroupId()).isNotNull(),
                    () -> assertThat(menu.isDisplayed()).isTrue(),
                    () -> assertThat(menu.menuProducts().values().size()).isOne()
            );
        }

        @DisplayName("메뉴그룹에 포함되어야 한다.")
        @Test
        void error_1() {
            final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(product.id(), 1L);
            final MenuSpecification menuSpecification
                    = new MenuSpecification("치킨메뉴", 15_000L, null, true, List.of(menuProductSpecification));

            assertThatThrownBy(() -> menuFactory.create(menuSpecification))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("등록된 메뉴그룹만 가능합니다.")
        @Test
        void error_2() {
            final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(product.id(), 1L);
            final MenuSpecification menuSpecification
                    = new MenuSpecification("치킨메뉴", 15_000L, UUID.randomUUID(), true, List.of(menuProductSpecification));

            assertThatThrownBy(() -> menuFactory.create(menuSpecification))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("메뉴명 정보가 있어야 한다.")
        @Test
        void error_3() {
            final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(product.id(), 1L);
            final MenuSpecification menuSpecification
                    = new MenuSpecification(null, 15_000L, menuGroup.id(), true, List.of(menuProductSpecification));

            assertThatThrownBy(() -> menuFactory.create(menuSpecification))
                    .isInstanceOf(InvalidDisplayedNameException.class);
        }

        @DisplayName("가격정보가 있어야 한다.")
        @Test
        void error_4() {
            final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(product.id(), 1L);
            final MenuSpecification menuSpecification
                    = new MenuSpecification("치킨메뉴", null, menuGroup.id(), true, List.of(menuProductSpecification));

            assertThatThrownBy(() -> menuFactory.create(menuSpecification))
                    .isInstanceOf(InvalidPriceException.class);
        }

        @DisplayName("메뉴상품 목록 정보가 있어야 합니다.")
        @Test
        void error_5() {
            final MenuSpecification menuSpecification
                    = new MenuSpecification("치킨메뉴", 15_000L, menuGroup.id(), true, Collections.emptyList());

            assertThatThrownBy(() -> menuFactory.create(menuSpecification))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("메뉴 상품은 비어있을 수 없습니다.");
        }

        @DisplayName("메뉴상품은 등록된 상품으로만 가능합니다.")
        @Test
        void error_6() {
            final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(UUID.randomUUID(), 1L);
            final MenuSpecification menuSpecification
                    = new MenuSpecification("치킨메뉴", 15_000L, menuGroup.id(), true, List.of(menuProductSpecification));

            assertThatThrownBy(() -> menuFactory.create(menuSpecification))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("메뉴상품의 수량 정보가 있어야 합니다.")
        @Test
        void error_7() {
            final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(product.id(), null);
            final MenuSpecification menuSpecification
                    = new MenuSpecification("치킨메뉴", 15_000L, menuGroup.id(), true, List.of(menuProductSpecification));

            assertThatThrownBy(() -> menuFactory.create(menuSpecification))
                    .isInstanceOf(InvalidQuantityException.class);
        }
    }
}

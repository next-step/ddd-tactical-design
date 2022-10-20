package kitchenpos.eatinorders.order.tobe.domain;

import kitchenpos.common.domain.FakeProfanity;
import kitchenpos.common.domain.Profanity;
import kitchenpos.common.domain.vo.DisplayedName;
import kitchenpos.eatinorders.order.tobe.domain.vo.OrderLineItemSpecification;
import kitchenpos.eatinorders.order.tobe.infra.MenuContextRepositoryClient;
import kitchenpos.eatinorders.ordertable.tobe.domain.InMemoryOrderTableRepository;
import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTable;
import kitchenpos.eatinorders.ordertable.tobe.domain.OrderTableRepository;
import kitchenpos.menus.menu.tobe.domain.InMemoryMenuRepository;
import kitchenpos.menus.menu.tobe.domain.Menu;
import kitchenpos.menus.menu.tobe.domain.MenuFactory;
import kitchenpos.menus.menu.tobe.domain.MenuRepository;
import kitchenpos.menus.menu.tobe.domain.ProductContextClient;
import kitchenpos.menus.menu.tobe.domain.vo.MenuProductSpecification;
import kitchenpos.menus.menu.tobe.domain.vo.MenuSpecification;
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

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EatInOrderFactoryTest {

    private MenuFactory menuFactory;
    private ProductRepository productRepository;
    private MenuGroupRepository menuGroupRepository;
    private MenuRepository menuRepository;
    private OrderTableRepository orderTableRepository;
    private Profanity profanity;
    private MenuContextClient menuContextClient;
    private ProductContextClient productContextClient;

    private Menu menu;
    private MenuGroup menuGroup;
    private Product product;
    private OrderTable orderTable;
    private OrderTable emptyTable;

    @BeforeEach
    void setUp() {
        menuRepository = new InMemoryMenuRepository();
        menuGroupRepository = new InMemoryMenuGroupRepository();
        productRepository = new InMemoryProductRepository();
        orderTableRepository = new InMemoryOrderTableRepository();
        profanity = new FakeProfanity();
        productContextClient = new ProductContextRepositoryClient(productRepository);
        menuContextClient = new MenuContextRepositoryClient(menuRepository);
        menuFactory = new MenuFactory(productContextClient, menuGroupRepository, profanity);

        menuGroup = MenuGroup.create("추천메뉴");
        menuGroupRepository.save(menuGroup);

        final DisplayedName productName = DisplayedName.valueOf("치킨", profanity);
        product = Product.create(productName, 15_000L);
        productRepository.save(product);

        final MenuProductSpecification menuProductSpecification = new MenuProductSpecification(product.id(), 1L);
        final MenuSpecification menuSpecification
                = new MenuSpecification("치킨메뉴", 15_000L, menuGroup.id(), true, List.of(menuProductSpecification));
        menu = menuFactory.create(menuSpecification);
        menuRepository.save(menu);

        orderTable = OrderTable.createEmptyTable("1번 테이블");
        emptyTable = OrderTable.createEmptyTable("2번 테이블");
        orderTable.use();
        orderTableRepository.save(orderTable);
        orderTableRepository.save(emptyTable);
    }

    @DisplayName("매장주문을 생성한다.")
    @Nested
    class CreateTest {

        @DisplayName("성공")
        @Test
        void success() {
            final OrderLineItemSpecification orderLineItemSpecification = new OrderLineItemSpecification(menu.id(), 15_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, orderTableRepository);

            final EatInOrder eatInOrder = eatInOrderFactory.create(orderTable.id(), List.of(orderLineItemSpecification));

            assertThat(eatInOrder).isNotNull();
        }

        @DisplayName("사용중인 주문테이블만 매장 주문 생성이 가능합니다.")
        @Test
        void error_1() {
            final OrderLineItemSpecification orderLineItemSpecification = new OrderLineItemSpecification(menu.id(), 15_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, orderTableRepository);

            assertThatThrownBy(() -> eatInOrderFactory.create(emptyTable.id(), List.of(orderLineItemSpecification)))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("사용중인 주문테이블만 매장 주문이 가능합니다.");
        }

        @DisplayName("전시 중인 메뉴만 주문이 가능합니다.")
        @Test
        void error_2() {
            final Menu findMenu = menuRepository.findById(menu.id()).get();
            findMenu.hide();
            final OrderLineItemSpecification orderLineItemSpecification = new OrderLineItemSpecification(findMenu.id(), 15_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, orderTableRepository);

            assertThatThrownBy(() -> eatInOrderFactory.create(orderTable.id(), List.of(orderLineItemSpecification)))
                    .isInstanceOf(IllegalStateException.class)
                    .hasMessage("전시 중인 메뉴만 주문이 가능합니다.");
        }

        @DisplayName("주문상품의 가격은 메뉴 가격과 동일해야 한다.")
        @Test
        void error_3() {
            final OrderLineItemSpecification orderLineItemSpecification = new OrderLineItemSpecification(menu.id(), 25_000L, 2);
            final EatInOrderFactory eatInOrderFactory = new EatInOrderFactory(menuContextClient, orderTableRepository);

            assertThatThrownBy(() -> eatInOrderFactory.create(orderTable.id(), List.of(orderLineItemSpecification)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("주문상품의 가격은 메뉴 가격과 동일해야 합니다.");
        }
    }
}

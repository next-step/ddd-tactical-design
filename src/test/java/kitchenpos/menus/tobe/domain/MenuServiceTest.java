package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.BDDMockito.given;


//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private MenuService menuService;

    private List<MenuGroup> expectedMenuGroups;
    private Menu expectedMenu;

    @BeforeEach
    void setUp() {
        expectedMenuGroups = Arrays.asList(
                new MenuGroup(1L, "두마리메뉴"),
                new MenuGroup(2L, "한마리메뉴"),
                new MenuGroup(3L, "순살파닭두마리메뉴"),
                new MenuGroup(4L, "신메뉴")
        );
        List<MenuProduct> menuProducts = Arrays.asList(
                new MenuProduct(null, 2L, 1L)
        );
        expectedMenu = new Menu("후라이드치킨", BigDecimal.valueOf(16_000), 2L, menuProducts);
    }

    @Test
    @DisplayName("객체 생성 테스트")
    void create() {
        Menu menu = new Menu("후라", BigDecimal.valueOf(1_000), 3L, Arrays.asList(new MenuProduct(null, 1L, 1L)));
        // give
        given(menuGroupRepository.findAll())
                .willReturn(expectedMenuGroups);
        given(productRepository.findById(1L))
                .willReturn(Optional.of(new Product(1L, "양념", BigDecimal.valueOf(17_000))));
        given(menuRepository.save(menu))
                .willReturn(expectedMenu);

        Menu actualMenu = menuService.create(menu);
        assertThat(actualMenu).isEqualTo(expectedMenu);
    }

    @Test
    @DisplayName("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 크거나 같아야 한다")
    void comparePrice() {
        // give
        Menu menu = new Menu("후라", BigDecimal.valueOf(18_000), 3L, Arrays.asList(new MenuProduct(null, 1L, 1L)));
        given(menuGroupRepository.findAll())
                .willReturn(expectedMenuGroups);
        given(productRepository.findById(1L))
                .willReturn(Optional.of(new Product(1L, "양념", BigDecimal.valueOf(17_000))));
        assertThatIllegalArgumentException().isThrownBy(() -> menuService.create(menu));

    }
}

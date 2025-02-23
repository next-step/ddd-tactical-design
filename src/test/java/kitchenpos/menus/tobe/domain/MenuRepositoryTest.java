package kitchenpos.menus.tobe.domain;

import kitchenpos.menus.tobe.domain.vo.EmptyProfanities;
import kitchenpos.menus.tobe.domain.vo.Profanities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("메뉴 저장소 단위 테스트")
public class MenuRepositoryTest {

    private MenuRepository menuRepository;
    private String name;
    private long price;
    private Profanities profanities;
    private MenuGroup menuGroup;
    private List<MenuProduct> menuProducts;

    @BeforeEach
    void setUp() {
        name = "메뉴";
        price = 1_000L;
        profanities = new EmptyProfanities();
        menuGroup = new MenuGroup(UUID.randomUUID(), "메뉴 그룹");
        menuProducts = new ArrayList<>(
                List.of(
                        new MenuProduct(null, 1000L, 1L, null, 1L),
                        new MenuProduct(null, 2000L, 2L, null, 2L)
                )
        );
        menuRepository = new InMemoryMenuRepository();
    }

    @DisplayName("매뉴를 저장할 수 있다.")
    @Test
    void save() {
        final Menu menu = menuRepository.save(new Menu(name, profanities, price, menuGroup.idValue(), menuProducts, false));

        assertAll(
                () -> assertThat(menu).isNotNull(),
                () -> assertThat(menu.menuProducts()).hasSize(menuProducts.size()),
                () -> assertThat(menu.menuProducts()).allMatch(menuProduct -> menuProduct.menuIdValue() != null)
        );
    }

    @DisplayName("메뉴를 조회할 수 있다.")
    @Test
    void findById() {
        final Menu menu = menuRepository.save(new Menu(name, profanities, price, menuGroup.idValue(), menuProducts, false));

        final Menu actual = menuRepository.findById(menu.idValue()).get();
        assertAll(
                () -> assertThat(actual).isNotNull(),
                () -> assertThat(actual.idValue()).isEqualTo(menu.idValue()),
                () -> assertThat(actual.menuProducts()).hasSize(menu.menuProducts().size()),
                () -> assertThat(actual.menuProducts()).allMatch(menuProduct -> menuProduct.menuIdValue() != null)
        );
    }

    @DisplayName("메뉴를 조회할 수 없다.")
    @Test
    void findByIdWithNotExists() {
        assertThat(menuRepository.findById(UUID.randomUUID())).isEmpty();
    }

    @DisplayName("모든 메뉴를 조회할 수 있다.")
    @Test
    void findAll() {
        final Menu savedMenu = menuRepository.save(new Menu(name, profanities, price, menuGroup.idValue(), menuProducts, false));

        final List<Menu> actual = menuRepository.findAll();
        assertAll(
                () -> assertThat(actual).hasSize(1),
                () -> assertThat(actual.get(0).idValue()).isEqualTo(savedMenu.idValue()),
                () -> assertThat(actual.get(0).menuProducts()).hasSize(savedMenu.menuProducts().size()),
                () -> assertThat(actual.get(0).menuProducts()).allMatch(menuProduct -> menuProduct.menuIdValue() != null)
        );
    }

    @DisplayName("여러 메뉴를 조회할 수 있다.")
    @Test
    void findAllByIdIn() {
        final Menu savedMenu = menuRepository.save(new Menu(name, profanities, price, menuGroup.idValue(), menuProducts, false));

        final List<Menu> actual = menuRepository.findAllByIdIn(List.of(savedMenu.idValue()));
        assertAll(
                () -> assertThat(actual).hasSize(1),
                () -> assertThat(actual.get(0).idValue()).isEqualTo(savedMenu.idValue()),
                () -> assertThat(actual.get(0).menuProducts()).hasSize(savedMenu.menuProducts().size()),
                () -> assertThat(actual.get(0).menuProducts()).allMatch(menuProduct -> menuProduct.menuIdValue() != null)
        );
    }

    @DisplayName("상품 ID로 메뉴를 조회할 수 있다.")
    @Test
    void findAllByProductId() {
        final Menu savedMenu = menuRepository.save(new Menu(name, profanities, price, menuGroup.idValue(), menuProducts, false));
        final Long productId = savedMenu.menuProducts().stream()
                .map(MenuProduct::productId)
                .findFirst()
                .get();

        final List<Menu> actual = menuRepository.findAllByProductId(productId);
        assertAll(
                () -> assertThat(actual).hasSize(1),
                () -> assertThat(actual.get(0).idValue()).isEqualTo(savedMenu.idValue()),
                () -> assertThat(actual.get(0).menuProducts()).hasSize(savedMenu.menuProducts().size()),
                () -> assertThat(actual.get(0).menuProducts()).allMatch(menuProduct -> menuProduct.menuIdValue() != null)
        );
    }
}

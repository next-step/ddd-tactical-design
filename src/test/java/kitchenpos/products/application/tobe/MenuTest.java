package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.entity.Menu;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuTest {
    @DisplayName("상품 가격 변동 후 메뉴 가격보다 높으면 메뉴는 숨겨진다")
    @Test
    void overProductTotalPriceTest() throws Exception {
        InMemoryProductRepository repo = new InMemoryProductRepository();
        Menu menu = new Menu(new DisplayedName("test"), new Price(5000), repo);
        assertThat(menu.isShow()).isEqualTo(true);

        int expectedPrice = 6000;
        Product pd = new Product(new DisplayedName("100"), new Price(expectedPrice));
        pd.register(repo);

        menu.registerProduct(pd);

        assertThat(menu.isShow()).isEqualTo(false);
    }

    @DisplayName("Menu 가격을 가져 올 수 있다")
    @Test
    void getProductsTotalPriceTest() throws Exception {
        InMemoryProductRepository repo = new InMemoryProductRepository();
        int expectedPrice = 1000;
        Product pd = new Product(new DisplayedName("100"), new Price(expectedPrice));
        pd.register(repo);

        Menu menu = new Menu(new DisplayedName("test"), new Price(5000), repo);
        menu.registerProduct(pd);

        assertThat(menu.getProductsTotalPrice()).isEqualTo(expectedPrice);
    }
}

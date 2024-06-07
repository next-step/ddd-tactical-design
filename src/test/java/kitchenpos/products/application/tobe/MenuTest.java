package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.ProductService;
import kitchenpos.products.tobe.domain.entity.Menu;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MenuTest {
    @DisplayName("Menu 가격을 가져 올 수 있다")
    @Test
    void getPriceTest() throws Exception {
        InMemoryProductRepository repo = new InMemoryProductRepository();
        int expectedPrice = 1000;
        Product pd = new Product(new DisplayedName("100"), new Price(expectedPrice));
        pd.register(repo);

        Menu menu = new Menu(new DisplayedName("test"));
        menu.addProduct(pd);

        assertThat(menu.getPrice(repo)).isEqualTo(expectedPrice);
    }
}

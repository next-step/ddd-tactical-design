package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.ProductService;
import kitchenpos.products.tobe.domain.vo.DisplayedName;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ProductServiceTest {
    @DisplayName("상품의 이름에는 비속어가 포함될 수 없다")
    @Test
    void changeWrongNameTest() throws Exception {
        Price price = new Price(10);
        DisplayedName displayedName = new DisplayedName("wrong-name");

        ProductService service = new ProductService(new InMemoryProductRepository());
        assertThatThrownBy(() -> service.register(displayedName, price))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @DisplayName("상품의 이름을 변경할 수 있다")
    @Test
    void changeGetNameTest() throws Exception {
        Price price = new Price(10);
        DisplayedName displayedName = new DisplayedName("name");

        ProductService service = new ProductService(new InMemoryProductRepository());
        Product pd = service.register(displayedName, price);

        service.changeName(pd.getId(), "new-name");

        Optional<Product> updatedPd = service.findById(pd.getId());
        Product upd = updatedPd.get();
        assertThat(upd.getName().value()).isEqualTo("new-name");
    }

    @DisplayName("상품을 등록할 수 있다")
    @Test
    void registerNewTest() {
        Price price = new Price(10);
        DisplayedName displayedName = new DisplayedName("name");

        ProductService service = new ProductService(new InMemoryProductRepository());
        service.register(displayedName, price);

        List<Product> pdList = service.getList();
        assertThat(pdList).hasSize(1);
    }
}

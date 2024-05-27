package kitchenpos.products.application.tobe;

import kitchenpos.products.tobe.domain.InMemoryProductRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductService;
import kitchenpos.products.tobe.domain.vo.Name;
import kitchenpos.products.tobe.domain.vo.Price;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ProductServiceTest {

    @DisplayName("상품의 이름을 변경할 수 있다")
    @Test
    void changeGetNameTest() throws Exception {
        Price price = new Price(10);
        Name name = new Name("name");
        Product pd = new Product(name, price);

        ProductService service = new ProductService(pd, new InMemoryProductRepository());
        service.register();

        service.changeName(pd.getId(), "new-name");

        Optional<Product> updatedPd = service.findById(pd.getId());
        Product upd = updatedPd.get();
        assertThat(upd.getName().value()).isEqualTo("new-name");
    }

    @DisplayName("상품을 등록할 수 있다")
    @Test
    void registerTest() {
        Price price = new Price(10);
        Name name = new Name("name");
        Product pd = new Product(name, price);

        ProductService service = new ProductService(pd, new InMemoryProductRepository());
        service.register();

        List<Product> pdList = service.getList();
        assertThat(pdList).hasSize(1);
    }
}

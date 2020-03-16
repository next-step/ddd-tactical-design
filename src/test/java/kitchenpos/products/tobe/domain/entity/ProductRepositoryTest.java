package kitchenpos.products.tobe.domain.entity;

import static kitchenpos.products.tobe.Fixtures.friedChicken;
import static kitchenpos.products.tobe.Fixtures.seasonedChicken;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품을 저장한다.")
    @Test
    void save() {
        Product expected = friedChicken();
        Product actual = productRepository.save(expected);
        assertProduct(expected, actual);
    }

    @DisplayName("상품 ID로 상품을 조회한다.")
    @Test
    void findById() {
        Product expected = productRepository.save(friedChicken());
        Product actual = productRepository.findById(expected.getId()).orElse(null);
        assertProduct(expected, actual);
    }

    @DisplayName("모든 상품을 조회한다.")
    @Test
    void findAll() {
        Product fried = productRepository.save(friedChicken());
        Product seasoned = productRepository.save(seasonedChicken());

        List<Product> actual = productRepository.findAll();
        assertThat(actual).containsAnyOf(fried, seasoned);
    }

    private void assertProduct(final Product expected, final Product actual) {
        assertThat(actual).isNotNull();
        assertAll(
            () -> assertThat(actual.getName()).isEqualTo(expected.getName()),
            () -> assertThat(actual.getPrice()).isEqualTo(expected.getPrice())
        );
    }
}

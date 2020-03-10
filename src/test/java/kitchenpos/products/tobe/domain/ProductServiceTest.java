package kitchenpos.products.tobe.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = Arrays.asList(
                new Product(1L, "치킨", BigDecimal.valueOf(16_000)),
                new Product(2L, "감자튀김", BigDecimal.valueOf(2_000)),
                new Product(3L, "콜라", BigDecimal.valueOf(3_000))
        );
    }

    @Test
    @DisplayName("조회 객체 크기 비교")
    void compareToSize() {
        // give
        given(productRepository.findAll())
                .willReturn(products);

        // when
        int size = productService.getProducts().size();

        // then
        assertThat(size).isEqualTo(products.size());
    }

    @Test
    @DisplayName("객체 생성")
    void createProduct() {
        // give
        Product realChicken = new Product(1L, "진짜 치킨", BigDecimal.valueOf(20_000));
        given(productRepository.save(new Product(1L, "가짜치킨", BigDecimal.valueOf(10_000))))
                .willReturn(realChicken);

        // when
        Product product = productService.createProduct(new Product(1L, "가짜치킨", BigDecimal.valueOf(10_000)));
        // then
        assertThat(product).isEqualTo(realChicken);
    }
}

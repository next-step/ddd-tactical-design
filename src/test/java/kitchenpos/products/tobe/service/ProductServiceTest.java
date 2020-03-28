package kitchenpos.products.tobe.service;
import kitchenpos.common.Price;
import kitchenpos.products.tobe.Fixtures;
import kitchenpos.products.tobe.application.DefaultProductService;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.dto.ProductDto;
import kitchenpos.products.tobe.exception.ProductDuplicationException;
import kitchenpos.common.WrongNameException;
import kitchenpos.common.WrongPriceException;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.infra.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ProductServiceTest {

    private final ProductRepository repository = new InMemoryProductRepository();
    private ProductService productService;

    @BeforeEach
    void setup (){
        productService = new DefaultProductService(repository);
    }

    @DisplayName("상품을 등록한다.")
    @Test
    void register(){
        //given
        ProductDto productDto = new ProductDto(Fixtures.friedChicken());

        //when
        ProductDto responseDto = productService.register(productDto);

        //then
        assertThat(responseDto).isEqualToComparingFieldByField(productDto);
    }

    @DisplayName("이미 등록된 상품을 다시 등록 할 수 없다.")
    @Test
    void duplicatedProduct (){
        //given
        repository.save(Fixtures.friedChicken());

        //when
        ProductDto productDto = new ProductDto(Fixtures.friedChicken());

        //then
        assertThatExceptionOfType(ProductDuplicationException.class)
            .isThrownBy(() -> productService.register(productDto));
    }

    @DisplayName("상품이름을이 입력해야한다.")
    @Test
    void registerWithoutProductName (){
        assertThatExceptionOfType(WrongNameException.class)
            .isThrownBy(() -> productService.register(new ProductDto(Fixtures.nonameProduct())));
    }

    @DisplayName("상품가격을 입력해야한다.")
    @Test
    void registerWithoutProductPrice (){
        assertThatExceptionOfType(WrongPriceException.class)
            .isThrownBy(() -> productService.register(new ProductDto(Fixtures.noPriceProduct())));
    }

    @DisplayName("여러 상품의 가격을 얻을 수 있다.")
    @Test
    void getAllPrice (){
        repository.save(Fixtures.friedChicken());
        repository.save(Fixtures.seasonedChicken());

        List<Long> ids = new ArrayList<>();
        ids.add(Fixtures.FRIED_CHICKEN_ID);
        ids.add(Fixtures.SEASONED_CHICKEN_ID);

        List<Price> expected = new ArrayList<>();
        expected.add(new Price(
            Fixtures.friedChicken().getPrice()
            )
        );
        expected.add(new Price(
            Fixtures.seasonedChicken().getPrice()
            )
        );

//        productService
    }
}

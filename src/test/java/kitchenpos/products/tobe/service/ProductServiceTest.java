package kitchenpos.products.tobe.service;

import kitchenpos.products.tobe.Fixtures;
import kitchenpos.products.tobe.application.DefaultProductService;
import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.dto.ProductDto;
import kitchenpos.products.tobe.exception.ProductDuplicationException;
import kitchenpos.products.tobe.exception.WrongProductNameException;
import kitchenpos.products.tobe.exception.WrongProductPriceException;
import kitchenpos.products.tobe.infra.InMemoryProductRepository;
import kitchenpos.products.tobe.infra.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        assertThatExceptionOfType(WrongProductNameException.class)
            .isThrownBy(() -> productService.register(new ProductDto(Fixtures.nonameProduct())));
    }

    @DisplayName("상품가격을 입력해야한다.")
    @Test
    void registerWithoutProductPrice (){
        assertThatExceptionOfType(WrongProductPriceException.class)
            .isThrownBy(() -> productService.register(new ProductDto(Fixtures.noPriceProduct())));
    }
}

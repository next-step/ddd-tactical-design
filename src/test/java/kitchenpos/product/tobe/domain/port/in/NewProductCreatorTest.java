package kitchenpos.product.tobe.domain.port.in;

import kitchenpos.product.tobe.domain.Name;
import kitchenpos.product.tobe.domain.NewProduct;
import kitchenpos.product.tobe.domain.Price;
import kitchenpos.product.tobe.domain.port.out.NewProductRepository;
import kitchenpos.profanity.domain.PurgomalumChecker;
import kitchenpos.support.BaseServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class NewProductCreatorTest extends BaseServiceTest {
    private final NewProductCreator newProductCreator;
    private final NewProductRepository newProductRepository;

    @MockBean
    private PurgomalumChecker purgomalumChecker;

    public NewProductCreatorTest(final NewProductCreator newProductCreator, final NewProductRepository newProductRepository) {
        this.newProductCreator = newProductCreator;
        this.newProductRepository = newProductRepository;
    }

    @DisplayName("상품은 등록이 가능하다")
    @Test
    void test1() {
        final NewProductCreatorCommand command = new NewProductCreatorCommand(new Name("product"), new Price(BigDecimal.valueOf(1000)));
        given(purgomalumChecker.containsProfanity(command.getName().getValue())).willReturn(false);

        final NewProduct createdNewProduct = newProductCreator.create(command);

        final NewProduct foundNewProduct = newProductRepository.findAll().get(0);

        assertAll(
                () -> assertThat(createdNewProduct.getId()).isNotNull(),
                () -> assertThat(createdNewProduct.getName()).isEqualTo(command.getName()),
                () -> assertThat(createdNewProduct.getPrice()).isEqualTo(command.getPrice()),
                () -> assertThat(foundNewProduct.getId()).isEqualTo(createdNewProduct.getId())
        );
    }

    @DisplayName("상품의 이름에 비속어가 포함되면 안된다")
    @Test
    void test3() {
        final NewProductCreatorCommand command = new NewProductCreatorCommand(new Name("비속어"), new Price(BigDecimal.valueOf(1000)));

        when(purgomalumChecker.containsProfanity(command.getName().getValue())).thenReturn(true);

        assertThatIllegalArgumentException().isThrownBy(() -> newProductCreator.create(command));
    }
}

package kitchenpos.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import kitchenpos.product.Fixtures;
import kitchenpos.product.application.port.in.ProductFindUseCase;
import kitchenpos.product.application.port.out.ProductNewRepository;
import kitchenpos.product.domain.ProductNew;
import kitchenpos.product.fakerepository.ProductNewFakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayNameGeneration(ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class DefaultProductFindUseCaseTest {

    private ProductNewRepository repository;

    private ProductFindUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = new ProductNewFakeRepository();

        useCase = new DefaultProductFindUseCase(repository);
    }

    @Test
    void findAll_저장된_모든_음식_목록을_dto로_변환하여_반환한다() {

        // given
        final ProductNew product1 = repository.save(Fixtures.create("dummy1", 1_000L));
        final ProductNew product2 = repository.save(Fixtures.create("dummy2", 3_000L));

        // when
        final List<ProductDTO> actual = useCase.findAll();

        // then
        final ProductDTO expected1 = new ProductDTO(product1);
        final ProductDTO expected2 = new ProductDTO(product2);

        assertThat(actual)
            .usingRecursiveFieldByFieldElementComparator()
            .contains(expected1, expected2);
    }
}
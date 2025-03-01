package kitchenpos.menu.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;
import kitchenpos.product.domain.model.ProductId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql({"/db/data.sql"})
class JpaMenuRepositoryTest {

    @Autowired
    private JpaMenuRepository repository;

    @Test
    void findAllByProductId() {
        var productId = ProductId.of(UUID.fromString("3b528244-34f7-406b-bb7e-690912f66b10"));
        var result = repository.findAllByProductId(productId);

        assertAll(
                () -> assertThat(result).isNotEmpty(),
                () -> assertEquals(result.size(), 1)
            );
    }
}
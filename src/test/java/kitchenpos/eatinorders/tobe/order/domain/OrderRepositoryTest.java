package kitchenpos.eatinorders.tobe.order.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    EntityManager entityManager;

    @DisplayName("")
    @Test
    void findAllByTableIdIn() {
        // given
        final List<Long> tableIds = Arrays.asList(1L, 2L);

        // when
        final List<Order> orders = orderRepository.findAllByTableIdIn(tableIds);

        // then
        orders.forEach(order -> {
            assertThat(order.getTableId()).isIn(tableIds);
        });
    }
}

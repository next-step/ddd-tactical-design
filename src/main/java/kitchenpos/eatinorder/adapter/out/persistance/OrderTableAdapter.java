package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.adapter.out.persistance.entity.OrderTableEntity;
import kitchenpos.eatinorder.application.port.out.LoadOrderTablePort;
import kitchenpos.eatinorder.application.port.out.SaveOrderTablePort;
import kitchenpos.eatinorder.domain.model.todo.OrderTable;
import kitchenpos.shared.domain.Profanities;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class OrderTableAdapter implements LoadOrderTablePort, SaveOrderTablePort {
    private final JpaOrderTableRepository jpaOrderTableRepository;
    private final Profanities profanities;

    public OrderTableAdapter(
            final JpaOrderTableRepository jpaOrderTableRepository,
            final Profanities profanities
    ) {
        this.jpaOrderTableRepository = jpaOrderTableRepository;
        this.profanities = profanities;
    }

    @Override
    public Optional<OrderTable> findById(UUID id) {
        return jpaOrderTableRepository.findById(id)
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities));
    }

    @Override
    public List<OrderTable> findAll() {
        return jpaOrderTableRepository.findAll()
                .stream()
                .map(orderTableEntity -> orderTableEntity.toDomain(profanities))
                .toList();
    }

    @Override
    public OrderTable save(OrderTable orderTable) {
        return jpaOrderTableRepository.save(OrderTableEntity.of(orderTable))
                .toDomain(profanities);
    }
}

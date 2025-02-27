package kitchenpos.eatinorder.adapter.out.persistance;

import kitchenpos.eatinorder.adapter.out.persistance.entity.Order;
import kitchenpos.eatinorder.application.port.out.LoadEatInOrderPort;
import kitchenpos.eatinorder.application.port.out.SaveEatInOrderPort;
import kitchenpos.eatinorder.domain.model.todo.EatInOrder;
import kitchenpos.eatinorder.domain.model.todo.EatInOrderStatus;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class EatInOrderAdapter implements LoadEatInOrderPort, SaveEatInOrderPort {

    private final JpaOrderRepository jpaOrderRepository;

    public EatInOrderAdapter(final JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public List<EatInOrder> findAll() {
        return jpaOrderRepository.findAll()
                .stream()
                .map(Order::toDomain)
                .toList();
    }

    @Override
    public Optional<EatInOrder> findById(UUID id) {
        return jpaOrderRepository.findById(id)
                .map(Order::toDomain);
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(UUID orderTableId, EatInOrderStatus status) {
        return jpaOrderRepository.existsByOrderTableAndStatusNot(orderTableId, status);
    }

    @Override
    public EatInOrder save(EatInOrder order) {
        return jpaOrderRepository.save(Order.of(order))
                .toDomain();
    }
}

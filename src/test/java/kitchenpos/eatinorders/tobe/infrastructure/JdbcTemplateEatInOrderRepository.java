package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.*;
import kitchenpos.eatinorders.tobe.domain.order.vo.EatInOrderId;
import kitchenpos.eatinorders.tobe.domain.ordertable.vo.OrderTableId;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JdbcTemplateEatInOrderRepository implements EatInOrderRepository {

    private final EatInOrderLineItemDao eatInOrderLineItemDao;
    private final EatInOrderDao eatInOrderDao;

    public JdbcTemplateEatInOrderRepository(final EatInOrderLineItemDao eatInOrderLineItemDao,
                                            final EatInOrderDao eatInOrderDao) {
        this.eatInOrderLineItemDao = eatInOrderLineItemDao;
        this.eatInOrderDao = eatInOrderDao;
    }

    @Transactional
    @Override
    public EatInOrder save(final EatInOrder eatInOrder) {
        eatInOrderDao.save(eatInOrder);
        eatInOrderLineItemDao.saveAll(eatInOrder.eatInOrderLineItems());
        return eatInOrder;
    }

    @Override
    public Optional<EatInOrder> findById(final EatInOrderId id) {
        final List<EatInOrderLineItem> eatInOrderLineItems = eatInOrderLineItemDao.findAllByEatInOrderId(id);
        return Optional.ofNullable(eatInOrderDao.findById(id, eatInOrderLineItems));
    }

    @Override
    public List<EatInOrder> findAll() {
        final List<EatInOrderLineItem> eatInOrderLineItems = eatInOrderLineItemDao.findAll();
        return eatInOrderDao.findAll(eatInOrderLineItems);
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final OrderTableId orderTableId, final EatInOrderStatus eatInOrderStatus) {
        return eatInOrderDao.existsByOrderTableAndStatusNot(orderTableId, eatInOrderStatus);
    }
}

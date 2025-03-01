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
    private final EatInOrderLineItemMenuDao eatInOrderLineItemMenuDao;
    private final EatInOrderDao eatInOrderDao;

    public JdbcTemplateEatInOrderRepository(final EatInOrderLineItemDao eatInOrderLineItemDao,
                                            final EatInOrderLineItemMenuDao eatInOrderLineItemMenuDao,
                                            final EatInOrderDao eatInOrderDao) {
        this.eatInOrderLineItemDao = eatInOrderLineItemDao;
        this.eatInOrderLineItemMenuDao = eatInOrderLineItemMenuDao;
        this.eatInOrderDao = eatInOrderDao;
    }

    @Transactional
    @Override
    public EatInOrder save(final EatInOrder eatInOrder) {
        eatInOrderDao.save(eatInOrder);
        final List<EatInOrderLineItem> eatInOrderLineItems = eatInOrder.eatInOrderLineItems();
        eatInOrderLineItemDao.saveAll(eatInOrder.eatInOrderLineItems());
        final List<EatInOrderLineItemMenu> eatInOrderLineItemMenus = eatInOrderLineItems.stream()
                .map(EatInOrderLineItem::eatInOrderLineItemMenu)
                .toList();
        eatInOrderLineItemMenuDao.saveAll(eatInOrderLineItemMenus);
        return eatInOrder;
    }

    @Override
    public Optional<EatInOrder> findById(final EatInOrderId id) {
        return Optional.empty();
    }

    @Override
    public List<EatInOrder> findAll() {
        return List.of();
    }

    @Override
    public boolean existsByOrderTableAndStatusNot(final OrderTableId orderTableId, final EatInOrderStatus eatInOrderStatus) {
        return false;
    }
}

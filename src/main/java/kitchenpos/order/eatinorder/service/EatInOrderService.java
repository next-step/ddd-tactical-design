package kitchenpos.order.eatinorder.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.domain.model.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import kitchenpos.order.common.model.OrderLineItem;
import kitchenpos.order.eatinorder.domain.model.EatInOrder;
import kitchenpos.order.eatinorder.domain.model.EatInOrderFactory;
import kitchenpos.order.eatinorder.domain.model.EatInOrderStatus;
import kitchenpos.order.eatinorder.domain.repository.EatInOrderRepository;
import kitchenpos.order.eatinorder.service.dto.CreateEatInOrderServiceRq;
import kitchenpos.order.eatinorder.service.dto.CreateEatInOrderServiceRq.OrderLineItemServiceDto;
import kitchenpos.order.eatinorder.service.dto.EatInOrderServiceRs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final EatInOrderFactory eatInOrderFactory;

    public EatInOrderService(
            final EatInOrderRepository eatInOrderRepository,
            final MenuRepository menuRepository,
            final EatInOrderFactory eatInOrderFactory
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.eatInOrderFactory = eatInOrderFactory;
    }

    @Transactional
    public UUID create(final CreateEatInOrderServiceRq request) {
        final List<OrderLineItem> orderLineItems = toOrderLineItems(request.getOrderLineItemDtos());
        EatInOrder eatInOrder = eatInOrderFactory.create(orderLineItems, request.getOrderTableId());
        return eatInOrderRepository.save(eatInOrder).getId();
    }

    private List<OrderLineItem> toOrderLineItems(List<OrderLineItemServiceDto> request) {
        return request.stream()
                .map(itemRq -> {
                    final Menu menu = menuRepository.findById(itemRq.getMenuId())
                            .orElseThrow(NoSuchElementException::new);
                    return new OrderLineItem(menu, itemRq.getQuantity(), menu.getId(), itemRq.getPrice());
                })
                .toList();
    }

    @Transactional
    public EatInOrderServiceRs accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.processOrderFlow(EatInOrderStatus.ACCEPTED);
        return new EatInOrderServiceRs(eatInOrder);
    }

    @Transactional
    public EatInOrderServiceRs serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.processOrderFlow(EatInOrderStatus.SERVED);
        return new EatInOrderServiceRs(eatInOrder);
    }

    @Transactional
    public EatInOrderServiceRs complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.processOrderFlow(EatInOrderStatus.COMPLETED);

        return new EatInOrderServiceRs(eatInOrder);
    }

    @Transactional(readOnly = true)
    public List<EatInOrderServiceRs> findAll() {
        return eatInOrderRepository.findAll().stream()
                .map(EatInOrderServiceRs::new)
                .toList();
    }
}

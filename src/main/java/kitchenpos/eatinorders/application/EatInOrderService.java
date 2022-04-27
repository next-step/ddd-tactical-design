package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.domain.tobe.domain.*;
import kitchenpos.eatinorders.dto.EatInOrderRegisterRequest;
import kitchenpos.eatinorders.dto.EatInOrderResponse;
import kitchenpos.eatinorders.dto.OrderStatusChangeRequest;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final EatInOrderRepository orderRepository;
    private final TobeMenuRepository menuRepository;
    private final TobeOrderTableRepository orderTableRepository;
    private final EatInOrderCompleteService orderCompleteService;

    public EatInOrderService(
            final EatInOrderRepository orderRepository,
            final TobeMenuRepository menuRepository,
            final TobeOrderTableRepository orderTableRepository,
            final EatInOrderCompleteService orderCompleteService
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.orderCompleteService = orderCompleteService;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRegisterRequest request) {
        OrderLineItems orderLineItems = new OrderLineItems(request.getOrderLineItem());
        List<TobeMenu> menus = menuRepository.findAllByIdIn(orderLineItems.getMenuIds());
        orderLineItems.validateMenus(menus);

        TobeOrderTable table = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
        if (table.isEmpty()) {
            throw new IllegalStateException();
        }

        return new EatInOrderResponse(orderRepository.save(EatInOrder.Of(orderLineItems, table)));
    }

    @Transactional
    public EatInOrderResponse accept(final OrderStatusChangeRequest request) {
        EatInOrder order = orderRepository.findById(request.getOrderId())
                .orElseThrow(NoSuchElementException::new);
        return new EatInOrderResponse(order.accept());
    }

    @Transactional
    public EatInOrderResponse serve(final OrderStatusChangeRequest request) {
        EatInOrder order = orderRepository.findById(request.getOrderId())
                .orElseThrow(NoSuchElementException::new);
        return new EatInOrderResponse(order.serve());
    }

    @Transactional
    public EatInOrderResponse complete(final OrderStatusChangeRequest request) {
        return new EatInOrderResponse(orderCompleteService.complete(request));
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(EatInOrderResponse::new)
                .collect(Collectors.toList());
    }
}

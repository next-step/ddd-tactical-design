package kitchenpos.eatinorders.application;

import kitchenpos.eatinorders.tobe.domain.order.EatInMenuClient;
import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.shared.dto.EatInOrderDto;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorders.tobe.domain.ordertable.OrderTableRepository;
import kitchenpos.eatinorders.shared.dto.EatInOrderLineItemDto;
import kitchenpos.eatinorders.shared.dto.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemPrice;
import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItemQuantity;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.shared.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final EatInMenuClient eatInMenuClient;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, MenuRepository menuRepository, OrderTableRepository orderTableRepository, EatInMenuClient eatInMenuClient) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.eatInMenuClient = eatInMenuClient;
    }

    @Transactional
    public EatInOrderDto create(final EatInOrderCreateRequest request) {
        List<EatInOrderLineItemDto> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<EatInOrderLineItem> orderLineItems = request.getOrderLineItems().stream()
                .map(eatInOrderLineItemDto -> EatInOrderLineItem.of(
                        eatInOrderLineItemDto.getMenuId(),
                        new EatInOrderLineItemQuantity(eatInOrderLineItemDto.getQuantity()),
                        new EatInOrderLineItemPrice(eatInOrderLineItemDto.getPrice()),
                        eatInMenuClient
                ))
                .collect(Collectors.toList());

        OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);

        EatInOrder eatInOrder = EatInOrder.of(
                new EatInOrderLineItems(orderLineItems),
                orderTable,
                eatInMenuClient
        );

        return ConvertUtil.convert(eatInOrderRepository.save(eatInOrder), EatInOrderDto.class);
    }

    @Transactional
    public EatInOrderDto accept(UUID orderId) {
        EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.accept();
        return ConvertUtil.convert(eatInOrder, EatInOrderDto.class);
    }

    @Transactional
    public EatInOrderDto serve(UUID orderId) {
        EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.serve();
        return ConvertUtil.convert(eatInOrder, EatInOrderDto.class);
    }

    @Transactional
    public EatInOrderDto complete(UUID orderId) {
        EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        eatInOrder.complete();

        OrderTable orderTable = orderTableRepository.findById(eatInOrder.getOrderTable().getId())
                .orElseThrow(NoSuchElementException::new);
        if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.clear();
        }
        return ConvertUtil.convert(eatInOrder, EatInOrderDto.class);
    }


    @Transactional
    public List<EatInOrderDto> findAll() {
        return ConvertUtil.convertList(eatInOrderRepository.findAll(), EatInOrderDto.class);
    }

}

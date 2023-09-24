package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.domain.OrderStatus;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderCreateRequest;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderCreateResponse;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderLineItemRequest;
import kitchenpos.eatinorders.tobe.application.dto.TobeOrderResponse;
import kitchenpos.eatinorders.tobe.domain.OrderMenu;
import kitchenpos.eatinorders.tobe.domain.TobeMenuClient;
import kitchenpos.eatinorders.tobe.domain.TobeOrder;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.TobeOrderLineItems;
import kitchenpos.eatinorders.tobe.domain.TobeOrderRepository;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TobeOrderService {
    private final TobeOrderRepository orderRepository;
    private final TobeMenuClient tobeMenuClient;
    private final TobeOrderTableRepository orderTableRepository;

    public TobeOrderService(
            final TobeOrderRepository orderRepository,
            final TobeMenuClient tobeMenuClient,
            final TobeOrderTableRepository orderTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.tobeMenuClient = tobeMenuClient;
        this.orderTableRepository = orderTableRepository;
    }

    @Transactional
    public TobeOrderCreateResponse create(final TobeOrderCreateRequest request) {
        TobeOrderLineItems tobeOrderLineItems = getTobeOrderLineItem(request.getOrderLineItems());
        final TobeOrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                                                              .orElseThrow(NoSuchElementException::new);
        orderTable.validateCreateOrder();

        TobeOrder order = new TobeOrder(UUID.randomUUID(), OrderStatus.WAITING, LocalDateTime.now(), orderTable.getId(),
                                        tobeOrderLineItems);
        TobeOrder savedOrder = orderRepository.save(order);
        return TobeOrderCreateResponse.from(savedOrder);
    }

    private TobeOrderLineItems getTobeOrderLineItem(final List<TobeOrderLineItemRequest> requests) {
        if (Objects.isNull(requests) || requests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<TobeOrderLineItem> tobeOrderLineItems = requests.stream().map(it -> {
            final OrderMenu orderMenu = tobeMenuClient.getOrderMenu(it.getMenuId());
            if (!orderMenu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (orderMenu.getMenuPrice().compareTo(it.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            return new TobeOrderLineItem(it.getQuantity(), orderMenu.getId(), orderMenu.getMenuPrice());
        }).collect(Collectors.toList());

        return new TobeOrderLineItems(tobeOrderLineItems);
    }

    @Transactional
    public TobeOrderResponse accept(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                                               .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        order.accept();

        return TobeOrderResponse.from(order);
    }

    @Transactional
    public TobeOrderResponse serve(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                                               .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.served();

        return TobeOrderResponse.from(order);
    }

    @Transactional
    public TobeOrderResponse complete(final UUID orderId) {
        final TobeOrder order = orderRepository.findById(orderId)
                                               .orElseThrow(NoSuchElementException::new);
        final OrderStatus status = order.getStatus();

        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.completed();
        TobeOrderTable orderTable = orderTableRepository.findById(order.getOrderTableId()).orElseThrow();

        if (!orderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
            orderTable.setNumberOfGuests(0);
            orderTable.setOccupied(false);
        }
        return TobeOrderResponse.from(order);
    }

    @Transactional(readOnly = true)
    public List<TobeOrderResponse> findAll() {
        return orderRepository.findAll().stream().map(TobeOrderResponse::from).collect(Collectors.toList());
    }
}

package kitchenpos.eatinorders.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.application.dto.OrderLineItemRequest;
import kitchenpos.eatinorders.application.dto.OrderRequest;
import kitchenpos.eatinorders.domain.eatinorder.OrderRepository;
import kitchenpos.common.domain.OrderStatus;
import kitchenpos.eatinorders.application.dto.OrderTableRequest;
import kitchenpos.eatinorders.domain.ordertable.OrderTableRepository;
import kitchenpos.common.domain.OrderType;
import kitchenpos.menus.domain.tobe.menu.Menu;
import kitchenpos.menus.domain.tobe.menu.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final KitchenridersClient kitchenridersClient;

    public OrderService(
        final OrderRepository orderRepository,
        final MenuRepository menuRepository,
        final OrderTableRepository orderTableRepository,
        final KitchenridersClient kitchenridersClient
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public OrderRequest create(final OrderRequest request) {
        final OrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
        final List<OrderLineItemRequest> orderLineItemRequestRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequestRequests) || orderLineItemRequestRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
            orderLineItemRequestRequests.stream()
                .map(OrderLineItemRequest::getMenuId)
                .toList()
        );
        if (menus.size() != orderLineItemRequestRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<OrderLineItemRequest> orderLineItems = new ArrayList<>();
        for (final OrderLineItemRequest orderLineItemRequest : orderLineItemRequestRequests) {
            final long quantity = orderLineItemRequest.getQuantity();
            if (type != OrderType.EAT_IN) {
                if (quantity < 0) {
                    throw new IllegalArgumentException();
                }
            }
            final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
                .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getMenuPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final OrderLineItemRequest orderLineItem = new OrderLineItemRequest();
            orderLineItem.setMenu(menu);
            orderLineItem.setQuantity(quantity);
            orderLineItems.add(orderLineItem);
        }
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setId(UUID.randomUUID());
        orderRequest.setType(type);
        orderRequest.setStatus(OrderStatus.WAITING);
        orderRequest.setOrderDateTime(LocalDateTime.now());
        orderRequest.setOrderLineItems(orderLineItems);
        if (type == OrderType.DELIVERY) {
            final String deliveryAddress = request.getDeliveryAddress();
            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
                throw new IllegalArgumentException();
            }
            orderRequest.setDeliveryAddress(deliveryAddress);
        }
        if (type == OrderType.EAT_IN) {
            final OrderTableRequest orderTableRequest = orderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
            if (!orderTableRequest.isOccupied()) {
                throw new IllegalStateException();
            }
            orderRequest.setOrderTable(orderTableRequest);
        }
        return orderRepository.save(orderRequest);
    }

    @Transactional
    public OrderRequest accept(final UUID orderId) {
        final OrderRequest orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRequest.getStatus() != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        if (orderRequest.getType() == OrderType.DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final OrderLineItemRequest orderLineItemRequest : orderRequest.getOrderLineItems()) {
                sum = orderLineItemRequest.getMenu()
                    .getMenuPrice()
                    .multiply(BigDecimal.valueOf(orderLineItemRequest.getQuantity()));
            }
            kitchenridersClient.requestDelivery(orderId, sum, orderRequest.getDeliveryAddress());
        }
        orderRequest.setStatus(OrderStatus.ACCEPTED);
        return orderRequest;
    }

    @Transactional
    public OrderRequest serve(final UUID orderId) {
        final OrderRequest orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRequest.getStatus() != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        orderRequest.setStatus(OrderStatus.SERVED);
        return orderRequest;
    }

    @Transactional
    public OrderRequest startDelivery(final UUID orderId) {
        final OrderRequest orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRequest.getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (orderRequest.getStatus() != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        orderRequest.setStatus(OrderStatus.DELIVERING);
        return orderRequest;
    }

    @Transactional
    public OrderRequest completeDelivery(final UUID orderId) {
        final OrderRequest orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (orderRequest.getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        orderRequest.setStatus(OrderStatus.DELIVERED);
        return orderRequest;
    }

    @Transactional
    public OrderRequest complete(final UUID orderId) {
        final OrderRequest orderRequest = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final OrderType type = orderRequest.getType();
        final OrderStatus status = orderRequest.getStatus();
        if (type == OrderType.DELIVERY) {
            if (status != OrderStatus.DELIVERED) {
                throw new IllegalStateException();
            }
        }
        if (type == OrderType.TAKEOUT || type == OrderType.EAT_IN) {
            if (status != OrderStatus.SERVED) {
                throw new IllegalStateException();
            }
        }
        orderRequest.setStatus(OrderStatus.COMPLETED);
        if (type == OrderType.EAT_IN) {
            final OrderTableRequest orderTableRequest = orderRequest.getOrderTable();
            if (!orderRepository.existsByOrderTableAndStatusNot(orderTableRequest, OrderStatus.COMPLETED)) {
                orderTableRequest.setNumberOfGuests(0);
                orderTableRequest.setOccupied(false);
            }
        }
        return orderRequest;
    }

    @Transactional(readOnly = true)
    public List<OrderRequest> findAll() {
        return orderRepository.findAll();
    }
}

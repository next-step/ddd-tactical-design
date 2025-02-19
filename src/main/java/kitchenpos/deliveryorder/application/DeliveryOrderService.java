package kitchenpos.deliveryorder.application;

import kitchenpos.common.exception.MenuNotFoundException;
import kitchenpos.common.exception.OrderException;
import kitchenpos.common.exception.OrderNotFoundException;
import kitchenpos.deliveryorder.domain.*;
import kitchenpos.deliveryorder.infra.KitchenridersClient;
import kitchenpos.eatinorder.domain.OrderTable;
import kitchenpos.eatinorder.domain.OrderTableRepository;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static kitchenpos.common.exception.ErrorCode.*;

@Service
public class DeliveryOrderService {
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final MenuRepository menuRepository;
    private final KitchenridersClient kitchenridersClient;
    private final OrderTableRepository orderTableRepository;

    public DeliveryOrderService(
            final DeliveryOrderRepository deliveryOrderRepository,
            final MenuRepository menuRepository,
            final KitchenridersClient kitchenridersClient,
            final OrderTableRepository orderTableRepository
    ) {
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.menuRepository = menuRepository;
        this.kitchenridersClient = kitchenridersClient;
        this.orderTableRepository = orderTableRepository;
    }


    @Transactional
    public DeliveryOrder create(final DeliveryOrder request) throws OrderException {
        final OrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new OrderException(ORDER_TYPE_INVALID);
        }
        final List<OrderLineItem> orderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(orderLineItemRequests) || orderLineItemRequests.isEmpty()) {
            throw new OrderException(ORDER_LINE_ITEM_NOT_FOUND);
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
                orderLineItemRequests.stream()
                        .map(OrderLineItem::getMenuId)
                        .toList()
        );
        if (menus.size() != orderLineItemRequests.size()) {
            throw new OrderException(ORDER_LINE_ITEM_SIZE_NOT_MATCHED);
        }
        final List<OrderLineItem> orderLineItems = new ArrayList<>();
        for (final OrderLineItem orderLineItemRequest : orderLineItemRequests) {
            final long quantity = orderLineItemRequest.getQuantity();
            if (type != OrderType.EAT_IN) {
                if (quantity < 0) {
                    throw new OrderException(ORDER_LINE_QUANTITY_NEGATIVE);
                }
            }
            final Menu menu = menuRepository.findById(orderLineItemRequest.getMenuId())
                    .orElseThrow(MenuNotFoundException::new);
            if (!menu.isDisplayed()) {
                throw new OrderException(MENU_DISPLAY_FALSE);
            }
            if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                throw new OrderException(MENU_PRICE_INVALID);
            }
            final OrderLineItem orderLineItem = new OrderLineItem();
            orderLineItem.setMenu(menu);
            orderLineItem.setQuantity(quantity);
            orderLineItems.add(orderLineItem);
        }
        DeliveryOrder order = new DeliveryOrder();
        order.setId(UUID.randomUUID());
        order.setType(type);
        order.setStatus(OrderStatus.WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(orderLineItems);
        if (type == OrderType.DELIVERY) {
            final String deliveryAddress = request.getDeliveryAddress();
            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
                throw new OrderException(DELIVERY_ADDRESS_NOT_FOUND);
            }
            order.setDeliveryAddress(deliveryAddress);
        }
        if (type == OrderType.EAT_IN) {
            final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                    .orElseThrow(() -> new OrderException(ORDER_TABLE_NOT_FOUND));
            if (!orderTable.isOccupied()) {
                throw new OrderException(ORDER_TABLE_EMPTY);
            }
            order.setOrderTable(orderTable);
        }
        return deliveryOrderRepository.save(order);
    }

    @Transactional
    public DeliveryOrder accept(final UUID orderId) {
        final DeliveryOrder order = deliveryOrderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        if (order.getStatus() != OrderStatus.WAITING) {
            throw new OrderException(ORDER_STATUS_INVALID);
        }
        if (order.getType() == OrderType.DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final OrderLineItem orderLineItem : order.getOrderLineItems()) {
                sum = orderLineItem.getMenu()
                        .getPrice()
                        .multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
            }
            kitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
        }
        order.setStatus(OrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public DeliveryOrder serve(final UUID orderId) {
        final DeliveryOrder order = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != OrderStatus.ACCEPTED) {
            throw new OrderException(ORDER_STATUS_INVALID);
        }
        order.setStatus(OrderStatus.SERVED);
        return order;
    }

    @Transactional
    public DeliveryOrder startDelivery(final UUID orderId) {
        final DeliveryOrder order = deliveryOrderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
        if (order.getType() != OrderType.DELIVERY) {
            throw new OrderException(ORDER_TYPE_INVALID);
        }
        if (order.getStatus() != OrderStatus.SERVED) {
            throw new OrderException(ORDER_STATUS_INVALID);
        }
        order.setStatus(OrderStatus.DELIVERING);
        return order;
    }

    @Transactional
    public DeliveryOrder completeDelivery(final UUID orderId) {
        final DeliveryOrder order = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != OrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        order.setStatus(OrderStatus.DELIVERED);
        return order;
    }

    @Transactional
    public DeliveryOrder complete(final UUID orderId) {
        final DeliveryOrder order = deliveryOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        final OrderType type = order.getType();
        final OrderStatus status = order.getStatus();
        if (type == OrderType.DELIVERY) {
            if (status != OrderStatus.DELIVERED) {
                throw new OrderException(ORDER_NOT_DELIVERED_YET);
            }
        }
        if (type == OrderType.TAKEOUT || type == OrderType.EAT_IN) {
            if (status != OrderStatus.SERVED) {
                throw new OrderException(ORDER_NOT_SERVED_YET);
            }
        }
        order.setStatus(OrderStatus.COMPLETED);
        if (type == OrderType.EAT_IN) {
            final OrderTable orderTable = order.getOrderTable();
            if (!deliveryOrderRepository.existsByOrderTableAndStatusNot(orderTable, OrderStatus.COMPLETED)) {
                orderTable.setNumberOfGuests(0);
                orderTable.setOccupied(false);
            }
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<DeliveryOrder> findAll() {
        return deliveryOrderRepository.findAll();
    }
}

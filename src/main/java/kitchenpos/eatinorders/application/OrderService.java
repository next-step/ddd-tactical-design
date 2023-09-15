package kitchenpos.eatinorders.application;

import kitchenpos.deliveryorders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.order.*;
import kitchenpos.ordertables.domain.OrderTable;
import kitchenpos.ordertables.domain.OrderTableRepository;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;
    private final KitchenridersClient kitchenridersClient;

    public OrderService(
            final EatInOrderRepository eatInOrderRepository,
            final MenuRepository menuRepository,
            final OrderTableRepository orderTableRepository,
            final KitchenridersClient kitchenridersClient
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        final List<EatInOrderLineItem> eatInOrderLineItemRequests = request.getOrderLineItems();
        if (Objects.isNull(eatInOrderLineItemRequests) || eatInOrderLineItemRequests.isEmpty()) {
            throw new IllegalArgumentException();
        }
        final List<Menu> menus = menuRepository.findAllByIdIn(
                eatInOrderLineItemRequests.stream()
                        .map(EatInOrderLineItem::getMenuId)
                        .collect(Collectors.toList())
        );
        if (menus.size() != eatInOrderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<EatInOrderLineItem> eatInOrderLineItems = new ArrayList<>();
        for (final EatInOrderLineItem eatInOrderLineItemRequest : eatInOrderLineItemRequests) {
            final long quantity = eatInOrderLineItemRequest.getQuantity();
            if (type != OrderType.EAT_IN) {
                if (quantity < 0) {
                    throw new IllegalArgumentException();
                }
            }
            final Menu menu = menuRepository.findById(eatInOrderLineItemRequest.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPriceValue().compareTo(eatInOrderLineItemRequest.getPrice()) != 0) {
                throw new IllegalArgumentException();
            }
            final EatInOrderLineItem eatInOrderLineItem = new EatInOrderLineItem();
            eatInOrderLineItem.setMenu(menu);
            eatInOrderLineItem.setQuantity(quantity);
            eatInOrderLineItems.add(eatInOrderLineItem);
        }
        EatInOrder order = new EatInOrder();
        order.setId(UUID.randomUUID());
        order.setType(type);
        order.setStatus(EatInOrderStatus.WAITING);
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderLineItems(eatInOrderLineItems);
        if (type == OrderType.DELIVERY) {
            final String deliveryAddress = request.getDeliveryAddress();
            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
                throw new IllegalArgumentException();
            }
            order.setDeliveryAddress(deliveryAddress);
        }
        if (type == OrderType.EAT_IN) {
            final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
                    .orElseThrow(NoSuchElementException::new);
            if (!orderTable.isOccupied()) {
                throw new IllegalStateException();
            }
            order.setOrderTable(orderTable);
        }
        return eatInOrderRepository.save(order);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        if (order.getType() == OrderType.DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final EatInOrderLineItem eatInOrderLineItem : order.getOrderLineItems()) {
                sum = eatInOrderLineItem.getMenu()
                        .getPriceValue()
                        .multiply(BigDecimal.valueOf(eatInOrderLineItem.getQuantity()));
            }
            kitchenridersClient.requestDelivery(orderId, sum, order.getDeliveryAddress());
        }
        order.setStatus(EatInOrderStatus.ACCEPTED);
        return order;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.SERVED);
        return order;
    }

    @Transactional
    public EatInOrder startDelivery(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (order.getStatus() != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.DELIVERING);
        return order;
    }

    @Transactional
    public EatInOrder completeDelivery(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (order.getStatus() != EatInOrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        order.setStatus(EatInOrderStatus.DELIVERED);
        return order;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder order = eatInOrderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        final OrderType type = order.getType();
        final EatInOrderStatus status = order.getStatus();
        if (type == OrderType.DELIVERY) {
            if (status != EatInOrderStatus.DELIVERED) {
                throw new IllegalStateException();
            }
        }
        if (type == OrderType.TAKEOUT || type == OrderType.EAT_IN) {
            if (status != EatInOrderStatus.SERVED) {
                throw new IllegalStateException();
            }
        }
        order.setStatus(EatInOrderStatus.COMPLETED);
        if (type == OrderType.EAT_IN) {
            final OrderTable orderTable = order.getOrderTable();
            if (!eatInOrderRepository.existsByOrderTableAndStatusNot(orderTable, EatInOrderStatus.COMPLETED)) {
                orderTable.setNumberOfGuests(0);
                orderTable.setOccupied(false);
            }
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }
}

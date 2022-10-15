package kitchenpos.eatinorders.application;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import kitchenpos.orders.infra.KitchenridersClient;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuRepository menuRepository;
    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final KitchenridersClient kitchenridersClient;

    public EatInOrderService(
        final EatInOrderRepository eatInOrderRepository,
        final MenuRepository menuRepository,
        final EatInOrderTableRepository eatInOrderTableRepository,
        final KitchenridersClient kitchenridersClient
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuRepository = menuRepository;
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.kitchenridersClient = kitchenridersClient;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        final OrderType type = request.getType();
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException();
        }
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
        EatInOrder eatInOrder = new EatInOrder();
        eatInOrder.setId(UUID.randomUUID());
        eatInOrder.setType(type);
        eatInOrder.setStatus(EatInOrderStatus.WAITING);
        eatInOrder.setEatInOrderDateTime(LocalDateTime.now());
        eatInOrder.setOrderLineItems(eatInOrderLineItems);
        if (type == OrderType.DELIVERY) {
            final String deliveryAddress = request.getDeliveryAddress();
            if (Objects.isNull(deliveryAddress) || deliveryAddress.isEmpty()) {
                throw new IllegalArgumentException();
            }
            eatInOrder.setDeliveryAddress(deliveryAddress);
        }
        if (type == OrderType.EAT_IN) {
            final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(request.getOrderTableId())
                .orElseThrow(NoSuchElementException::new);
            if (!eatInOrderTable.isOccupied()) {
                throw new IllegalStateException();
            }
            eatInOrder.setOrderTable(eatInOrderTable);
        }
        return eatInOrderRepository.save(eatInOrder);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        if (eatInOrder.getType() == OrderType.DELIVERY) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final EatInOrderLineItem eatInOrderLineItem : eatInOrder.getOrderLineItems()) {
                sum = eatInOrderLineItem.getMenu()
                    .getPriceValue()
                    .multiply(BigDecimal.valueOf(eatInOrderLineItem.getQuantity()));
            }
            kitchenridersClient.requestDelivery(orderId, sum, eatInOrder.getDeliveryAddress());
        }
        eatInOrder.setStatus(EatInOrderStatus.ACCEPTED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.SERVED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder startDelivery(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getType() != OrderType.DELIVERY) {
            throw new IllegalStateException();
        }
        if (eatInOrder.getStatus() != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.DELIVERING);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder completeDelivery(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.DELIVERING) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.DELIVERED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final OrderType type = eatInOrder.getType();
        final EatInOrderStatus status = eatInOrder.getStatus();
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
        eatInOrder.setStatus(EatInOrderStatus.COMPLETED);
        if (type == OrderType.EAT_IN) {
            final EatInOrderTable eatInOrderTable = eatInOrder.getOrderTable();
            if (!eatInOrderRepository.existsByEatInOrderTableAndStatusNot(eatInOrderTable, EatInOrderStatus.COMPLETED)) {
                eatInOrderTable.clear();
            }
        }
        return eatInOrder;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }
}

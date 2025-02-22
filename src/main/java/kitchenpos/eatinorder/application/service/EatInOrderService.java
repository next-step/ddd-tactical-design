package kitchenpos.eatinorder.application.service;

import kitchenpos.eatinorder.application.port.out.EatInOrderRepository;
import kitchenpos.eatinorder.application.port.out.EatInOrderTableRepository;
import kitchenpos.eatinorder.domain.*;
import kitchenpos.menu.application.port.out.MenuRepository;
import kitchenpos.menu.domain.Menu;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EatInOrderService {
    private final EatInOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final EatInOrderTableRepository eatInOrderTableRepository;

    public EatInOrderService(
            final EatInOrderRepository orderRepository,
            final MenuRepository menuRepository,
            final EatInOrderTableRepository eatInOrderTableRepository
    ) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @Transactional
    public EatInOrder create(final EatInOrder request) {
        final EatInOrderType type = request.getType();
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
                        .toList()
        );
        if (menus.size() != eatInOrderLineItemRequests.size()) {
            throw new IllegalArgumentException();
        }
        final List<EatInOrderLineItem> eatInOrderLineItems = new ArrayList<>();
        for (final EatInOrderLineItem eatInOrderLineItemRequest : eatInOrderLineItemRequests) {
            final long quantity = eatInOrderLineItemRequest.getQuantity();
            if (type != EatInOrderType.EAT_IN) {
                if (quantity < 0) {
                    throw new IllegalArgumentException();
                }
            }
            final Menu menu = menuRepository.findById(eatInOrderLineItemRequest.getMenuId())
                    .orElseThrow(NoSuchElementException::new);
            if (!menu.isDisplayed()) {
                throw new IllegalStateException();
            }
            if (menu.getPrice().compareTo(eatInOrderLineItemRequest.getPrice()) != 0) {
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
        eatInOrder.setOrderDateTime(LocalDateTime.now());
        eatInOrder.setOrderLineItems(eatInOrderLineItems);

        if (type == EatInOrderType.EAT_IN) {
            final EatInOrderTable eatInOrderTable = eatInOrderTableRepository.findById(request.getOrderTableId())
                    .orElseThrow(NoSuchElementException::new);
            if (!eatInOrderTable.isOccupied()) {
                throw new IllegalStateException();
            }
            eatInOrder.setOrderTable(eatInOrderTable);
        }
        return orderRepository.save(eatInOrder);
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.ACCEPTED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder serve(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.SERVED);
        return eatInOrder;
    }

    @Transactional
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
                .orElseThrow(NoSuchElementException::new);
        final EatInOrderType type = eatInOrder.getType();
        final EatInOrderStatus status = eatInOrder.getStatus();

        if (type == EatInOrderType.EAT_IN) {
            if (status != EatInOrderStatus.SERVED) {
                throw new IllegalStateException();
            }
        }
        eatInOrder.setStatus(EatInOrderStatus.COMPLETED);
        if (type == EatInOrderType.EAT_IN) {
            final EatInOrderTable eatInOrderTable = eatInOrder.getOrderTable();
            if (!orderRepository.existsByEatInOrderTableAndStatusNot(eatInOrderTable, EatInOrderStatus.COMPLETED)) {
                eatInOrderTable.setNumberOfGuests(0);
                eatInOrderTable.setOccupied(false);
            }
        }
        return eatInOrder;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}

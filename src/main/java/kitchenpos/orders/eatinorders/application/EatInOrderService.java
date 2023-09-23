package kitchenpos.orders.eatinorders.application;

import kitchenpos.orders.eatinorders.application.dto.EatInOrderLineItemRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderRequest;
import kitchenpos.orders.eatinorders.application.dto.EatInOrderResponse;
import kitchenpos.orders.eatinorders.application.loader.MenuLoader;
import kitchenpos.orders.eatinorders.application.loader.OrderTableStatusLoader;
import kitchenpos.orders.eatinorders.application.mapper.EatInOrderMapper;
import kitchenpos.orders.eatinorders.domain.*;
import kitchenpos.orders.eatinorders.exception.EatInOrderErrorCode;
import kitchenpos.orders.eatinorders.exception.EatInOrderException;
import kitchenpos.orders.eatinorders.exception.EatInOrderLineItemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final MenuLoader menuLoader;
    private final OrderTableStatusLoader orderTableStatusLoader;

    public EatInOrderService(EatInOrderRepository eatInOrderRepository, MenuLoader menuLoader, OrderTableStatusLoader orderTableStatusLoader) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuLoader = menuLoader;
        this.orderTableStatusLoader = orderTableStatusLoader;
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderRequest request) {
        // 주문 테이블 사용중 검증
        if (orderTableStatusLoader.isUnOccupied(request.getOrderTableId())) {
            throw new EatInOrderException(EatInOrderErrorCode.ORDER_TABLE_UNOCCUPIED);
        }
        // null, empty 검증
        if (request.getOrderLineItems() == null || request.getOrderLineItems().isEmpty()) {
            throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_LINE_ITEMS_IS_EMPTY);
        }

        List<EatInOrderLineItem> orderLineItems = request.getOrderLineItems()
                .stream()
                .map(makeOrderLineItem())
                .collect(Collectors.toUnmodifiableList());

        EatInOrder eatInOrder = eatInOrderRepository.save(EatInOrderMapper.toEntity(request, orderLineItems));
        return EatInOrderMapper.toDto(eatInOrder);
    }

    // menu 가격 검증
    private Function<EatInOrderLineItemRequest, EatInOrderLineItem> makeOrderLineItem() {
        return item -> {
            OrderedMenu orderedMenu = menuLoader.findMenuById(item.getMenuId());

            if (!orderedMenu.getMenuPrice().equal(item.getOrderPrice())) {
                throw new EatInOrderLineItemException(EatInOrderErrorCode.ORDER_PRICE_EQUAL_MENU_PRICE);
            }

            return EatInOrderMapper.toItemEntity(item, orderedMenu);
        };
    }

    @Transactional
    public EatInOrderResponse accept(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.accept();
        return EatInOrderMapper.toDto(order);
    }

    @Transactional
    public EatInOrderResponse serve(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.serve();
        return EatInOrderMapper.toDto(order);
    }


    @Transactional
    public EatInOrderResponse complete(final EatInOrderId orderId) {
        final EatInOrder order = findById(orderId);
        order.complete();
        return EatInOrderMapper.toDto(order);
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        List<EatInOrder> eatInOrders = eatInOrderRepository.findAll();
        return EatInOrderMapper.toDtos(eatInOrders);
    }

    private EatInOrder findById(EatInOrderId eatInOrderId) {
        return eatInOrderRepository.findById(eatInOrderId)
                .orElseThrow(NoSuchElementException::new);
    }

}

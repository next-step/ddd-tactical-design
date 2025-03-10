package kitchenpos.eatinorders.tobe.application.eatinorder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import kitchenpos.eatinorders.tobe.application.restaurant.TobeRestaurantTableService;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrder;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderLineItem;
import kitchenpos.eatinorders.tobe.domain.eatinorder.EatInOrderStatus;
import kitchenpos.eatinorders.tobe.domain.eatinorder.MenuValidationService;
import kitchenpos.eatinorders.tobe.domain.eatinorder.TobeEatInOrderRepository;
import kitchenpos.eatinorders.tobe.domain.restaurant.RestaurantTable;
import kitchenpos.eatinorders.tobe.dto.eatinorder.EatInOrderCreateRequest;
import kitchenpos.eatinorders.tobe.dto.eatinorder.EatInOrderLineItemRequest;
import kitchenpos.eatinorders.tobe.dto.eatinorder.EatInOrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TobeEatInOrderService {

    private final TobeEatInOrderRepository eatInOrderRepository;
    private final MenuValidationService menuValidationService;
    private final TobeRestaurantTableService restaurantTableService;

    public TobeEatInOrderService(final TobeEatInOrderRepository eatInOrderRepository,
        final MenuValidationService menuValidationService,
        final TobeRestaurantTableService restaurantTableService) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.menuValidationService = menuValidationService;
        this.restaurantTableService = restaurantTableService;
    }

    @Transactional(readOnly = true)
    public EatInOrder getById(UUID eatInOrderId) {
        return eatInOrderRepository.findById(eatInOrderId).orElseThrow(
            () -> new NoSuchElementException("EatInOrder not found with id: " + eatInOrderId));
    }

    @Transactional
    public EatInOrderResponse create(final EatInOrderCreateRequest request) {

        final RestaurantTable restaurantTable = restaurantTableService.getById(
            request.orderTableId());

        restaurantTable.validateOccupied();

        final List<EatInOrderLineItem> eatInOrderLineItems = toEntity(request.orderLineItems());

        EatInOrder eatInOrder = new EatInOrder(UUID.randomUUID(), request.type(),
            EatInOrderStatus.WAITING, LocalDateTime.now(), eatInOrderLineItems,
            restaurantTable.getId());

        return EatInOrderResponse.from(eatInOrderRepository.save(eatInOrder));
    }

    public List<EatInOrderLineItem> toEntity(List<EatInOrderLineItemRequest> requests) {
        if (Objects.isNull(requests) || requests.isEmpty()) {
            throw new IllegalArgumentException();
        }

        List<UUID> menuIds = requests.stream().map(EatInOrderLineItemRequest::menuId).toList();
        menuValidationService.validateCount(menuIds, requests.size());

        final List<EatInOrderLineItem> eatInOrderLineItems = new ArrayList<>();
        for (final EatInOrderLineItemRequest request : requests) {
            eatInOrderLineItems.add(
                new EatInOrderLineItem(request.menuId(), request.price(), menuValidationService,
                    request.quantity()));
        }
        return eatInOrderLineItems;
    }

    @Transactional
    public EatInOrderResponse accept(final UUID orderId) {
        final EatInOrder eatInOrder = getById(orderId);
        eatInOrder.accepted();
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse serve(final UUID orderId) {
        final EatInOrder eatInOrder = getById(orderId);
        eatInOrder.served();
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse complete(final UUID orderId) {
        final EatInOrder eatInOrder = getById(orderId);
        eatInOrder.completed();
        if (!eatInOrderRepository.existsByRestaurantTableIdAndStatusNot(
            eatInOrder.getRestaurantTableId(), EatInOrderStatus.COMPLETED)) {
            restaurantTableService.clear(eatInOrder.getRestaurantTableId());
        }
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }
}

package kitchenpos.order.tobe.eatinorder.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;
import kitchenpos.order.tobe.eatinorder.application.dto.CreateEatInOrderRequest;
import kitchenpos.order.tobe.eatinorder.application.dto.DetailEatInOrderResponse;
import kitchenpos.order.tobe.eatinorder.application.dto.EatInOrderLintItemDto;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrder;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderLineItem;
import kitchenpos.order.tobe.eatinorder.domain.EatInOrderRepository;
import kitchenpos.order.tobe.eatinorder.domain.OrderTable;
import kitchenpos.order.tobe.eatinorder.domain.OrderTableRepository;
import kitchenpos.order.tobe.eatinorder.event.EatInOrderCompleteEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {

    private final EatInOrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final OrderTableRepository orderTableRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public EatInOrderService(
        final EatInOrderRepository orderRepository,
        final MenuRepository menuRepository,
        final OrderTableRepository orderTableRepository,
        ApplicationEventPublisher applicationEventPublisher) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.orderTableRepository = orderTableRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public EatInOrder create(final CreateEatInOrderRequest request) {
        if (Objects.isNull(request.getOrderLineItems()) || request.getOrderLineItems().isEmpty()) { // TODO(경록) : 이 로직을 DTO로 넘길 순 없나? (이걸 써 말어?)
            throw new IllegalArgumentException();
        }

        final var orderLineItemRequests = request.getOrderLineItems();
        List<UUID> menuIds = orderLineItemRequests.stream()
            .map(EatInOrderLintItemDto::getMenuId)
            .collect(Collectors.toList());

        final var menus = menuRepository.findAllByIdIn(menuIds)
            .stream()
            .collect(Collectors.toMap(Menu::getId, Function.identity()));

        if (menus.size() != menuIds.size()) {
            throw new IllegalArgumentException();
        }

        // TODO(경록) :  도메인 서비스가 필요한 시점??     고민해보기!
        final var orderLineItems = orderLineItemRequests.stream()
            .map(orderLineItemRequest -> {
                // TODO(경록) : 이 validation 로직을 어떻게 추상화할 수 있을까? 
                final Menu menu = menus.get(orderLineItemRequest.getMenuId());
                if (!menu.isDisplayed()) {
                    throw new IllegalStateException();
                }
                if (menu.getPrice().compareTo(orderLineItemRequest.getPrice()) != 0) {
                    throw new IllegalArgumentException();
                }

                return new EatInOrderLineItem(menu, orderLineItemRequest.getQuantity());
            })
            .collect(Collectors.toList());

        final OrderTable orderTable = orderTableRepository.findById(request.getOrderTableId())
            .orElseThrow(NoSuchElementException::new);
        if (!orderTable.isOccupied()) {
            throw new IllegalStateException();
        }

        EatInOrder eatInOrder = EatInOrder.init(LocalDateTime.now(), orderLineItems, orderTable.getId());

        return orderRepository.save(eatInOrder);
    }

    @Transactional
    public DetailEatInOrderResponse accept(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        eatInOrder.accept();

        return DetailEatInOrderResponse.of(eatInOrder);
    }

    @Transactional
    public DetailEatInOrderResponse serve(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        eatInOrder.serve();

        return DetailEatInOrderResponse.of(eatInOrder);
    }

    @Transactional
    public DetailEatInOrderResponse complete(final UUID orderId) {
        final EatInOrder eatInOrder = orderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        eatInOrder.complete();

        applicationEventPublisher.publishEvent(new EatInOrderCompleteEvent(eatInOrder.getOrderTableId()));

        return DetailEatInOrderResponse.of(eatInOrder);
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return orderRepository.findAll();
    }
}

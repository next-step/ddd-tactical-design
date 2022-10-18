package kitchenpos.eatinorders.application;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.domain.EatInOrder;
import kitchenpos.eatinorders.domain.EatInOrderLineItem;
import kitchenpos.eatinorders.domain.EatInOrderLineItems;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.ui.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.request.EatInOrderLineItemCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import kitchenpos.event.EatInOrderCompletedEvent;
import kitchenpos.reader.application.EatInOrderTableOccupiedChecker;
import kitchenpos.reader.application.MenuPriceReader;
import kitchenpos.reader.domain.MenuPriceAndDisplayed;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final EatInOrderTableOccupiedChecker tableOccupiedChecker;
    private final MenuPriceReader menuPriceReader;
    private final ApplicationEventPublisher publisher;

    public EatInOrderService(
        EatInOrderRepository eatInOrderRepository,
        EatInOrderTableOccupiedChecker tableOccupiedChecker,
        MenuPriceReader menuPriceReader,
        ApplicationEventPublisher publisher
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.tableOccupiedChecker = tableOccupiedChecker;
        this.menuPriceReader = menuPriceReader;
        this.publisher = publisher;
    }

    @Transactional
    public EatInOrderResponse create(EatInOrderCreateRequest request) {
        EatInOrderLineItems eatInOrderLineItems = convertEatInOrderLineItems(request.getEatInOrderLineItems());
        validateTableOccupied(request.getEatInOrderTableId());
        EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, request.getEatInOrderTableId());
        return EatInOrderResponse.from(eatInOrderRepository.save(eatInOrder));
    }

    private EatInOrderLineItems convertEatInOrderLineItems(List<EatInOrderLineItemCreateRequest> requests) {
        List<EatInOrderLineItem> eatInOrderLineItems = requests.stream()
            .map(this::convertEatInOrderLineItem)
            .collect(Collectors.toList());
        return new EatInOrderLineItems(eatInOrderLineItems);
    }

    private EatInOrderLineItem convertEatInOrderLineItem(EatInOrderLineItemCreateRequest request) {
        MenuPriceAndDisplayed menu = menuPriceReader.getMenuPriceAndDisplayedById(request.getMenuId());
        validateMenuDisplayed(menu);
        return new EatInOrderLineItem(
            menu.getMenuId(),
            request.getQuantity(),
            menu.getPrice()
        );
    }

    private void validateMenuDisplayed(MenuPriceAndDisplayed menu) {
        if (menu.isNotDisplayed()) {
            throw new IllegalStateException("메뉴가 전시중이지 않아 주문을 진행할 수 없습니다.");
        }
    }

    private void validateTableOccupied(UUID eatInOrderTableId) {
        if (tableOccupiedChecker.isEatInOrderTableNotOccupied(eatInOrderTableId)) {
            throw new IllegalStateException("빈 테이블에는 주문을 등록할 수 없습니다.");
        }
    }

    @Transactional
    public EatInOrderResponse accept(UUID orderId) {
        EatInOrder eatInOrder = findOrderById(orderId);
        eatInOrder.accept();
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse serve(UUID orderId) {
        EatInOrder eatInOrder = findOrderById(orderId);
        eatInOrder.serve();
        return EatInOrderResponse.from(eatInOrder);
    }

    @Transactional
    public EatInOrderResponse complete(UUID orderId) {
        EatInOrder eatInOrder = findOrderById(orderId);
        eatInOrder.complete();
        publishCompletedEvent(eatInOrder);
        return EatInOrderResponse.from(eatInOrder);
    }

    private void publishCompletedEvent(EatInOrder eatInOrder) {
        EatInOrderCompletedEvent event = new EatInOrderCompletedEvent(eatInOrder.getEatInOrderTableId());
        publisher.publishEvent(event);
    }

    private EatInOrder findOrderById(UUID orderId) {
        return eatInOrderRepository.findById(orderId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 주문을 찾을 수 없습니다."));
    }

    @Transactional(readOnly = true)
    public List<EatInOrderResponse> findAll() {
        return EatInOrderResponse.of(eatInOrderRepository.findAll());
    }
}

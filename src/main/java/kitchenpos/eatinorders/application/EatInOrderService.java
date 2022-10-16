package kitchenpos.eatinorders.application;

import java.util.*;
import java.util.stream.Collectors;
import kitchenpos.eatinorders.domain.*;
import kitchenpos.eatinorders.ui.request.EatInOrderCreateRequest;
import kitchenpos.eatinorders.ui.request.EatInOrderLineItemCreateRequest;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import kitchenpos.menus.domain.Menu;
import kitchenpos.reader.application.MenuPriceReader;
import kitchenpos.reader.domain.MenuPriceAndDisplayed;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EatInOrderService {
    private final EatInOrderRepository eatInOrderRepository;
    private final EatInOrderTableRepository eatInOrderTableRepository;
    private final MenuPriceReader menuPriceReader;

    public EatInOrderService(
        EatInOrderRepository eatInOrderRepository,
        EatInOrderTableRepository eatInOrderTableRepository,
        MenuPriceReader menuPriceReader
    ) {
        this.eatInOrderRepository = eatInOrderRepository;
        this.eatInOrderTableRepository = eatInOrderTableRepository;
        this.menuPriceReader = menuPriceReader;
    }

    @Transactional
    public EatInOrderResponse create(EatInOrderCreateRequest request) {
        EatInOrderLineItems eatInOrderLineItems = convertEatInOrderLineItems(request.getEatInOrderLineItems());
        EatInOrderTable eatInOrderTable = findEatInOrderTableById(request.getEatInOrderTableId());
        validateTableOccupied(eatInOrderTable);
        EatInOrder eatInOrder = new EatInOrder(eatInOrderLineItems, eatInOrderTable);
        return EatInOrderResponse.from(eatInOrderRepository.save(eatInOrder));
    }

    private EatInOrderTable findEatInOrderTableById(UUID eatInOrderTableId) {
        return eatInOrderTableRepository.findById(eatInOrderTableId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 주문 테이블을 찾을 수 없습니다."));
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

    private static void validateMenuDisplayed(MenuPriceAndDisplayed menu) {
        if (menu.isNotDisplayed()) {
            throw new IllegalStateException("메뉴가 전시중이지 않아 주문을 진행할 수 없습니다.");
        }
    }

    private static void validateTableOccupied(EatInOrderTable eatInOrderTable) {
        if (eatInOrderTable.isNotOccupied()) {
            throw new IllegalStateException("빈 테이블에는 주문을 등록할 수 없습니다.");
        }
    }

    @Transactional
    public EatInOrder accept(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        if (eatInOrder.getStatus() != EatInOrderStatus.WAITING) {
            throw new IllegalStateException();
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
    public EatInOrder complete(final UUID orderId) {
        final EatInOrder eatInOrder = eatInOrderRepository.findById(orderId)
            .orElseThrow(NoSuchElementException::new);
        final EatInOrderStatus status = eatInOrder.getStatus();
        if (status != EatInOrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        eatInOrder.setStatus(EatInOrderStatus.COMPLETED);
        final EatInOrderTable eatInOrderTable = eatInOrder.getOrderTable();
        if (!eatInOrderRepository.existsByEatInOrderTableAndStatusNot(eatInOrderTable, EatInOrderStatus.COMPLETED)) {
            eatInOrderTable.clear();
        }
        return eatInOrder;
    }

    @Transactional(readOnly = true)
    public List<EatInOrder> findAll() {
        return eatInOrderRepository.findAll();
    }
}

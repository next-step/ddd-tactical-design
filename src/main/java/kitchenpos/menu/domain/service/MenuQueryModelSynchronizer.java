package kitchenpos.menu.domain.service;

import kitchenpos.menu.domain.model.MenuSummary;
import kitchenpos.menu.domain.model.MenuSummaryEvent;
import kitchenpos.menu.domain.repository.MenuSummaryRepository;
import kitchenpos.product.domain.model.ProductSummary;
import kitchenpos.product.domain.repository.ProductSummaryRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MenuQueryModelSynchronizer {
    private final MenuSummaryRepository menuSummaryRepository;
    private final ProductSummaryRepository productSummaryRepository; // 이 레포지토리 추가

    public MenuQueryModelSynchronizer(MenuSummaryRepository menuSummaryRepository,
                                      ProductSummaryRepository productSummaryRepository) {
        this.menuSummaryRepository = menuSummaryRepository;
        this.productSummaryRepository = productSummaryRepository;
    }

    @EventListener
    public void updateReadModel(MenuSummaryEvent event) {
        for (ProductSummary productSummary : event.getProductSummaries()) {
            productSummaryRepository.save(productSummary);
        }
        menuSummaryRepository.save(new MenuSummary(event));
    }
}

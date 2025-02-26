package kitchenpos.menu.application;

import java.util.List;
import kitchenpos.menu.domain.model.MenuSummary;
import kitchenpos.menu.domain.repository.MenuQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class MenuQueryService {
    private final MenuQueryRepository menuQueryRepository;

    public MenuQueryService(MenuQueryRepository menuQueryRepository) {
        this.menuQueryRepository = menuQueryRepository;
    }

    public List<MenuSummary> findAll() {
        return menuQueryRepository.findAll();
    }
}

package kitchenpos.eatinorders.application;

import java.util.List;
import kitchenpos.eatinorders.domain.EatInOrderRepository;
import kitchenpos.eatinorders.ui.response.EatInOrderResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class EatInOrderRepresentationService {
    private final EatInOrderRepository eatInOrderRepository;

    public EatInOrderRepresentationService(EatInOrderRepository eatInOrderRepository) {
        this.eatInOrderRepository = eatInOrderRepository;
    }

    public List<EatInOrderResponse> findAll() {
        return EatInOrderResponse.of(eatInOrderRepository.findAll());
    }
}

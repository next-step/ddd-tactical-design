package kitchenpos.eatinordertables.application;

import java.util.List;
import kitchenpos.eatinordertables.domain.EatInOrderTableRepository;
import kitchenpos.eatinordertables.ui.response.EatInOrderTableResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class EatInOrderTableRepresentationService {
    private final EatInOrderTableRepository eatInOrderTableRepository;

    public EatInOrderTableRepresentationService(
        EatInOrderTableRepository eatInOrderTableRepository
    ) {
        this.eatInOrderTableRepository = eatInOrderTableRepository;
    }

    @Transactional(readOnly = true)
    public List<EatInOrderTableResponse> findAll() {
        return EatInOrderTableResponse.of(eatInOrderTableRepository.findAll());
    }
}

package kitchenpos.eatinorders.tobe.application;

import kitchenpos.eatinorders.tobe.domain.TobeOrderTable;
import kitchenpos.eatinorders.tobe.domain.TobeOrderTableRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TobeOrderTableService {

    private final TobeOrderTableRepository tobeOrderTableRepository;

    public TobeOrderTableService(TobeOrderTableRepository tobeOrderTableRepository) {
        this.tobeOrderTableRepository = tobeOrderTableRepository;
    }

    // 사용 가능한 테이블인지 확인
    public void isAvailableTable(UUID orderTableId) {

        TobeOrderTable tobeOrderTable = tobeOrderTableRepository.findById(orderTableId)
                .orElseThrow(() -> new IllegalArgumentException("주문 테이블이 존재하지 않습니다."));

        tobeOrderTable.isAvailableTable();
    }
}

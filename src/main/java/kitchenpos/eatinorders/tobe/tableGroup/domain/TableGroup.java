package kitchenpos.eatinorders.tobe.tableGroup.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TableGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Transient
    private List<Long> tableIds;

    public TableGroup(final List<Long> tableIds) {
        if (CollectionUtils.isEmpty(tableIds) || tableIds.size() < 2) {
            throw new IllegalArgumentException("테이블을 2개 이상 지정해야합니다.");
        }
        if (tableIds.size() != tableIds.stream()
                .distinct()
                .count()) {
            throw new IllegalArgumentException("중복된 테이블을 지정할 수 없습니다.");
        }
        this.tableIds = new ArrayList<>(tableIds);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public List<Long> getTableIds() {
        return new ArrayList<>(tableIds);
    }
}

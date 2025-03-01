package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrderLineItem;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcEatInOrderLineItemDao implements EatInOrderLineItemDao {

    private static final String SEQ = "seq";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert menuProductJdbcInsert;

    public JdbcEatInOrderLineItemDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.menuProductJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("eat_in_order_line_items")
                .usingGeneratedKeyColumns(SEQ);
    }

    @Override
    public void saveAll(final List<EatInOrderLineItem> eatInOrderLineItems) {
        final List<MapSqlParameterSource> mapSqlParameterSources = eatInOrderLineItems.stream()
                .map(eatInOrderLineItem -> new MapSqlParameterSource()
                        .addValue("id", eatInOrderLineItem.idValue())
                        .addValue("eat_in_order_id", eatInOrderLineItem.eatInOrderIdValue())
                        .addValue("quantity", eatInOrderLineItem.quantityValue())
                ).toList();
        final int[] results = menuProductJdbcInsert.executeBatch(mapSqlParameterSources.toArray(new MapSqlParameterSource[0]));
        if (results.length != eatInOrderLineItems.size()) {
            throw new IllegalArgumentException("주문 항목 저장에 실패했습니다.");
        }
    }
}

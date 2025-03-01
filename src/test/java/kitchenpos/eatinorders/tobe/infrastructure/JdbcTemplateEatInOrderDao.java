package kitchenpos.eatinorders.tobe.infrastructure;

import kitchenpos.eatinorders.tobe.domain.order.EatInOrder;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JdbcTemplateEatInOrderDao implements EatInOrderDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert eatInOrderJdbcInsert;
    private static final int EXECUTE_FAILED = 0;

    public JdbcTemplateEatInOrderDao(final DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.eatInOrderJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("eat_in_orders");
    }

    @Override
    public void save(final EatInOrder eatInOrder) {
        final SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(eatInOrder);
        final int execute = eatInOrderJdbcInsert.execute(parameterSource);
        if (execute == EXECUTE_FAILED) {
            throw new IllegalArgumentException("매장 주문 저장에 실패했습니다.");
        }
    }
}

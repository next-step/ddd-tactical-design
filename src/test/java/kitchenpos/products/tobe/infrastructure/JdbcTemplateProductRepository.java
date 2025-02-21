package kitchenpos.products.tobe.infrastructure;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.domain.vo.ProfanityName;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcTemplateProductRepository implements ProductRepository {

    private static final String TABLE_NAME = "product";
    private static final String KEY_COLUMN = "id";
    private static final String NAME_COLUMN = "name";
    private static final String PRICE_COLUMN = "price";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateProductRepository(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns(KEY_COLUMN)
        ;
    }

    @Override
    public Product save(final Product product) {
        final SqlParameterSource parameters = new BeanPropertySqlParameterSource(product);
        final Number number = jdbcInsert.executeAndReturnKey(parameters);
        return select(number.longValue());
    }

    @Override
    public Optional<Product> findById(final Long id) {
        try {
            return Optional.of(select(id));
        } catch (final EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        final String sql = "SELECT id, name, price FROM product";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    @Override
    public List<Product> findAllByIdIn(final List<Long> ids) {
        final String sql = "SELECT id, name, price FROM product WHERE id IN (:ids)";
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("ids", ids);
        return jdbcTemplate.query(sql, parameters, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private Product select(final Long id) {
        final String sql = "SELECT id, name, price FROM product WHERE id = (:id)";
        final SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id);
        return jdbcTemplate.queryForObject(sql, parameters, (resultSet, rowNumber) -> toEntity(resultSet));
    }

    private Product toEntity(final ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getLong(KEY_COLUMN),
                resultSet.getString(NAME_COLUMN),
                new ProfanityName(),
                resultSet.getLong(PRICE_COLUMN)
        );
    }
}

package kitchenpos.menus.tobe.domain;

import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuRepository {
    Menu save(Menu menu);

    Optional<Menu> findById(UUID id);

    @Query("select m from Menu m where m.id = :id and m.displayed = true and m.price = :price")
    Optional<Menu> findByIdAndDisplayedWithPrice(UUID id, BigDecimal price);

    List<Menu> findAll();

    List<Menu> findAllByIdIn(List<UUID> ids);

    List<Menu> findAllByProductId(UUID productId);
}


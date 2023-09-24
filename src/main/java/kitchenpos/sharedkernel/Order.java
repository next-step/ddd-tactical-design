package kitchenpos.sharedkernel;

import kitchenpos.menus.tobe.domain.Menu;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Order {

    @Column(name = "id", columnDefinition = "binary(16)")
    @Id
    protected UUID id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    protected OrderStatus status;

    @Column(name = "order_date_time", nullable = false)
    protected LocalDateTime orderDateTime;

    public UUID getId() {
        return id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDateTime() {
        return orderDateTime;
    }

    protected static void validateDisplayedMenu(final Menu menu) {
        if (!menu.isDisplayed()) {
            throw new IllegalStateException();
        }
    }

    protected static Map<UUID, Menu> createMenuMap(final List<Menu> menus) {
        return menus.stream()
            .map(menu -> {
                validateDisplayedMenu(menu);
                return menu;
            })
            .collect(Collectors.toMap(
                Menu::getId,
                menu -> menu
            ));
    }

    public void accept() {
        if (status != OrderStatus.WAITING) {
            throw new IllegalStateException();
        }
        status = OrderStatus.ACCEPTED;
    }

    public void serve() {
        if (status != OrderStatus.ACCEPTED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.SERVED;
    }

    public void complete() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException();
        }
        status = OrderStatus.COMPLETED;
    }
}

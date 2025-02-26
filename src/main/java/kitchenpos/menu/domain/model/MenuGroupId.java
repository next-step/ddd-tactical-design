package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public record MenuGroupId(
    @Column(name = "id", columnDefinition = "binary(16)")
    UUID id
) {

    public static MenuGroupId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_MENU_GROUP.toString());
        }
        return new MenuGroupId(id);
    }

    public UUID get() {
        return id;
    }
}
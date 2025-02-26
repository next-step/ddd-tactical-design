package kitchenpos.menu.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.UUID;
import kitchenpos.global.exception.ErrorCode;
import kitchenpos.global.exception.NotFoundException;

@Embeddable
public record MenuId(
    UUID id
) {

    public static MenuId of(UUID id) {
        if (id == null) {
            throw new NotFoundException(ErrorCode.NOT_FOUND_MENU.toString());
        }
        return new MenuId(id);
    }


    public UUID get() {
        return id;
    }
}
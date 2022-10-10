package kitchenpos.menus.domain;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.profanity.infra.ProfanityCheckClient;

@Embeddable
public class MenuName {

    @Column(name = "name", nullable = false)
    private String value;

    protected MenuName() {
    }

    public MenuName(String value, ProfanityCheckClient profanityCheckClient) {
        this.value = value;
        validateNull(this.value);
        validateBlank(this.value);
        validateProfanity(this.value, profanityCheckClient);
    }

    private void validateNull(String value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("올바르지 않은 메뉴 이름입니다.");
        }
    }

    private void validateBlank(String value) {
        if (value.isBlank()) {
            throw new IllegalArgumentException("메뉴 이름은 공백일 수 없습니다.");
        }
    }

    private void validateProfanity(String value, ProfanityCheckClient profanityCheckClient) {
        if (profanityCheckClient.containsProfanity(value)) {
            throw new IllegalArgumentException("메뉴 이름에는 비속어가 포함될 수 없습니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MenuName menuName = (MenuName) o;
        return Objects.equals(value, menuName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}

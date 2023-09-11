package kitchenpos.products.tobe.domain;

import org.thymeleaf.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static kitchenpos.products.exception.ProductExceptionMessage.PRODUCT_NAME_CONTAINS_PROFANITY;

@Embeddable
public class DisplayedName {
    @Column(name = "name", nullable = false)
    private String name;

    protected DisplayedName() {}

    private DisplayedName(String name) {
        this.name = name;
    }

    public static DisplayedName of(String name, DisplayNameChecker displayNameChecker) {
        if (StringUtils.isEmpty(name) || displayNameChecker.containsProfanity(name)) {
            throw new IllegalArgumentException(PRODUCT_NAME_CONTAINS_PROFANITY);
        }
        return new DisplayedName(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayedName that = (DisplayedName) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

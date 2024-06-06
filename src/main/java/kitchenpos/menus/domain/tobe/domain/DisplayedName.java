package kitchenpos.menus.domain.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.tobe.domain.Profanities;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

@Embeddable
public class DisplayedName {

    @Column(name = "name", nullable = false)
    private String name;

    private DisplayedName() {
    }

    private DisplayedName(String name, Profanities profanities) {
        if (StringUtils.isBlank(name) || profanities.contains(name)) {
            throw new IllegalArgumentException();
        }

        this.name = name;
    }

    public static DisplayedName of(String name, Profanities profanities) {
        return new DisplayedName(name, profanities);
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
        return Objects.hashCode(name);
    }
}

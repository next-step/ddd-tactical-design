package kitchenpos.products.tobe.domain;

import org.apache.logging.log4j.util.Strings;

import javax.persistence.Embeddable;

@Embeddable
public class Name {

    private String name;

    protected Name() {}

    public Name(String name, boolean isProfanity) {
        validate(name, isProfanity);
        this.name = name;
    }

    private void validate(String name, boolean isProfanity) {
        if (Strings.isEmpty(name)) {
            throw new IllegalArgumentException("상품명은 null 이나 공백일 수 없습니다.");
        }
        if (isProfanity) {
            throw new IllegalArgumentException("상품명에 비속어를 포함할 수 없습니다.");
        }

    }

}

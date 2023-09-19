package kitchenpos.menus.domain.tobe;

public class DisplayedName {
    private final String name;

    public DisplayedName(final String name, Profanities profanities) {
        if (profanities.contains(name)) {
            throw new IllegalArgumentException("이름에는 비속어가 포함될 수 없습니다.");
        }
        this.name = name;
    }
}

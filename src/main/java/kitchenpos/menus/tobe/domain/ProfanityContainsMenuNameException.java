package kitchenpos.menus.tobe.domain;

public class ProfanityContainsMenuNameException extends IllegalArgumentException {
    public ProfanityContainsMenuNameException() {
        super("비속어가 포함 된 메뉴 이름입니다.");
    }
}

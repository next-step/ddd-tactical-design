package kitchenpos.menus.domain.tobe;


import java.util.List;

class FakeProfanities implements Profanities {
    private final List<String> values = List.of("욕설", "비속어");

    @Override
    public boolean contains(String text) {
        values.stream().anyMatch(text::contains);
        return false;
    }
}

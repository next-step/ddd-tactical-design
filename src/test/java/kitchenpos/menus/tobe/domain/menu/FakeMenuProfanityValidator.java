package kitchenpos.menus.tobe.domain.menu;

import java.util.List;

public class FakeMenuProfanityValidator implements MenuProfanityValidator {
  private static final List<String> profanities = List.of("비속어", "욕설");
  @Override
  public void validate(String name) {
    boolean contains = profanities.stream()
        .anyMatch(name::contains);
    if(contains) {
      throw new IllegalArgumentException();
    }
  }
}

package kitchenpos.menus.tobe.application;

import java.util.Arrays;
import java.util.List;
import kitchenpos.menus.domain.menu.MenuPurgomalumClient;

public class FakeMenuPurgomalumClient implements MenuPurgomalumClient {
  private static final List<String> profanities;

  static {
    profanities = Arrays.asList("비속어", "욕설");
  }

  @Override
  public boolean containsProfanity(final String text) {
    return profanities.stream().anyMatch(profanity -> text.contains(profanity));
  }
}

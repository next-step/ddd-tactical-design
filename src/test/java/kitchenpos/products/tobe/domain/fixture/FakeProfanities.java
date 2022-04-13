package kitchenpos.products.tobe.domain.fixture;

import kitchenpos.products.tobe.domain.Profanities;

import java.util.Arrays;
import java.util.List;

public class FakeProfanities implements Profanities {
  private static final List<String> profanities = Arrays.asList("욕설", "비속어");

  @Override
  public boolean containsProfanity(String text) {
    return profanities.stream().anyMatch(text::contains);
  }
}

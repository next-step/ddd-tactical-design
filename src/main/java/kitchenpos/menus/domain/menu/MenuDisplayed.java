package kitchenpos.menus.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuDisplayed {
  @Column(name = "displayed", nullable = false)
  private boolean displayed;

  protected MenuDisplayed() {}

  protected MenuDisplayed(boolean displayed) {
    this.displayed = displayed;
  }

  public boolean isDisplayed() {
    return displayed;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MenuDisplayed that = (MenuDisplayed) o;
    return displayed == that.displayed;
  }

  @Override
  public int hashCode() {
    return Objects.hash(displayed);
  }
}

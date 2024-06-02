package kitchenpos.menus.tobe.domain.menu;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import kitchenpos.supports.domain.tobe.Price;
import kitchenpos.supports.domain.tobe.PriceFixture;
import kitchenpos.supports.domain.tobe.Quantity;
import kitchenpos.supports.domain.tobe.QuantityFixture;

public class MenuFixture {

  private static final MenuProfanityValidator menuProfanityValidator = new FakeMenuProfanityValidator();

  public static MenuName normalMenuName() {
    return createMenuName("메뉴명");
  }

  public static MenuName createMenuName(String name) {
    return new MenuName(menuProfanityValidator, name);
  }

  public static RegisteredProduct normalRegisteredProduct() {
    return new RegisteredProduct(UUID.randomUUID(), PriceFixture.normal());
  }

  public static RegisteredProduct createRegisteredProduct(UUID id, Price price) {
    return new RegisteredProduct(id, price);
  }
  public static MenuProduct createMenuProduct(Long seq, Quantity quantity) {
    return new MenuProduct(seq, quantity, normalRegisteredProduct());
  }

  public static MenuProduct createMenuProduct(Long seq, Quantity quantity, RegisteredProduct registeredProduct) {
    return new MenuProduct(seq, quantity, registeredProduct);
  }

  public static MenuProducts createMenuProducts(List<MenuProduct> menuProducts) {
    return new MenuProducts(menuProducts);
  }

  public static List<MenuProduct> normalMenuProductList(int size) {
    return IntStream.range(0, size)
        .mapToObj(cur -> createMenuProduct(Integer.toUnsignedLong(cur), QuantityFixture.normal()))
        .collect(Collectors.toList());
  }

  public static Menu normal() {
    return new Menu(UUID.randomUUID(),
        MenuFixture.normalMenuName(),
        PriceFixture.normal(),
        UUID.randomUUID(),
        true,
        createMenuProducts(normalMenuProductList(3))
    );
  }

  public static Menu create(UUID id, MenuName name, Price price, UUID menuGroupId, boolean displayed, MenuProducts menuProducts) {
    return new Menu(id, name, price, menuGroupId, displayed, menuProducts);
  }
}

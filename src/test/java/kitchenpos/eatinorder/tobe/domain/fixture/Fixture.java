package kitchenpos.eatinorder.tobe.domain.fixture;

import kitchenpos.eatinorder.tobe.domain.ordertable.NumberOfGuests;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTable;
import kitchenpos.eatinorder.tobe.domain.ordertable.OrderTableName;
import kitchenpos.fixture.tobe.ProductFixture;
import kitchenpos.infra.FakePurgomalumClient;
import kitchenpos.infra.PurgomalumClient;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuNameFactory;
import kitchenpos.menus.tobe.domain.menu.MenuPrice;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menuproduct.MenuProduct;
import kitchenpos.menus.tobe.domain.menuproduct.MenuProducts;
import kitchenpos.menus.tobe.domain.menuproduct.Quantity;
import kitchenpos.products.tobe.domain.Product;

import java.util.List;

import static kitchenpos.MoneyConstants.만원;

public class Fixture {

    public static Menu menuSetUP() {
        MenuGroup menuGroup = new MenuGroup("메뉴그룹명");
        Product product = ProductFixture.createProduct("상품명", 만원);
        MenuProduct menuProduct = MenuProduct.of(product.getId(), Quantity.of(2), product.getPrice().longValue());
        MenuProducts menuProducts = new MenuProducts(List.of(menuProduct));
        PurgomalumClient purgomalumClient = new FakePurgomalumClient();
        MenuNameFactory menuName = new MenuNameFactory(purgomalumClient);

        return Menu.of(menuName.create("메뉴이름"), MenuPrice.of(10_000L), menuGroup.getId(), true, menuProducts);
    }

    public static OrderTable tableSetUP() {
        OrderTableName tableName = OrderTableName.of("정상이름");

        return OrderTable.of(tableName);
    }
}

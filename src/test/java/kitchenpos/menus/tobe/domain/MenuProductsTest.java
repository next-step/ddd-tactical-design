package kitchenpos.menus.tobe.domain;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductsException;
import kitchenpos.products.tobe.domain.ProductId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MenuProductsTest {

    @DisplayName("메뉴상품 목록이 빈 리스트 또는 null이면 예외가 발생한다")
    @NullAndEmptySource
    @ParameterizedTest
    void aa(List<MenuProduct> products) {
        assertThatThrownBy(() -> new MenuProducts(products))
                .isInstanceOf(InvalidMenuProductsException.class);
    }

    @DisplayName("메뉴 상품들의 금액의 총 합을 구할 수 있다")
    @Test
    void totalPrice() {
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(ProductId.generate(), 1, 10_000),
                new MenuProduct(ProductId.generate(), 2, 20_000)
        );

        Price total = menuProducts.totalPrice();

        assertThat(total).isEqualTo(new Price(50_000));
    }


    @DisplayName("특정 상품이 포함됐는지 여부를 알 수 있다")
    @Test
    void containsProduct(){
        ProductId id = ProductId.generate();

        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(id, 1, 10_000)
        );

        boolean isContainId = menuProducts.containsProduct(id);

        assertThat(isContainId).isTrue();
    }

    @DisplayName("메뉴에 있는 상품의 key 리스트를 조회할 수 있다")
    @Test
    void productIds(){
        ProductId id1 = ProductId.generate();
        ProductId id2 = ProductId.generate();
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(id1, 1, 10_000),
                new MenuProduct(id2, 2, 20_000)
        );

        List<ProductId> productIds = menuProducts.productIds();

        assertThat(productIds).containsExactly(id1, id2);
    }
    
    @DisplayName("메뉴를 구성하는 상품의 수가 맞는지 확인할 수 있다")
    @Test
    void isSizeMismatch(){
        MenuProducts menuProducts = new MenuProducts(
                new MenuProduct(ProductId.generate(), 1, 10_000),
                new MenuProduct(ProductId.generate(), 2, 20_000)
        );

        assertThat(menuProducts.isSizeMismatch(2)).isFalse();
        assertThat(menuProducts.isSizeMismatch(3)).isTrue();
    }
}

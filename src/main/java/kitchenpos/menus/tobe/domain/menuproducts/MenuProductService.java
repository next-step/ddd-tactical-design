package kitchenpos.menus.tobe.domain.menuproducts;

import kitchenpos.products.tobe.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class MenuProductService {
    public void validateMenuProducts(final List<Product> products, final List<MenuProduct> menuProducts) {
        if (Objects.isNull(menuProducts) || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴는 반드시 한개 이상의 상품으로 구성되어야 합니다.");
        }
        if (products.size() != menuProducts.size()) {
            throw new IllegalArgumentException("상품 목록과 주문 상품 목록의 크기는 일치해야 합니다.");
        }
    }
}

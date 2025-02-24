package kitchenpos.product.tobe.domain;

import kitchenpos.menu.tobe.domain.menu.Menu;
import kitchenpos.menu.tobe.domain.menu.MenuProduct;
import kitchenpos.menu.tobe.domain.menu.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
public class ProductPriceValidator implements ProductValidator {
    private final MenuRepository menuRepository;

    public ProductPriceValidator(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Transactional
    @Override
    public void validate(UUID productId, Long price) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        menus.forEach(menu -> {
            // 메뉴에 포함된 모든 제품의 총 가격 계산
            Long totalProductsPrice = calculateTotalProductsPrice(menu.getMenuProducts(), productId, price);

            // 메뉴 가격이 업데이트된 상품 가격의 합과 관계를 확인하여 표시 여부 결정
            // 메뉴의 도메인 정책에 따라 각 도메인 객체의 메서드 사용
            if (menu.getMenuPrice() > totalProductsPrice) {
                menu.hide();
            }
        });
    }

    private Long calculateTotalProductsPrice(List<MenuProduct> menuProducts, UUID updatedProductId, Long updatedPrice) {
        return menuProducts.stream()
                .mapToLong(menuProduct -> {
                    if (menuProduct.getProductId().equals(updatedProductId)) {
                        // 업데이트되는 상품이면 새 가격 적용
                        return updatedPrice * menuProduct.getQuantity();
                    } else {
                        // 다른 상품이면 기존 가격 사용
                        return menuProduct.totalPrice();
                    }
                })
                .sum();
    }
}
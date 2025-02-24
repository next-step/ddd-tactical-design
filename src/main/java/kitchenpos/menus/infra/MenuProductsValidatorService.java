package kitchenpos.menus.infra;

import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.MenuProductsValidator;
import kitchenpos.menus.tobe.domain.exception.InvalidMenuProductsException;
import kitchenpos.products.tobe.domain.ProductId;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/*
메뉴 상품을 검증하는 도메인 서비스

메뉴 상품의 product Id의 수와 product 테이블에 있는 product Id의 수가
동일하지 않으면 유효하지 않은 메뉴 상품으로 간주한다

ProductRepository를 사용하나 메뉴 상품의 검증 즉 메뉴에서만 쓰이기 때문에
메뉴 관련 구현체를 관리하는 menus.infra에 파일 생성하였다
*/

@Component
public class MenuProductsValidatorService implements MenuProductsValidator {

    private final ProductRepository productRepository;

    public MenuProductsValidatorService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void validate(MenuProducts menuProduct) {
        final List<ProductId> productIds = menuProduct.productIds();
        int productSize = productRepository.findAllByIdIn(productIds).size();

        if (menuProduct.isSizeMismatch(productSize)) {
            throw new InvalidMenuProductsException();
        }
    }
}

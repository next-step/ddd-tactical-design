package kitchenpos.menus.tobe.domain.menu;

import java.util.List;
import java.util.NoSuchElementException;
import kitchenpos.common.DomainService;
import kitchenpos.common.domain.MenuGroupId;
import kitchenpos.common.domain.ProductId;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.products.tobe.domain.ProductRepository;

@DomainService
public class MenuValidator {

    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    public MenuValidator(
        final MenuGroupRepository menuGroupRepository,
        final ProductRepository productRepository
    ) {
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    public void validateMenuGroup(final MenuGroupId menuGroupId) {
        menuGroupRepository.findById(menuGroupId)
            .orElseThrow(() -> new NoSuchElementException("메뉴는 특정 메뉴 그룹에 속해야 합니다."));
    }

    public void validateProducts(final List<ProductId> productIds) {
        final int sizeOfExpectedProducts = productIds.size();
        final int sizeOfActualProducts = productRepository.findAllByIdIn(productIds).size();
        if (sizeOfExpectedProducts != sizeOfActualProducts) {
            throw new NoSuchElementException("상품이 없으면 메뉴를 등록할 수 없습니다.");
        }
    }
}

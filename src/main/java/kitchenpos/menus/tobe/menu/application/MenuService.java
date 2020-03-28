package kitchenpos.menus.tobe.menu.application;

import kitchenpos.menus.tobe.menu.application.dto.MenuCreationRequestDto;
import kitchenpos.menus.tobe.menu.application.dto.MenuCreationResponseDto;
import kitchenpos.menus.tobe.menu.domain.Menu;
import kitchenpos.menus.tobe.menu.domain.MenuProduct;
import kitchenpos.menus.tobe.menu.domain.MenuRepository;
import kitchenpos.menus.tobe.menu.domain.Products;
import kitchenpos.menus.tobe.menuGroup.application.MenuGroupService;
import kitchenpos.menus.tobe.menuGroup.application.exception.MenuGroupNotExistsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final Products products;
    private final MenuGroupService menuGroupService;

    public MenuService(final MenuRepository menuRepository, final Products products, final MenuGroupService menuGroupService) {
        this.menuRepository = menuRepository;
        this.products = products;
        this.menuGroupService = menuGroupService;
    }

    public MenuCreationResponseDto create(final MenuCreationRequestDto menuCreationRequestDto) {

        // 제품 미지정 시 에러
        if (CollectionUtils.isEmpty(menuCreationRequestDto.getProductQuantityDtos())) {
            throw new IllegalArgumentException("메뉴 내 제품을 1개 이상 지정해야합니다.");
        }

        final List<Long> productIds = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(pq -> {
                    if (pq.getProductId() == null || pq.getProductId() < 1) {
                        throw new IllegalArgumentException("productId 가 유효하지 않습니다.");
                    }
                    return pq.getProductId();
                })
                .collect(Collectors.toList());

        final List<Long> quantities = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(pq -> {
                    if (pq.getQuantity() == null || pq.getQuantity() < 1) {
                        throw new IllegalArgumentException("quantity 는 1개 이상이여야합니다.");
                    }
                    return pq.getQuantity();
                })
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(productIds)) {
            throw new IllegalArgumentException("productIds 가 입력되지 않았습니다.");
        }

        if (CollectionUtils.isEmpty(quantities)) {
            throw new IllegalArgumentException("quantity 가 입력되지 않았습니다.");
        }

        if (productIds.size() != productIds.parallelStream().distinct().count()) {
            throw new IllegalArgumentException("제품은 중복될 수 없습니다.");
        }

        try {
            menuGroupService.findById(menuCreationRequestDto.getMenuGroupId());
        } catch (MenuGroupNotExistsException e) {
            throw new IllegalArgumentException("메뉴그룹이 존재하지 않습니다.");
        }


        // Product 정보 가져오기 (Anti-Corruption Layer)
        final List<MenuProduct> menuProducts = products.getMenuProductsByProductIdsAndQuantities(menuCreationRequestDto.getProductQuantityDtos());

        final Menu menu = new Menu(menuCreationRequestDto.getName(), menuCreationRequestDto.getPrice(), menuCreationRequestDto.getMenuGroupId(), menuProducts);

        return new MenuCreationResponseDto(menuRepository.save(menu).getId());
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}

package kitchenpos.menus.tobe.menu.application;

import kitchenpos.menus.tobe.menu.application.dto.MenuCreationRequestDto;
import kitchenpos.menus.tobe.menu.application.dto.MenuCreationResponseDto;
import kitchenpos.menus.tobe.menu.domain.Menu;
import kitchenpos.menus.tobe.menu.domain.MenuProduct;
import kitchenpos.menus.tobe.menu.domain.MenuRepository;
import kitchenpos.menus.tobe.menu.domain.Products;
import kitchenpos.menus.tobe.menuGroup.application.MenuGroupService;
import kitchenpos.menus.tobe.menuGroup.application.exception.MenuGroupNotExistsException;
import kitchenpos.products.tobe.domain.Product;
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

        try {
            menuGroupService.findById(menuCreationRequestDto.getMenuGroupId());
        } catch (MenuGroupNotExistsException e) {
            throw new IllegalArgumentException("메뉴그룹이 존재하지 않습니다.");
        }

        // 제품 미지정 시 에러
        if (CollectionUtils.isEmpty(menuCreationRequestDto.getProductQuantityDtos())) {
            throw new IllegalArgumentException("메뉴 내 제품을 1개 이상 지정해야합니다.");
        }

        // 제품 Id 리스트 생성
        final List<Long> productIds = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(p -> p.getProductId())
                .collect(Collectors.toList());

        // Product 정보 가져오기 (Anti-Corruption Layer)
        final List<Product> foundProducts = products.getProductsByProductIds(productIds);

        final List<MenuProduct> menuProducts = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(p -> {
                    final Product product = foundProducts.stream()
                            .filter(fp -> fp.getId().equals(p.getProductId()))
                            .findFirst().orElseThrow(IllegalArgumentException::new);

                    return new MenuProduct(p.getProductId(), product.getPrice(), p.getQuantity());
                })
                .collect(Collectors.toList());

        final Menu menu = new Menu(menuCreationRequestDto.getName(), menuCreationRequestDto.getPrice(), menuCreationRequestDto.getMenuGroupId(), menuProducts);

        return new MenuCreationResponseDto(menuRepository.save(menu).getId());
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}

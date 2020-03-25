package kitchenpos.menus.tobe.menu.application;

import kitchenpos.menus.tobe.menu.application.dto.MenuCreationRequestDto;
import kitchenpos.menus.tobe.menu.application.dto.MenuCreationResponseDto;
import kitchenpos.menus.tobe.menu.application.dto.ProductQuantityDto;
import kitchenpos.menus.tobe.menuGroup.application.exception.MenuGroupNotExistsException;
import kitchenpos.menus.tobe.menu.domain.*;
import kitchenpos.menus.tobe.menuGroup.application.MenuGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (menuCreationRequestDto.getProductQuantityDtos() == null || menuCreationRequestDto.getProductQuantityDtos().isEmpty()) {
            throw new IllegalArgumentException("메뉴 내 제품을 1개 이상 지정해야합니다.");
        }

        // 제품 중복 시 에러
        if (menuCreationRequestDto.getProductQuantityDtos().size() != menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(ProductQuantityDto::getProductId)
                .distinct()
                .count()) {
            throw new IllegalArgumentException("메뉴 내 제품은 중복될 수 없습니다.");
        }

        // 제품 Id 리스트 생성
        final List<Long> productIds = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(p -> p.getProductId())
                .collect(Collectors.toList());

        // 제품 수량 리스트 생성
        final List<Long> quantities = menuCreationRequestDto.getProductQuantityDtos()
                .stream()
                .map(p -> p.getQuantity())
                .collect(Collectors.toList());

        // MenuProduct 정보 가져오기 (Anti-Corruption Layer)
        final List<MenuProduct> menuProducts = products.getMenuProductsByProductIdsAndQuantities(productIds, quantities);

        final Menu newMenu = new Menu(menuCreationRequestDto.getName(), menuCreationRequestDto.getPrice(), menuCreationRequestDto.getMenuGroupId(), menuProducts);

        return new MenuCreationResponseDto(menuRepository.save(newMenu).getId());
    }

    public List<Menu> list() {
        return menuRepository.findAll();
    }
}

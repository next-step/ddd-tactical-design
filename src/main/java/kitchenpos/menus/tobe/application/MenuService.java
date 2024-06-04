package kitchenpos.menus.tobe.application;

import java.util.*;
import kitchenpos.menus.tobe.application.dto.MenuChangePriceRequestDto;
import kitchenpos.menus.tobe.application.dto.MenuCreateRequestDto;
import kitchenpos.menus.tobe.application.dto.MenuCreateResponse;
import kitchenpos.menus.tobe.application.dto.MenuProductCreateRequestDto;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuProduct;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.menus.tobe.domain.menu.ProductProviderInterface;
import kitchenpos.menus.tobe.domain.menu.PurgomalumClient;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroup;
import kitchenpos.menus.tobe.domain.menugroup.MenuGroupRepository;
import kitchenpos.menus.tobe.infra.dto.ProductConsumerDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuService {
  private final MenuRepository menuRepository;
  private final MenuGroupRepository menuGroupRepository;
  private final PurgomalumClient purgomalumClient;
  private final ProductProviderInterface productProviderInterface;

  public MenuService(
      final MenuRepository menuRepository,
      final MenuGroupRepository menuGroupRepository,
      final PurgomalumClient purgomalumClient,
      ProductProviderInterface productProviderInterface) {
    this.menuRepository = menuRepository;
    this.menuGroupRepository = menuGroupRepository;
    this.purgomalumClient = purgomalumClient;
    this.productProviderInterface = productProviderInterface;
  }

  @Transactional
  public MenuCreateResponse create(final MenuCreateRequestDto request) {
    final Menu menu =
        Menu.create(
            request.getName(),
            request.getPrice(),
            this.getMenuGroup(request),
            this.getMenuProducts(request),
            request.getDisplayed(),
            purgomalumClient);

    menuRepository.save(menu);
    return MenuCreateResponse.of(menu);
  }

  @Transactional
  public MenuCreateResponse changePrice(final MenuChangePriceRequestDto menuChangePriceRequestDto) {
    final Menu menu =
        menuRepository
            .findById(menuChangePriceRequestDto.getMenuId())
            .orElseThrow(NoSuchElementException::new);

    menu.changePrice(menuChangePriceRequestDto.getMenuPrice());
    return MenuCreateResponse.of(menu);
  }

  @Transactional
  public MenuCreateResponse display(final UUID menuId) {
    final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
    menu.displayed();
    return MenuCreateResponse.of(menu);
  }

  @Transactional
  public MenuCreateResponse hide(final UUID menuId) {
    final Menu menu = menuRepository.findById(menuId).orElseThrow(NoSuchElementException::new);
    menu.hide();
    return MenuCreateResponse.of(menu);
  }

  @Transactional(readOnly = true)
  public List<MenuCreateResponse> findAll() {
    final List<Menu> menus = menuRepository.findAll();
    return menus.stream().map(MenuCreateResponse::of).toList();
  }

  private MenuGroup getMenuGroup(MenuCreateRequestDto request) {
    return menuGroupRepository
        .findById(request.getMenuGroupId())
        .orElseThrow(() -> new NoSuchElementException("메뉴 그룹이 존재하지 않습니다."));
  }

  private List<MenuProduct> getMenuProducts(MenuCreateRequestDto request) {
    final List<ProductConsumerDto> productConsumerDtos =
        request.getMenuProductCreateRequestDtos().stream()
            .map(MenuProductCreateRequestDto::to)
            .toList();

    return productProviderInterface.findByIds(productConsumerDtos);
  }
}

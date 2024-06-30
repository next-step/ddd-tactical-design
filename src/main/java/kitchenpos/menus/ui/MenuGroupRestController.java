package kitchenpos.menus.ui;

import java.net.URI;
import java.util.List;

import kitchenpos.menus.application.dto.MenuGroupCreationResponseDto;
import kitchenpos.menus.application.MenuGroupService;
import kitchenpos.menus.application.dto.MenuCreateRequestDto;
import kitchenpos.menus.application.dto.MenuGroupsCreationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
  private final MenuGroupService menuGroupService;

  public MenuGroupRestController(final MenuGroupService menuGroupService) {
    this.menuGroupService = menuGroupService;
  }

  @PostMapping
  public ResponseEntity<MenuGroupCreationResponseDto> create(
      @RequestBody final MenuCreateRequestDto request) {
    final MenuGroupCreationResponseDto response = menuGroupService.create(request.getName());
    return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
        .body(response);
  }

  @GetMapping
  public ResponseEntity<List<MenuGroupCreationResponseDto>> findAll() {
    final MenuGroupsCreationResponseDto menuGroupsCreationResponseDto = menuGroupService.findAll();
    return ResponseEntity.ok(menuGroupsCreationResponseDto.getList());
  }
}

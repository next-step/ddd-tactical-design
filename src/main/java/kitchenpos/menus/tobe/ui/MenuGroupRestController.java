package kitchenpos.menus.tobe.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.menus.tobe.application.MenuCommandHandler;
import kitchenpos.menus.tobe.query.MenuQueryHandler;
import kitchenpos.menus.tobe.dto.MenuGroupCreateDto;
import kitchenpos.menus.tobe.ui.view.MenuGroupViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/menu-groups")
@RestController
public class MenuGroupRestController {
    private final MenuCommandHandler menuCommandHandler;
    private final MenuQueryHandler menuQueryHandler;

    public MenuGroupRestController(MenuCommandHandler menuCommandHandler, MenuQueryHandler menuQueryHandler) {
        this.menuCommandHandler = menuCommandHandler;
        this.menuQueryHandler = menuQueryHandler;
    }

    @PostMapping
    public ResponseEntity<MenuGroupViewModel> create(@RequestBody final MenuGroupCreateDto request) {
        final MenuGroupViewModel response = MenuGroupViewModel.from(menuCommandHandler.createMenuGroup(request));
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                             .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroupViewModel>> findAll() {
        List<MenuGroupViewModel> result = menuQueryHandler.findAllMenuGroup()
                                                          .stream()
                                                          .map(MenuGroupViewModel::from)
                                                          .toList();
        return ResponseEntity.ok(result);
    }
}

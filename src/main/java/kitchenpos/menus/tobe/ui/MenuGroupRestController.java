package kitchenpos.menus.tobe.ui;

import java.net.URI;
import java.util.List;
import kitchenpos.menus.tobe.application.MenuCommandHandler;
import kitchenpos.menus.tobe.application.MenuQueryHandler;
import kitchenpos.menus.tobe.domain.entity.MenuGroup;
import kitchenpos.menus.tobe.dto.MenuGroupCreateDto;
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
    public ResponseEntity<MenuGroup> create(@RequestBody final MenuGroupCreateDto request) {
        final MenuGroup response = menuCommandHandler.createMenuGroup(request);
        return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
                             .body(response);
    }

    @GetMapping
    public ResponseEntity<List<MenuGroup>> findAll() {
        return ResponseEntity.ok(menuQueryHandler.findAllMenuGroup());
    }
}

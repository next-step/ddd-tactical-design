package kitchenpos.menus.ui;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.tobe.domain.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/menus")
@RestController
public class MenuRestController {
    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }


}

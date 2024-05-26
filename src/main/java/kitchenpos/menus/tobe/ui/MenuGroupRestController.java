package kitchenpos.menus.tobe.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.menus.tobe.application.MenuGroupService;
import kitchenpos.menus.tobe.domain.MenuGroup;

@RequestMapping("/api/menu-groups")
public class MenuGroupRestController {
	private final MenuGroupService menuGroupService;

	public MenuGroupRestController(final MenuGroupService menuGroupService) {
		this.menuGroupService = menuGroupService;
	}

	@PostMapping
	public ResponseEntity<MenuGroup> create(@RequestBody final String requestName) {
		final MenuGroup response = menuGroupService.create(requestName);
		return ResponseEntity.created(URI.create("/api/menu-groups/" + response.getId()))
			.body(response);
	}

	@GetMapping
	public ResponseEntity<List<MenuGroup>> findAll() {
		return ResponseEntity.ok(menuGroupService.findAll());
	}
}

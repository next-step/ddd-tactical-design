package kitchenpos.tobe.menu.ui

import kitchenpos.tobe.menu.application.MenuServiceV2
import kitchenpos.tobe.menu.application.dto.request.ChangeMenuPriceRequest
import kitchenpos.tobe.menu.application.dto.request.CreateMenuRequest
import kitchenpos.tobe.menu.application.dto.response.ChangeMenuPriceResponse
import kitchenpos.tobe.menu.application.dto.response.CreateMenuResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RequestMapping("/api/v2/menus")
@RestController
class MenuRestControllerV2(
    private val menuServiceV2: MenuServiceV2,
) {
    @PostMapping
    fun create(
        @RequestBody request: CreateMenuRequest,
    ): ResponseEntity<CreateMenuResponse> {
        val response = menuServiceV2.createMenu(request)
        return ResponseEntity.created(
            URI.create("/api/v2/menus/${response.id}"),
        ).body(response)
    }

    @PutMapping("/{menuId}/price")
    fun changePrice(
        @PathVariable("menuId") menuId: UUID,
        @RequestBody request: ChangeMenuPriceRequest,
    ): ResponseEntity<ChangeMenuPriceResponse> {
        return ResponseEntity.ok(
            menuServiceV2.changeMenuPrice(
                menuId,
                request,
            ),
        )
    }

    @PutMapping("/{menuId}/display")
    fun display(
        @PathVariable("menuId") menuId: UUID,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(
            menuServiceV2.display(menuId),
        )
    }

    @PutMapping("/{menuId}/unDisplay")
    fun unDisplay(
        @PathVariable("menuId") menuId: UUID,
    ): ResponseEntity<UUID> {
        return ResponseEntity.ok(
            menuServiceV2.unDisplay(menuId),
        )
    }
}

package kitchenpos.products.tobe.ui;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.domain.Product;

@RequestMapping("/api/products")
@RestController(value = "TobeProductRestController")
public class ProductRestController {
	private final ProductService productService;

	public ProductRestController(final ProductService productService) {
		this.productService = productService;
	}

	@PostMapping
	public ResponseEntity<Product> create(@Valid @RequestBody final ProductCreateRequest request) {
		final Product response = productService.create(request);
		return ResponseEntity.created(URI.create("/api/products/" + response.getId()))
			.body(response);
	}

	@PutMapping("/{productId}/price")
	public ResponseEntity<Product> changePrice(
		@PathVariable final UUID productId,
		@Valid @RequestBody final ProductPriceChangeRequest request
	) {
		return ResponseEntity.ok(productService.changePrice(productId, request));
	}

	@GetMapping
	public ResponseEntity<List<Product>> findAll() {
		return ResponseEntity.ok(productService.findAll());
	}
}

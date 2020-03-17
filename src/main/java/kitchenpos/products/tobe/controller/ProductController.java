package kitchenpos.products.tobe.controller;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.dto.ProductDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private ProductService productService;

    public ProductController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ProductDto register (@RequestBody ProductDto productDto){
        return productService.register(productDto);
    }

    @GetMapping("/api/products")
    public List<ProductDto> list (){
        return productService.list();
    }
}

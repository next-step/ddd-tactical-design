package kitchenpos.products.tobe.controller;

import kitchenpos.products.tobe.application.ProductService;
import kitchenpos.products.tobe.dto.ProductDto;
import kitchenpos.products.tobe.dto.ProductRegisterDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductRestController {

    private ProductService productService;

    public ProductRestController(final ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/api/products")
    public ProductRegisterDto register (@RequestBody ProductRegisterDto productRegisterDto){
        return productService.register(productRegisterDto);
    }

    @GetMapping("/api/products")
    public List<ProductDto> list (){
        return productService.list();
    }
}

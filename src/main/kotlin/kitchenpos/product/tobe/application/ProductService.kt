package kitchenpos.product.tobe.application

import kitchenpos.common.domain.Profanities
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.application.dto.ProductResp
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Service
@Primary
class ProductService(
    private val productRepository: ProductRepository,
    private val profanities: Profanities,
) {

    fun create(request: CreateProductReq): ProductResp {
        val product = productRepository.save(
            Product(
                productName = ProductName(profanities, request.name),
                price = request.price
            )
        )
        return ProductResp.of(product)
    }

    fun findAll(): List<ProductResp> {
        return productRepository.findAll().map(ProductResp::of)
    }
}

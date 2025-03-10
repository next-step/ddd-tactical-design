package kitchenpos.product.tobe.application

import java.util.*
import kitchenpos.common.domain.Profanities
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.application.dto.ProductResp
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.domain.ProductNamePolicy
import kitchenpos.product.tobe.domain.ProductPrice
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("tobeProductService")
class ProductService(
    private val productRepository: ProductRepository,
    private val profanities: Profanities,
) {

    @Transactional
    fun create(request: CreateProductReq): ProductResp {
        val product = productRepository.save(
            Product(
                productName = ProductName(ProductNamePolicy(profanities), request.name),
                productPrice = ProductPrice(request.price)
            )
        )
        return ProductResp.of(product)
    }

    fun findAll(): List<ProductResp> {
        return productRepository.findAll().map(ProductResp::of)
    }

    @Transactional
    fun changePrice(productId: UUID, request: ChangeProductPriceReq) {
        val product =
            productRepository.findById(productId).orElseThrow { throw NoSuchElementException("상품을 찾을 수 없습니다.") }
        product.changePrice(ProductPrice(request.price))
        productRepository.save(product)
    }
}

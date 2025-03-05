package kitchenpos.product.tobe.application

import java.util.*
import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.domain.MenuProductPriceChanged
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.application.dto.ProductResp
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.domain.ProductNamePolicy
import kitchenpos.product.tobe.domain.ProductPrice
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.stereotype.Service

@Service("tobeProductService")
class ProductService(
    private val productRepository: ProductRepository,
    private val profanities: Profanities,
    private val menuProductPriceChanged: MenuProductPriceChanged,
) {

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

    fun changePrice(productId: UUID, request: ChangeProductPriceReq): ProductResp {
        val product =
            productRepository.findById(productId).orElseThrow { throw NoSuchElementException("상품을 찾을 수 없습니다.") }
        product.changePrice(ProductPrice(request.price))

        menuProductPriceChanged.changedProduct(productId)
        return ProductResp.of(productRepository.save(product))
    }
}

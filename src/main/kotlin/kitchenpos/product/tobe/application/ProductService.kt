package kitchenpos.product.tobe.application

import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.domain.MenuRepository
import kitchenpos.product.tobe.application.dto.ChangeProductPriceReq
import kitchenpos.product.tobe.application.dto.CreateProductReq
import kitchenpos.product.tobe.application.dto.ProductResp
import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductName
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val menuRepository: MenuRepository,
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

    fun changePrice(productId: UUID, request: ChangeProductPriceReq): ProductResp {
        val product = productRepository.findById(productId)
            .orElseThrow { throw NoSuchElementException("상품을 찾을 수 없습니다.") }
        product.changePrice(request.price)
        val menus = menuRepository.findAllByProductId(productId)
        menus.forEach { menu ->
            run {
                if (menu.price > menu.amount()) {
                    //TODO Menu 리팩토링 시 처리
                    menu.displayed = false
                }
            }
        }
        return ProductResp.of(productRepository.save(product))
    }
}

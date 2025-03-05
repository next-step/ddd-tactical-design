package kitchenpos.product.tobe.application

import java.util.*
import kitchenpos.common.domain.Profanities
import kitchenpos.menu.tobe.domain.MenuAmountService
import kitchenpos.menu.tobe.domain.MenuDisplay
import kitchenpos.menu.tobe.domain.MenuRepository
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
    private val menuRepository: MenuRepository,
    private val profanities: Profanities,
    private val menuAmountService: MenuAmountService,
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

        val menus = menuRepository.findAllByProductId(productId)
        menus.forEach { menu ->
            run {
                if (menu.menuPrice.price > menu.amount(menuAmountService)) {
                    //TODO Menu 리팩토링 시 처리
                    menu.menuDisplay = MenuDisplay.NOT_DISPLAYED
                }
            }
        }
        return ProductResp.of(productRepository.save(product))
    }
}

package kitchenpos.product.tobe.infra

import kitchenpos.product.tobe.domain.Product
import kitchenpos.product.tobe.domain.ProductRepository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaProductRepository : ProductRepository, JpaRepository<Product, UUID>

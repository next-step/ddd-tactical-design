package kitchenpos.menus.tobe.domain;

import java.util.Objects;

public class MenuProduct {
	private final Long seq;
	private final Product product;
	private final MenuProductCount count;


	public MenuProduct(Long seq, Product product, MenuProductCount count) {
		this.seq = seq;
		this.product = product;
		this.count = count;
	}
	
	public Price calculatePrice() {
		return product.getPrice().multiply(count.getCount());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final MenuProduct that = (MenuProduct) o;
		return Objects.equals(seq, that.seq);
	}

	@Override
	public int hashCode() {
		return Objects.hash(seq);
	}
}

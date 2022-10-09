package kitchenpos.core.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ArrayUtils {

	private ArrayUtils() {
	}

	public static <T> List<T> asList(T value, T[] values) {
		if (Objects.isNull(value))
			return Collections.emptyList();

		ArrayList<T> list = new ArrayList<>();
		list.add(value);
		list.addAll(List.of(values));
		return list;
	}
}

package kitchenpos.eatinorders.ordertable.tobe.domain.vo;

import kitchenpos.eatinorders.ordertable.tobe.domain.vo.exception.MinimumGuestOfNumbersException;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Embeddable
public class GuestOfNumbers {

    private static final int MINIMUM_VALUE = 0;
    private static final int ZERO_VALUE = 0;

    static {
        GuestOfNumbersCache.save(new GuestOfNumbers(ZERO_VALUE));
    }
    public static final GuestOfNumbers ZERO = GuestOfNumbersCache.findByNumber(ZERO_VALUE);

    @Column(name = "number_of_guests", nullable = false)
    private int value;

    protected GuestOfNumbers() {
    }

    private GuestOfNumbers(final int value) {
        this.value = value;
    }

    public static GuestOfNumbers valueOf(final int value) {
        if (value < MINIMUM_VALUE) {
            throw new MinimumGuestOfNumbersException(value);
        }
        final GuestOfNumbers guestOfNumbers = GuestOfNumbersCache.findByNumber(value);
        if (guestOfNumbers != null) {
            return guestOfNumbers;
        }
        return GuestOfNumbersCache.save(new GuestOfNumbers(value));
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GuestOfNumbers that = (GuestOfNumbers) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    private static class GuestOfNumbersCache {

        private static final Map<Integer, GuestOfNumbers> guestOfNumbersCache = new HashMap<>();

        private static GuestOfNumbers save(final GuestOfNumbers guestOfNumbers) {
            guestOfNumbersCache.put(guestOfNumbers.value, guestOfNumbers);
            return guestOfNumbers;
        }

        private static GuestOfNumbers findByNumber(int value) {
            return guestOfNumbersCache.get(value);
        }
    }
}

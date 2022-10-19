package kitchenpos.common.name;

/**
 * <h1>이름</h1>
 *
 * @see NameFactory
 */
public class Name {

    public final String value;

    public Name(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Name name = (Name) o;

        return value.equals(name.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

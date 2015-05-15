import java.lang.Iterable;
import java.util.Iterator;
import java.util.function.BiFunction;

public interface List<T> extends Iterable<T> {
	class Cons<T> implements List<T> {
		private final T car;
		private final List<T> cdr;
		private Cons(List<T> cdr, T car) {
			this.car = Contract.requiresNonNull(car, "car");
			this.cdr = Contract.requiresNonNull(cdr, "cdr");
		}
		@Override public boolean isEmpty() { return false; }
		@Override public T car() { return car; }
		@Override public List<T> cdr() { return cdr; }
	}
	class Empty<T> implements List<T> {
		private Empty() {}
		@SuppressWarnings("rawtypes") private static final List INSTANCE = new Empty();
	}

	@SuppressWarnings("unchecked") default List<T> nil() { return Empty.INSTANCE; }
	default boolean isEmpty() { return true; }
	default T car() { throw new UnsupportedOperationException(); }
	default List<T> cdr() { throw new UnsupportedOperationException(); }
	default List<T> cons(T value) { return new Cons<T>(this, value); }
	default Iterator<T> iterator() {
		List<T> _this = this;
		return new Iterator<T>() {
			List<T> it = _this;
			public boolean hasNext() { return ! it.isEmpty(); }
			public T next() {
				T next = it.car();
				it = it.cdr();
				return next;
			}
			public void remove() { throw new UnsupportedOperationException(); }
		};
	}
	default List<T> reverse() {
		return new BiFunction<List<T>, List<T>, List<T>>() {
			public List<T> apply(List<T> xs, List<T> bs)
				{ return xs.isEmpty() ? bs : this.apply(xs.cdr(), bs.cons(xs.car())); }
		}.apply(this, nil());
	}
	@SuppressWarnings("unchecked")
	static <T> List<T> from(T... xs) {
		List<T> l = Empty.INSTANCE;
		for (int i = (xs.length - 1); i >= 0; i--)
			l = l.cons(xs[i]);
		return l;
	}
}

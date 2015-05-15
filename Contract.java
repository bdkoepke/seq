public class Contract {
	public static <T> T requiresNonNull(T t, String name) {
		if (t == null)
			throw new IllegalArgumentException(name);
		return t;
	}
}

public class Program {
	public static void main(String... args) {
		for (int v : List.from(0, 1, 2, 3, 4, 5).reverse())
			System.out.println(v);
	}
}

import java.awt.Rectangle;


public class CollisionTest {
	public static void main(String[] args) {
		Rectangle a = new Rectangle(50, 411, 32, 64);
		Rectangle b = new Rectangle(10, 444, 2000, 10);
		System.out
		.println(a + " " + 
				(a.intersects(b) ? "intersects"
						: "doesn't intersect")
				+ " with " + b);
	}
}

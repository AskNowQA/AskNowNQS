package test;

public class replaceWords {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String name = "height (μ) vice president";
		System.out.println(name.replaceAll("[^a-zA-Z0-9\\ ]", ""));

	}

}

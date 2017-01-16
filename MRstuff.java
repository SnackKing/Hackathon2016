package example;

public final class MRstuff {
	
	public static double cosine(double theta) {
		double answer = 0;
		if (theta >= 0) {
			answer = Math.cos(theta*(Math.PI/180));
		}
		else {
			answer = -Math.cos(theta*(Math.PI/180));
		}
		return answer;
	}
	
	public static double sine(double theta) {
		double answer = 0;
		if (theta >= 0) {
			answer = Math.sin(theta*(Math.PI/180));
		}
		else {
			answer = -Math.sin(theta*(Math.PI/180));
		}
		return answer;
	}
}

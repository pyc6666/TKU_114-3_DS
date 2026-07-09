public class BmiMethod {
  public static void main(String[] args) {
    double height = 1.65;
    double weight = 55;

    double bmi = calculateBmi(height, weight);
    String level = getBmiLevel(bmi);

    System.out.println("BMI: " + bmi);
    System.out.println("Level: " + level);
  }

  public static double calculateBmi(double height, double weight) {
    return weight / (height * height);
  }

  public static String getBmiLevel(double bmi) {
    if (bmi < 18.5) {
      return "Underweight";
    } else if (bmi < 24) {
      return "Normal";
    } else if (bmi < 27) {
      return "Overweight";
    } else {
      return "Obese";
    }
  }
}

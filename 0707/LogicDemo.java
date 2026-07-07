public class LogicDemo {
  public static void main(String[] args) {
    int score = 75;
    int attendance = 90;

    boolean passCourse = score >= 60 && attendance >= 80;

    System.out.println("Score: " + score);
    System.out.println("Attendance: " + attendance);
    System.out.println("Pass course: " + passCourse);
  }
}

public class MenuDemo {
  public static void main(String[] args) {
    int option = 2;

    switch (option) {
      case 1:
        System.out.println("Show BMI");
        break;
      case 2:
        System.out.println("Show score average");
        break;
      case 3:
        System.out.println("Show grade level");
        break;
      default:
        System.out.println("Unknown option");
    }
  }
}

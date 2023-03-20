import view.Login;
import view.SelectScreen;

public class StartProgram {
    public static void main(String[] args) {
        SelectScreen startProgram = new SelectScreen();
        new Login(startProgram);
        while (true) {
            if (!startProgram.isDisplayable()) {
                System.gc();
                startProgram = new SelectScreen();
                new Login(startProgram);
            }
        }
    }
}

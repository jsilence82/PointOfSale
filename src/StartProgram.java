import view.Login;
import view.SelectScreen;
import view.custom.BlankBackground;


public class StartProgram {
    public static void main(String[] args)  {
        new BlankBackground();
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

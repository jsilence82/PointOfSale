import view.Login;
import view.SelectScreen;

import java.io.IOException;

public class StartProgram {
    public static void main(String[] args) throws IOException {
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

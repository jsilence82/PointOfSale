package view;


import java.awt.*;

public enum SystemColors {

    BACKGROUND(new Color(0xdbf0ff)),
    BUTTONS(new Color(0x0d99ff)),
    WARENCONTAINER(new Color(0xf8f8f8)),
    BLACKBG(new Color(0x1e1e1e)),
    SCHRIFTHELL(new Color(0xffffff)),
    SCHRIFTDUNKEL(new Color(0x000000)),
    XBUTTON(new Color(0xb8010e)),
    INFOCONTAINER(new Color(0x0d99ff));

    final Color colorCode;

    SystemColors(Color colorCode) {
        this.colorCode = colorCode;
    }

    public Color getColorCode() {
        return this.colorCode;
    }
}

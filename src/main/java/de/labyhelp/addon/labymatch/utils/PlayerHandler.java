package de.labyhelp.addon.labymatch.utils;

import de.labyhelp.addon.LabyHelp;
import net.labymod.main.LabyMod;

import java.util.UUID;

public class PlayerHandler {

    public void sendAge(final UUID uuid, String age) {
     LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendAge.php?name=" + age + "&uuid=" + uuid.toString());
    }

    public void sendGender(final UUID uuid, String gender) {
        LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendGender.php?name=" + gender + "&uuid=" + uuid.toString());
    }

    public void sendIntress(final UUID uuid, String intress) {
       LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendIntress.php?name=" + intress + "&uuid=" + uuid.toString());
    }

    public void quitMatch() {
        LabyHelp.getInstance().getRequestManager().sendRequest("https://marvhuelsmann.de/sendMatchOffline.php?uuid=" + LabyMod.getInstance().getPlayerUUID().toString());
    }


}

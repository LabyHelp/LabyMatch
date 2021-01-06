package de.labyhelp.addon.labymatch.listener;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.labymatch.LabyMatch;
import net.labymod.utils.ServerData;

import java.util.function.Consumer;

public class ClientJoinListener implements Consumer<ServerData>, net.labymod.utils.Consumer<ServerData> {

    int i = 1;

    @Override
    public void accept(ServerData serverData) {
        LabyMatch.getInstace().getSettingsManager().onServer = true;
        if (LabyMatch.getInstace().getSettingsManager().joinMessage) {
            String message = LabyHelp.getInstance().getTranslationManager().getTranslation("labymatch.adversting");

            switch (i) {
                case 2:
                    LabyMatch.getInstace().sendClientMessage(message);
                    break;
                case 5:
                case 9:
                case 12:
                    LabyMatch.getInstace().sendClientMessage(message);
                    LabyHelp.getInstance().sendTranslMessage("labymatch.turnoff");;
                    break;
            }

            i++;
        }
    }
}
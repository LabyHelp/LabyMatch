package de.marvhuelsmann.labymatch.listener;

import de.marvhuelsmann.labymatch.LabyMatch;
import net.labymod.utils.ServerData;

import java.util.function.Consumer;

public class ClientJoinListener implements Consumer<ServerData>, net.labymod.utils.Consumer<ServerData> {

    int i = 1;

    @Override
    public void accept(ServerData serverData) {
        LabyMatch.getInstace().getSettingsManager().onServer = true;
        if (LabyMatch.getInstace().getSettingsManager().joinMessage) {
            String message = "With /labymatch you can find a game partner! With the new LabyMatch addon";

            switch (i) {
                case 2:
                    LabyMatch.getInstace().sendClientMessage(message);
                    break;
                case 5:
                case 9:
                case 12:
                    LabyMatch.getInstace().sendClientMessage(message);
                    LabyMatch.getInstace().sendClientMessage("You can turn off these messages in the settings");
                    break;
            }

            i++;
        }
    }
}
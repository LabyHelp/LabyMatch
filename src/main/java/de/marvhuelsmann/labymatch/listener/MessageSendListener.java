package de.marvhuelsmann.labymatch.listener;

import de.marvhuelsmann.labymatch.LabyMatch;
import net.labymod.api.events.MessageSendEvent;

public class MessageSendListener implements MessageSendEvent {

    @Override
    public boolean onSend(String s) {

        if (s.startsWith("/labymatch")) {
            if (LabyMatch.getInstace().getSettingsManager().age != null && LabyMatch.getInstace().getSettingsManager().intress != null && LabyMatch.getInstace().getSettingsManager().gender != null) {
                LabyMatch.getInstace().sendClientMessage("§cSearching...");
                LabyMatch.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        LabyMatch.getInstace().getMatchManager().sendMatch();
                        LabyMatch.getInstace().getMatchManager().readMatch();
                    }
                });
            } else {
                LabyMatch.getInstace().sendClientMessage("You first have to go to the LabyHelp settings and complete your profile!");
            }

            return true;
        } else if (s.startsWith("/lhhelp")) {

            LabyMatch.getInstace().sendClientMessage("/labymatch");

            return true;
        }

        return false;
    }
}

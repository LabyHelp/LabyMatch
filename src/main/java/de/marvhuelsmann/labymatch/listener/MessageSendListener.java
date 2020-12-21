package de.marvhuelsmann.labymatch.listener;

import de.marvhuelsmann.labymatch.LabyMatch;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;

public class MessageSendListener implements MessageSendEvent {

    @Override
    public boolean onSend(String s) {

        if (s.startsWith("/labymatch")) {
            if (!LabyMatch.getInstace().getSettingsManager().age.equals("") || !LabyMatch.getInstace().getSettingsManager().intress.equals("") || !LabyMatch.getInstace().getSettingsManager().gender.equals("")) {
                LabyMatch.getInstace().sendClientMessage("§cSearching...");
                LabyMatch.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        LabyMatch.getInstace().sendClientMessage("§c...");

                        LabyMatch.getInstace().getPlayerHandler().quitMatch(LabyMod.getInstance().getPlayerUUID());

                        LabyMatch.getInstace().getSettingsManager().isQuering = true;
                        LabyMatch.getInstace().getSettingsManager().backup = false;
                        LabyMatch.getInstace().getSettingsManager().isAlready = false;

                        LabyMatch.getInstace().getMatchManager().sendMatch();
                        LabyMatch.getInstace().getMatchManager().sendQuery();
                        LabyMatch.getInstace().getMatchManager().readMatch(true);
                    }
                });
            } else {
                LabyMatch.getInstace().sendClientMessage("You first have to go to the LabyHelp settings and complete your profile!");
            }

            return true;
        } else if (s.startsWith("/lhhelp")) {

            LabyMatch.getInstace().sendClientMessage("/labymatch");

            return true;
        } else if (s.startsWith("/labymr")) {
            LabyMatch.getInstace().getMatchManager().sendQuery();
            LabyMatch.getInstace().getMatchManager().readMatch(true);
            LabyMatch.getInstace().sendClientMessage("Reload!");
        }

        return false;
    }
}

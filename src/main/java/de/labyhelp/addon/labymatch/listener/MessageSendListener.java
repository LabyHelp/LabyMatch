package de.labyhelp.addon.labymatch.listener;

import de.labyhelp.addon.LabyHelp;
import de.labyhelp.addon.labymatch.LabyMatch;
import net.labymod.api.events.MessageSendEvent;
import net.labymod.main.LabyMod;

public class MessageSendListener implements MessageSendEvent {

    @Override
    public boolean onSend(String s) {

        if (s.startsWith("/labymatch")) {
            if (!LabyMatch.getInstace().getSettingsManager().age.equals("") || !LabyMatch.getInstace().getSettingsManager().intress.equals("") || !LabyMatch.getInstace().getSettingsManager().gender.equals("")) {
                LabyHelp.getInstance().sendTranslMessage("labymatch.searching");

                LabyMatch.getInstace().getExecutor().submit(new Runnable() {
                    @Override
                    public void run() {
                        LabyMatch.getInstace().sendClientMessage("...");

                        LabyMatch.getInstace().getPlayerHandler().quitMatch();

                        LabyMatch.getInstace().getSettingsManager().isQuering = true;
                        LabyMatch.getInstace().getSettingsManager().backup = false;
                        LabyMatch.getInstace().getSettingsManager().isAlready = false;

                        LabyMatch.getInstace().getMatchManager().sendMatch();
                        LabyMatch.getInstace().getMatchManager().sendQuery();
                        LabyMatch.getInstace().getMatchManager().readMatch(true);
                    }
                });
            } else {
                LabyHelp.getInstance().sendTranslMessage("labymatch.settings");
            }

            return true;
        } else if (s.startsWith("/lhhelp")) {
            LabyMatch.getInstace().sendClientMessage("/labymatch");

            return true;
        } else if (s.startsWith("/labymr")) {
            if (LabyMatch.getInstace().getSettingsManager().isQuering) {
                LabyMatch.getInstace().getMatchManager().sendQuery();
                LabyMatch.getInstace().getMatchManager().readMatch(true);
                LabyHelp.getInstance().sendTranslMessage("labymatch.reload");
                return true;
            }
        }

        return false;
    }
}

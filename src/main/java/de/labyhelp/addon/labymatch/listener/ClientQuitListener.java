package de.labyhelp.addon.labymatch.listener;

import de.labyhelp.addon.labymatch.LabyMatch;
import net.labymod.utils.ServerData;

import java.util.function.Consumer;

public class ClientQuitListener implements Consumer<ServerData>, net.labymod.utils.Consumer<ServerData> {
    @Override
    public void accept(ServerData serverData) {
        LabyMatch.getInstace().getSettingsManager().onServer = false;
    }
}

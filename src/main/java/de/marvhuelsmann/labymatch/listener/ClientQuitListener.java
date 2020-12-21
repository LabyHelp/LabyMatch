package de.marvhuelsmann.labymatch.listener;

import de.marvhuelsmann.labymatch.LabyMatch;
import net.labymod.utils.ServerData;

import java.util.function.Consumer;

public class ClientQuitListener implements Consumer<ServerData>, net.labymod.utils.Consumer<ServerData> {
    @Override
    public void accept(ServerData serverData) {
        LabyMatch.getInstace().getSettingsManager().onServer = false;
    }
}

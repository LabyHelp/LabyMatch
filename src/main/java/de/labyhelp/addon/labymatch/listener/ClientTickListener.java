package de.labyhelp.addon.labymatch.listener;

import de.labyhelp.addon.labymatch.LabyMatch;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickListener {

    private int tick = 0;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {

        if (LabyMatch.getInstace().getSettingsManager().serverResponding) {
            if (tick > 600) {
                try {
                    if (LabyMatch.getInstace().getSettingsManager().getIsQuery()) {
                        if (!LabyMatch.getInstace().getSettingsManager().backup) {
                            LabyMatch.getInstace().getExecutor().submit(new Runnable() {
                                @Override
                                public void run() {
                                    LabyMatch.getInstace().getMatchManager().readMatch(true);
                                }
                            });
                        }
                    }
                } catch (Exception ignored) {
                    LabyMatch.getInstace().getSettingsManager().backup = true;
                }
                tick = 0;
            }

            tick++;
        }
    }
}

package de.marvhuelsmann.labymatch.listener;

import de.marvhuelsmann.labymatch.LabyMatch;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTickListener {

    private int tick = 0;
    boolean backup = false;

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {

        if (LabyMatch.getInstace().getSettingsManager().serverResponding) {
            if (tick > 990) {
                try {
                    if (LabyMatch.getInstace().getSettingsManager().getIsQuery()) {
                        if (!backup) {
                            LabyMatch.getInstace().getExecutor().submit(new Runnable() {
                                @Override
                                public void run() {
                                    LabyMatch.getInstace().getMatchManager().sendMatch();
                                    LabyMatch.getInstace().getMatchManager().readMatch();
                                }
                            });
                        }
                    }
                } catch (Exception ignored) {
                    backup = true;
                }
                tick = 0;
            }

            tick++;
        }
    }
}

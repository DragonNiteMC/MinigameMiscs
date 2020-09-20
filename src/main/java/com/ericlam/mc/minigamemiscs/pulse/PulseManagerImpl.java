package com.ericlam.mc.minigamemiscs.pulse;

import com.ericlam.mc.minigamemiscs.api.PulseManager;
import com.ericlam.mc.minigamemiscs.config.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class PulseManagerImpl implements PulseManager {

    private final MainConfig.PulseSetting pulseSetting;
    private final Plugin plugin;
    private final Map<Player, PulseRunnable> currentTasks = new ConcurrentHashMap<>();

    public PulseManagerImpl(MainConfig.PulseSetting pulseSetting, Plugin plugin) {
        this.pulseSetting = pulseSetting;
        this.plugin = plugin;
    }

    @Override
    public void startPulse(Player player, int time) {
        stopPulse(player);
        var runnable = new PulseRunnable(plugin, pulseSetting, player, time, this.currentTasks::remove);
        currentTasks.put(player, runnable);
        runnable.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void startPulse(Player player) {
        this.startPulse(player, -1);
    }

    @Override
    public void stopPulse(Player player) {
        Optional.ofNullable(currentTasks.remove(player)).ifPresent(t -> {
            if (!t.isCancelled()) t.cancel();
        });
    }

    @Override
    public boolean isPulsing(Player player) {
        return currentTasks.containsKey(player);
    }
}

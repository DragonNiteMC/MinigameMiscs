package com.ericlam.mc.minigamemiscs.api;

import org.bukkit.entity.Player;

public interface PulseManager {

    void startPulse(Player player, int time);

    void startPulse(Player player);

    void stopPulse(Player player);

    boolean isPulsing(Player player);

}

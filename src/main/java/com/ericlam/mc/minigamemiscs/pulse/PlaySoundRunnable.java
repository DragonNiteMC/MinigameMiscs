package com.ericlam.mc.minigamemiscs.pulse;

import com.ericlam.mc.minigamemiscs.config.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaySoundRunnable extends BukkitRunnable {

    private final Player player;
    private final MainConfig.PulseSetting.PulseSound sound;

    public PlaySoundRunnable(Player player, MainConfig.PulseSetting.PulseSound sound) {
        this.player = player;
        this.sound = sound;
    }

    @Override
    public void run() {
        player.playSound(player.getLocation(), sound.name, sound.volume, sound.pitch);
    }
}

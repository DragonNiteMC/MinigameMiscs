package com.ericlam.mc.minigamemiscs.pulse;

import com.ericlam.mc.minigamemiscs.config.MainConfig;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.function.Consumer;

public class PulseRunnable extends BukkitRunnable {

    private final Plugin plugin;
    private final MainConfig.PulseSetting setting;
    private final Player player;
    private final Consumer<Player> afterRun;
    private long time;
    private boolean fastBeatEnabled = false;
    private BukkitTask firstTask;
    private BukkitTask secondTask;

    public PulseRunnable(Plugin plugin, MainConfig.PulseSetting setting, Player player, int time, Consumer<Player> afterRun) {
        this.plugin = plugin;
        this.setting = setting;
        this.player = player;
        this.afterRun = afterRun;
        this.time = time;
        reassignSoundsRunnable();
    }

    private void reassignSoundsRunnable() {
        this.cancelSoundsRunnable();
        var frequency = (int) (fastBeatEnabled ? setting.ticks_frequency * 0.6 : setting.ticks_frequency);
        this.firstTask = new PlaySoundRunnable(player, setting.sound_first).runTaskTimer(plugin, 0L, frequency);
        this.secondTask = new PlaySoundRunnable(player, setting.sound_second).runTaskTimer(plugin, setting.ticks_between_sounds, frequency);
    }

    private void cancelSoundsRunnable(){
        if (this.firstTask != null && !this.firstTask.isCancelled()) {
            this.firstTask.cancel();
        }
        if (this.secondTask != null && !this.secondTask.isCancelled()) {
            this.secondTask.cancel();
        }
    }

    @Override
    public void run() {
        if (!player.isOnline()) {
            this.cancel();
            return;
        }
        final var originalFastBeatState = this.fastBeatEnabled;
        if (time > -1) {
            if (time == 0) cancel();
            else time--;
        } else {

            if (player.getHealth() <= setting.fast_beat) {
                this.fastBeatEnabled = true;
            } else if (player.getHealth() > setting.fast_beat) {
                this.fastBeatEnabled = false;
            }

            if (player.getHealth() >= setting.launch_health) {
                cancel();
            }else if (originalFastBeatState != this.fastBeatEnabled){
                reassignSoundsRunnable();
            }

        }
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        super.cancel();
        this.cancelSoundsRunnable();
        afterRun.accept(player);
    }
}

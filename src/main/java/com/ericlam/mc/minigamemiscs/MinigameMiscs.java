package com.ericlam.mc.minigamemiscs;

import com.ericlam.mc.minigamemiscs.api.MinigameMiscApi;
import com.ericlam.mc.minigamemiscs.api.PulseManager;
import com.ericlam.mc.minigamemiscs.api.TintManager;
import com.ericlam.mc.minigamemiscs.config.MainConfig;
import com.ericlam.mc.minigamemiscs.pulse.PulseListener;
import com.ericlam.mc.minigamemiscs.pulse.PulseManagerImpl;
import com.ericlam.mc.minigamemiscs.tint.TintListener;
import com.ericlam.mc.minigamemiscs.tint.TintManagerImpl;
import com.hypernite.mc.hnmc.core.main.HyperNiteMC;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameMiscs extends JavaPlugin implements MinigameMiscApi {

    private static MinigameMiscApi api;

    public static MinigameMiscApi getApi() {
        return api;
    }

    private PulseManager pulseManager;
    private TintManager tintManager;

    @Override
    public void onEnable() {
        api = this;
        var yamlManager = HyperNiteMC.getAPI().getFactory().getConfigFactory(this)
                .register(MainConfig.class)
                .dump();
        var mainConfig = yamlManager.getConfigAs(MainConfig.class);
        this.pulseManager = new PulseManagerImpl(mainConfig.pulse, this);
        this.tintManager = new TintManagerImpl(mainConfig.tint);

        if (!mainConfig.tint.api_mode_only) {
            getServer().getPluginManager().registerEvents(new TintListener(mainConfig.tint, tintManager, mainConfig.disabled_worlds), this);
        }
        if (!mainConfig.pulse.api_mode_only){
            getServer().getPluginManager().registerEvents(new PulseListener(mainConfig.disabled_worlds, mainConfig.pulse, pulseManager), this);
        }
    }

    @Override
    public PulseManager getPulseManager() {
        return pulseManager;
    }

    @Override
    public TintManager getTintManager() {
        return tintManager;
    }
}

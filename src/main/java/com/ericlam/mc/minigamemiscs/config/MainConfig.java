package com.ericlam.mc.minigamemiscs.config;

import com.dragonite.mc.dnmc.core.config.yaml.Configuration;
import com.dragonite.mc.dnmc.core.config.yaml.Resource;

import java.util.List;

@Resource(locate = "config.yml")
public class MainConfig extends Configuration {

    public TintSetting tint;
    public PulseSetting pulse;
    public List<String> disabled_worlds;


    public static class TintSetting {
        public boolean api_mode_only;
        public boolean fade_enabled;
        public int fade_time;
        public int intensity_modifier;
        public MiscMode mode;
        public double launch_health;
    }

    public enum MiscMode {
        HEALTH, DAMAGE
    }

    public static class PulseSetting {
        public boolean api_mode_only;
        public MiscMode mode;
        public int damage_duration;
        public PulseSound sound_first;
        public PulseSound sound_second;
        public double launch_health;
        public double fast_beat;
        public int ticks_between_sounds;
        public int ticks_frequency;


        public static class PulseSound {
            public String name;
            public float volume;
            public float pitch;
        }
    }
}

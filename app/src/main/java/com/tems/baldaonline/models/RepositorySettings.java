package com.tems.baldaonline.models;

import com.tems.baldaonline.domain.Mascot;
import com.tems.baldaonline.domain.Settings;

public interface RepositorySettings {
    void getMascot(int id, SettingsRepository.OnMascotReadyListener onMascotReadyListener);
    void getSettingsGameOneOnOne(SettingsRepository.OnSettingsReadyListener onSettingsReadyListener);
    void saveMascot(Mascot mascot);
    void saveSettingsGameOneOnOne(Settings settings);
}

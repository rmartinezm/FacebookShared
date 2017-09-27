package com.rmartinezm.unammobile.facebookshared;


import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

public class MiConfiguracion {

    Permission[] permisions = new Permission[]{Permission.EMAIL, Permission.PUBLISH_ACTION};
    static final String APP_ID="143351079613847";

    public SimpleFacebookConfiguration getMyConfigs(){
        SimpleFacebookConfiguration configs = new SimpleFacebookConfiguration.Builder()
                .setAppId(APP_ID)
                .setNamespace("publishfeed")
                .setPermissions(permisions)
                .build();
        return configs;
    }

}

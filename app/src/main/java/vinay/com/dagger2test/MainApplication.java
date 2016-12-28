package vinay.com.dagger2test;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

public class MainApplication extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .appModule(new AppModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }

    @VisibleForTesting
    void setComponent(ApplicationComponent component) {
        this.component = component;
    }
}

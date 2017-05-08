package krystian.drawacard;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupImageLoader();
    }

    private void setupImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);
    }
}

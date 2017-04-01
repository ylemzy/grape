package application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by huangzebin on 2017/2/22.
 */

@Component
public class AppProperties {

    @Value("${data.dir}")
    public String parseDataDirectory;

    @Value("${batch.size}")
    public int esBatchSize;

    @Value("${image.file.temp.dir}")
    public String fileTempDirectory;

    @Value("${news.newest.limit}")
    public int newsMostNewestLimit;

    @Deprecated
    public String uploadImageApi;

    @Deprecated
    public String imageLoginApi;

    @Value("${image.api}")
    public String imageApi;

    static AppProperties appProperties;

    public AppProperties(){
        AppProperties.appProperties = this;
    }

    public static AppProperties getAppProperties(){
        return AppProperties.appProperties;
    }
}

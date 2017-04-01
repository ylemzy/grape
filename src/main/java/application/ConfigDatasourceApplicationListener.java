package application;
/**
 * Created by huangzebin on 2017/3/28.
 */

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.PropertiesPropertySource;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class ConfigDatasourceApplicationListener implements ApplicationListener<ApplicationEvent> {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ApplicationEnvironmentPreparedEvent) {
            this.onApplicationEnvironmentPreparedEvent((ApplicationEnvironmentPreparedEvent)event);
        }
    }

    private void onApplicationEnvironmentPreparedEvent(ApplicationEnvironmentPreparedEvent event) {
        String property = event.getEnvironment().getProperty("additional.datasource.location");
        if (StringUtils.isEmpty(property))
            return;

        Properties properties = new Properties();
        try {
            properties.load(FileUtils.openInputStream(new File(property)));

            PropertiesPropertySource propertySource = new PropertiesPropertySource("datasource", properties);
            event.getEnvironment().getPropertySources().addLast(propertySource);
        } catch (IOException e) {
            logger.error("load settings error:", e);
        }
    }
}

package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by huangzebin on 2017/2/8.
 */
@SpringBootApplication
@EnableScheduling
public class Application extends SpringApplication{

    private static final Logger logger = LogManager.getLogger();

    public Application(Object... sources) {
        super(sources);
    }

    public static void main(String[] args) {
        Application springApplication = new Application(new Object[]{Application.class});
        springApplication.addListeners(new ConfigDatasourceApplicationListener());
        springApplication.run(args);
    }
}

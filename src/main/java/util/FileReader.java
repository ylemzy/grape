package util;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/8.
 */
public class FileReader {

    private static final Logger logger = LogManager.getLogger();



    public static List<String> getAllFiles(String path) {
        logger.info("Get file name from path {}", path);

        List<String> filePath = new ArrayList<String>();
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()){
                filePath.add(file.getAbsolutePath());
            }

        }
        return filePath;
    }
}

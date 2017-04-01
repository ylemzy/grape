package application.service;/**
 * Created by huangzebin on 2017/3/21.
 */

import application.AppProperties;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@Service
public class StorageService {
    private static final Logger logger = LogManager.getLogger();


    @Autowired
    AppProperties appProperties;

    public Resource loadAsResource(String fileName){

        return null;
    }


    public HttpServletResponse writeImageToResponse(String bucket, String filename, HttpServletResponse httpResponse) throws IOException {
        String path = appProperties.fileTempDirectory + "/" + bucket + "/" + filename;
        logger.info("Get image:{}", path);
        File file = new File(path);
        byte[] bytes = FileUtils.readFileToByteArray(file);
        httpResponse.getOutputStream().write(bytes);
        httpResponse.getOutputStream().flush();
        httpResponse.getOutputStream().close();
        httpResponse.setHeader(HttpHeaders.CONTENT_TYPE, "image/*");
        return httpResponse;
    }


}

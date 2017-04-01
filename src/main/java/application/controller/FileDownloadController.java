package application.controller;/**
 * Created by huangzebin on 2017/3/21.
 */

import application.service.StorageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.ImageDownloadUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FileDownloadController {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    StorageService storageService;

    @GetMapping("/image/{bucket}/{file:.+}")
    @ResponseBody
    public void serveFile(@PathVariable String bucket, @PathVariable String file, HttpServletResponse response) {
        try {
            storageService.writeImageToResponse(bucket, file, response);
        } catch (IOException e) {
            logger.error(e, e);
        }
    }

    @RequestMapping(value = "/image/news", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity downloadNewsImage(@RequestBody String imgUrl){
        String bucket = "news";
        try {
            String url = ImageDownloadUtil.downloadToSystemFile(imgUrl, bucket, 240, 216);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            logger.error(e, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

package util;/**
 * Created by huangzebin on 2017/3/21.
 */

import application.AppProperties;
import application.elastic.NewsDocument;
import application.elastic.repository.NewsRepository;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ImageDownloadUtil {
    private static final Logger logger = LogManager.getLogger();

    public static void scale(InputStream inputStream, File file, int width, int height) throws IOException {
        BufferedImage srcImage = ImageIO.read(inputStream);
        BufferedImage rescaled = Scalr.resize(srcImage, width, height);
        int i = file.getName().lastIndexOf(".") + 1;
        ImageIO.write(rescaled, file.getName().substring(i), file);
    }

    public static boolean isValidHttpUrl(String imgUrl){
        if (!StringUtils.isEmpty(imgUrl) && imgUrl.startsWith("http://"))
            return true;
        return false;
    }

    public static String makeImageFileNameByUrl(String imgUrl, String bucketDir){
        int i = imgUrl.lastIndexOf("/");
        String fileName = imgUrl.substring(i);
        return bucketDir + "/" + fileName;
    }

    public static String checkMakeBucketDir(String bucket) throws IOException {
        AppProperties appProperties = AppProperties.getAppProperties();
        String prefixDir = null;
        if (appProperties == null){
            prefixDir = "D:/file.temp";
        }else{
            prefixDir = appProperties.fileTempDirectory;
        }
        String bucketDir = prefixDir + "/" + bucket;
        FileUtils.forceMkdir(new File(bucketDir));
        return bucketDir;
    }

    public static String downloadToSystemFile(String imgUrl, String bucket, int dstWidth, int dstHeight) throws Exception {
        if (!isValidHttpUrl(imgUrl)){
            return null;
        }
        HttpGet httpget = new HttpGet(imgUrl);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget);

        try {
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() >= 400) {
                throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode()
                        + " imageUrl: " + imgUrl);
            }
            if (entity != null) {
                InputStream input = entity.getContent();
                String bucketDir = checkMakeBucketDir(bucket);
                File dst = new File(makeImageFileNameByUrl(imgUrl, bucketDir));

                if (!dst.exists()){
                    scale(input, dst, dstWidth, dstHeight);
                }else{
                    logger.warn("{} is exists", dst);
                }

                String api;
                if (AppProperties.getAppProperties() == null){
                    api = "";
                }else{
                    api = AppProperties.getAppProperties().imageApi;
                }
                String newImageUrl = api + "/" + bucket + "/" + dst.getName();
                logger.info("Save image to {}, imgUrl={}", dst, newImageUrl);
                return newImageUrl;
            }
        } finally {
            response.close();
        }
        return null;
    }

    static class ImageDownload implements Runnable{

        Collection<NewsDocument> documents;


        NewsRepository newsRepository;
        public ImageDownload(Collection<NewsDocument> documents, NewsRepository newsRepository){
            this.documents = documents;
            this.newsRepository = newsRepository;
        }

        @Override
        public void run() {
            documents.forEach(doc->{
                try {
                    String localUrl = ImageDownloadUtil.downloadToSystemFile(doc.getFirstImg(), "news", 240, 216);
                    if (StringUtils.isNotEmpty(localUrl)){
                        doc.setHtbxThumbnail(localUrl);
                    }
                } catch (Exception e) {
                    logger.error(e, e);
                }
            });
            newsRepository.save(documents);
            logger.info("Finish ImageDownload");
        }
    }

    public static void runDownload(Collection<NewsDocument> documents, NewsRepository newsRepository){
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ImageDownload imageDownload = new ImageDownload(documents, newsRepository);
        executorService.execute(imageDownload);
        logger.info("shutdown executorService");
        executorService.shutdown();//delay showdown way
    }
}

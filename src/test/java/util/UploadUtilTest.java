package util;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by huangzebin on 2017/3/10.
 */
public class UploadUtilTest {

    private static final Logger logger = LogManager.getLogger();

    String imgUrl = "https://secure-ds.serving-sys.com/BurstingRes/Site-105657/Type-0/a5a68773-87a5-40a3-97ec-18ede0173f07.jpg";

    String gifImgUrl = "http://ubmcmm.baidustatic.com/media/v1/0f0005UlpKZORlP21PDpa6.gif";
    @Test
    public void login() throws IOException {
        //System.out.println("res = " + UploadUtil.login());

        Document post =
                Jsoup.connect("http://106.75.130.86:7008/adminht/au/uploadPhoto?type=1&picType=1&picName=spider_1").post();

        System.out.println(post.html());
    }

    @Test
    public void testUpload() {

        /*String s = null;
        try {
            s = UploadUtil.uploadImage(gifImgUrl);
        } catch (Exception e) {
            logger.error(e, e);
        }
        System.out.println("RESPONSE:" + s);*/
    }


    @Test
    public void testImg() throws IOException {
        HttpGet httpget = new HttpGet(imgUrl);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget);
    }


    @Test
    public void testScale(){
        try {
            File src = new File("D:\\file.temp\\gACo-fxpfhzq2561683.jpg");
            File dest = new File("D:\\file.temp\\scale.jpg");
            BufferedImage srcImage = ImageIO.read(src);
            BufferedImage rescaled = Scalr.resize(srcImage, 600, 500);
            ImageIO.write(rescaled, "jpg", dest);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
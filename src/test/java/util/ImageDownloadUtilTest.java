package util;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by huangzebin on 2017/3/21.
 */
public class ImageDownloadUtilTest {
    @Test
    public void downloadToSystemFile() throws Exception {

        String imgUrl = "http://n.sinaimg.cn/default/20160104/gawi-fxneefs5532129.png";
        ImageDownloadUtil.downloadToSystemFile(imgUrl, "test", 300, 300);
    }


    @Test
    public void testScale() throws IOException {
        File file = new File("D:\\file.temp\\test\\gawi-fxneefs5532129.png");
        FileInputStream fileInputStream = FileUtils.openInputStream(file);

        BufferedImage srcImage = ImageIO.read(fileInputStream);
        //BufferedImage rescaled = Scalr.resize(srcImage, width, height);

        File dst = new File("D:\\file.temp\\test\\gawi-fxneefs5532129_new.png");
        ImageIO.write(srcImage, "png", dst);
        ImageDownloadUtil.scale(fileInputStream, dst, 300, 300);
    }

}
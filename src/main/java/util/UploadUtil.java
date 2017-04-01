package util;/**
 * Created by huangzebin on 2017/3/10.
 */

import application.AppProperties;
import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

@Deprecated
public class UploadUtil {
    private static final Logger logger = LogManager.getLogger();


    public static String login() throws IOException {
        String loginApi = AppProperties.getAppProperties().imageLoginApi;
        HttpGet httpget = new HttpGet(loginApi);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            if (response.getStatusLine().getStatusCode() >= 400) {
                throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode()
                        + " imageUrl: " + loginApi);
            }
            HeaderElement headerElement = response.getHeaders("Set-Cookie")[0].getElements()[0];
            return JsonHelper.toJSON(headerElement.getName() + "=" + headerElement.getValue());

        } finally {
            response.close();
            //return null;
        }

    }

    public static String uploadFile(File file, String picName) throws IOException {

        String session = login();

        String uploadUrl = AppProperties.getAppProperties().uploadImageApi + picName;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httppost = new HttpPost(uploadUrl);
            httppost.setHeader("Cookie", session);
            FileBody fileInputStream = new FileBody(file);
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("imgFile", fileInputStream)
                    .build();

            httppost.setEntity(reqEntity);

            logger.info("executing request " + httppost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httppost);
            try {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    logger.info("Response content length: " + resEntity.getContentLength());
                }
                String s = EntityUtils.toString(resEntity).replace("_M_S", "");
                return s;
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e, e);
        } catch (IOException e) {
            logger.error(e, e);
        } finally {
            httpclient.close();
        }
        return null;
    }


    public static String makeImageFileNameByUrl(String imgUrl){
        int i = imgUrl.lastIndexOf("/");
        String fileName = imgUrl.substring(i);
        AppProperties appProperties = AppProperties.getAppProperties();
        return appProperties.fileTempDirectory + "/" + fileName;
    }

    public static void saveData(InputStream input, File file) throws IOException {

        OutputStream output = new FileOutputStream(file);
        IOUtils.copy(input, output);
        output.flush();
    }

}

package application.service;


import application.AppProperties;
import application.elastic.Device;
import application.elastic.UserActionDocument;
import application.elastic.repository.EsUserActionRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.FileReader;
import util.PatternUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by huangzebin on 2017/2/9.
 */
@Service
public class UserActionService {
    private static final Logger logger = LogManager.getLogger();
    static final String ip_seperator = " - - ";
    static final String url_prefix = "http://";
    static final String url_prefix2 = "GET ";
    static final String useragent_prefix = "Mozilla/";
    static final String openid_filter = "openid";
    static final String iphone = "(iPhone;";

    @Autowired
    AppProperties appProperties;

    @Autowired
    EsUserActionRepository esUserActionRepository;

    public List<String> parseFilesByDirectory(String dir){
        List<String> allFiles = FileReader.getAllFiles(dir);
        for (String file : allFiles) {

            logger.info("Parse file {}", file);
            parseFile(file);
        }

        return allFiles;
    }

    public UserActionDocument parse(final String content) throws Exception {

        if (!content.contains(openid_filter)){
            return null;
        }

        //IP
        String[] split = content.split(ip_seperator);
        UserActionDocument action = new UserActionDocument();
        action.setIp(split[0]);

        //url
        String[] splitBlank = split[1].split(" ");
        String url = null;
        for (String s : splitBlank) {
            if (s.contains(openid_filter)){
                url = s.replace("\"", "");
                break;
            }
        }

        String[] urlFormat = url.split("[?]");
        action.setUlr(urlFormat[0]);

        //parameters
        Map<String, String> parameters = getParameters(urlFormat[1]);
        action.setParameters(parameters);
        action.setOpenid(parameters.get(openid_filter));

        //useragent
        int i = split[1].lastIndexOf("\"");
        int i1 = split[1].substring(0, i).lastIndexOf("\"");
        String useragent = split[1].substring(i1 + 1, i);

        action.setUseragent(useragent);

        if (Strings.isNullOrEmpty(action.getOpenid())){
            return null;// 数据来源有误
        }

        //device
        Device device = parseDevice(useragent);
        action.setDevice(device);

        //netType
        action.setNetType(parsePattern(useragent, "NetType/"));

        action.setMicroMessenger(parsePattern(useragent, "MicroMessenger/"));

        return action;
    }



    Device parseDevice(String text){

        Device device = new Device();
        Matcher matcher = PatternUtil.matchParenthesis(text);

        while (matcher.find()) {
            String info = matcher.group(0);
            if (info.contains("(iPhone;")){
                device.setPhone("iPhone");
                device.setSystem(info);
                break;
            }

            if (info.contains("(Linux; Android")){
                device.setPhone("Android");
                device.setSystem(info);
                break;
            }

            if (info.contains("(compatible;")){
                device.setPhone("compatible");
                device.setSystem(info);
                break;
            }

            if (info.contains("(Windows NT")){
                device.setPhone("Windows NT");
                device.setSystem(info);
                break;
            }

            if (info.contains("(Windows NT")){
                device.setPhone("Windows NT");
                device.setSystem(info);
                break;
            }

            if (info.contains("(iPad;")){
                device.setPhone("iPad");
                device.setSystem(info);
                break;
            }

            if (info.contains("(Linux; U; Android")){
                device.setPhone("Linux; U; Android");
                device.setSystem(info);
                break;
            }

            if (info.contains("(iPod touch;")){
                device.setPhone("iPod touch");
                device.setSystem(info);
                break;
            }

        }

        device.setMobile(parsePattern(text, "Mobile/"));
        return device;
    }


    String parsePattern(String text, String pattern){
        int i = text.indexOf(pattern);
        if (i != -1){
            return text.split(pattern)[1].split(" ")[0];
        }
        return null;
    }

    Map<String, String> getParameters(String paramUrl){

        Map<String, String> map = new HashMap<String, String>();

        String[] params = paramUrl.split("&");
        for (String param : params) {
            String[] split = param.split("=");
            if (split.length == 2){
                map.put(split[0], split[1]);
            }else if (split.length == 1){
                map.put(split[0], null);
            }
        }

        return map;
    }


    public void parseFile(String filename) {

        int batchSize = appProperties.esBatchSize;

        try {
            FileInputStream in = new FileInputStream(filename);
            InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
            BufferedReader bufReader = new BufferedReader(inReader);
            String line = null;

            List<UserActionDocument> data = new ArrayList();
            while ((line = bufReader.readLine()) != null) {
                if (data.size() >= batchSize){

                    esUserActionRepository.update(data);
                    data.clear();
                }

                try{
                    UserActionDocument parse = parse(line);
                    if (parse != null){
                        data.add(parse);
                    }
                }catch (Exception e){
                    logger.error("parse content = {} failed", line);
                }
            }

            if (data.size() > 0){
                esUserActionRepository.update(data);
            }

            bufReader.close();
            inReader.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("read file error");
        }
    }
}

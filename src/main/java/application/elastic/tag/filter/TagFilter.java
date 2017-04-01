package application.elastic.tag.filter;

import application.elastic.*;
import application.elastic.tag.Tag;
import application.elastic.tag.TagType;
import org.elasticsearch.common.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/24.
 */
public class TagFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagFilter.class);
    static final private String DAY_G = "天";

    static final private String YEAR_G = "岁";
    static final private String YEAR_GG = "周岁";

    static final private String DAY_AAA = "天以上";
    static final private String DAY_BBB = "天以下";

    static final private String YEAR_AAA = "岁以上";
    static final private String YEAR_AAAA = "周岁以上";

    static final private String YEAR_BBB = "岁以下";
    static final private String YEAR_BBBB = "周岁以下";

    public static Tag parseAgeLimit(String ageLimit){

        try{
            Tag ageTag = build(TagType.ENSURE_AGE).put("limit", ageLimit);
            if (ageLimit.contains("-")){
                String[] split = ageLimit.split("-");
                Integer min = ageToDay(split[0]);
                Integer max = ageToDay(split[1]);
                if (min == null && max == null){
                    ageTag.put("min", Integer.valueOf(split[0]));
                    ageTag.put("max", Integer.valueOf(split[1]));
                }else if (min == null){//unknown type, decide by second value
                    if (max > 365){
                        ageTag.put("min", 365 * Integer.valueOf(split[0]));
                    }else{
                        ageTag.put("min", Integer.valueOf(split[0]));
                    }
                    ageTag.put("max", max);
                }else{
                    ageTag.put("min", min);
                    ageTag.put("max", max);
                }

            }else{
                Integer min = ageToDayUpon(ageLimit);

                if (min != null){
                    ageTag.put("min", min);
                }else{
                    ageTag.put("max", ageToDayIn(ageLimit));
                }
            }

            if (ageTag.getData().get("min") == null){
                ageTag.getData().put("min", 0);
            }

            if (ageTag.getData().get("max") == null){
                ageTag.getData().put("max", 0);
            }
            return ageTag;
        }catch (Exception e){
            LOGGER.error(e.getMessage() + "with age limit attribute = {}", ageLimit);
        }
        return null;
    }
/*
* if (age.contains(dayA)){
            return Integer.valueOf(age.replace(dayA, ""));
        }else


        else if (age.contains(ageA)){
            return 365 * Integer.valueOf(age.replace(ageA, ""));
        }
* */
    public static Integer ageToDayUpon(String age){
        if (age.contains(YEAR_AAAA)){
            return 365 * Integer.valueOf(age.replace(YEAR_AAAA, ""));
        } else if(age.contains(YEAR_AAA)){
            return 365 * Integer.valueOf(age.replace(YEAR_AAA, ""));
        } else if(age.contains(DAY_AAA)){
            return Integer.valueOf(age.replace(DAY_AAA, ""));
        }else{
            return null;//unknown type
        }
    }

    public static Integer ageToDayIn(String age){
        if (age.contains(YEAR_BBBB)){
            return 365 * Integer.valueOf(age.replace(YEAR_BBBB, ""));
        } else if(age.contains(YEAR_BBB)){
            return 365 * Integer.valueOf(age.replace(YEAR_BBB, ""));
        } else if(age.contains(DAY_BBB)){
            return Integer.valueOf(age.replace(DAY_BBB, ""));
        }else{
            return null;//unknown type
        }
    }

    public static Integer ageToDay(String age){
        if (age.contains(YEAR_GG)){
            return 365 * Integer.valueOf(age.replace(YEAR_GG, ""));
        }else if (age.contains(YEAR_G)){
            return 365 * Integer.valueOf(age.replace(YEAR_G, ""));
        }else if (age.contains(DAY_G)){
            return Integer.valueOf(age.replace(DAY_G, ""));
        }else{
            return null;//unknown type
        }
    }


    public static List<Tag> parseAttri3(String attribute3){
        if (Strings.isNullOrEmpty(attribute3))
            return null;

        List<Tag> tags = new ArrayList<>();
        String[] attributes = attribute3.split(",");
        if (attributes.length >= 5){
            int i = attributes.length - 1;
            tags.add(parseAgeLimit(attributes[i]));
            tags.add(build(TagType.ENSURE_MONEY).put("money", Integer.valueOf(attributes[i-1])));
            tags.add(build(TagType.ENSURE_TIME).put("time", Integer.valueOf(attributes[i-2])));
            tags.add(build(TagType.ENSURE_STOCK).put("stock", Integer.valueOf(attributes[i-3])));
            tags.add(build(TagType.ENSURE_FEE).put("fee", Integer.valueOf(attributes[i-4])));

            if (attributes.length > 5){
                String temp = "";
                for (int j = 0; j < i - 4; ++j){
                    temp += attributes[j];
                }
                tags.add(build(TagType.ENSURE_CUSTOM).put("custom", temp));
            }
        }
        return tags;
    }

    public static TagSkuDocument filterSku(SkuDetailDocument skuDetailDocument, TagSkuDocument res){
        if (res == null) {
            res = new TagSkuDocument();
            res.setProductId(skuDetailDocument.getProductId());
            res.setSkuId(skuDetailDocument.getSkuId());
        }


        res.addTag(parseAttri3(skuDetailDocument.getSku3()));

        InsureProductDocument insureProductDocument = skuDetailDocument.getInsureProductDocument();
        if (insureProductDocument != null){
            res.addTag(build(TagType.CATEGORY).put("id", insureProductDocument.getCatId()).put("name", ""));
            res.addTag(build(TagType.PC_SLOGAN).put("slogan", insureProductDocument.getPcSlogan()));
            res.addTag(build(TagType.PC_GOOD_POINT).put("goodPoint", insureProductDocument.getPcGoodPoint()));
            res.addTag(build(TagType.SEO).put("seo", insureProductDocument.getSeoKey()));
            res.addTag(build(TagType.TAG).put("tag", insureProductDocument.getTag()));
            res.addTag(build(TagType.PRODUCT_NAME).put("name", insureProductDocument.getProductName()));
            res.addTag(build(TagType.BUY_VENDOR).put("name", insureProductDocument.getBuyVendor()));
        }

        return res;
    }

    public static Object noEmptyValue(Object value){
        if (value != null){
            return value.toString();
        }
        return "";
    }

    public static Object noEmptyIntValue(Object value){
        if (value != null){
            return value;
        }
        return 0;
    }

    public static TagUserDocument filterUser(UserSkusDocument userSkusDocument, TagUserDocument res){
        if (res == null){
            res = new TagUserDocument();
            res.setUserId(userSkusDocument.getOpenId());
        }

        res.addTag(build(TagType.DEVICE).put("device", userSkusDocument.getDevice()));
        res.addTag(build(TagType.URL).put("url", userSkusDocument.getUrl()));
        res.addTag(build(TagType.ACTION_PARAM).put("actionParam", userSkusDocument.getParameters()));


        UserOrderBaseDocument userOrderBaseDocument = userSkusDocument.getUserOrderBaseDocument();
        res.addTag(build(TagType.GENDER).put("gender", userOrderBaseDocument.getGender()));
        res.addTag(build(TagType.AGE).put("selfAge", userOrderBaseDocument.getAge()));
        res.addTag(build(TagType.JOB).put("job", userOrderBaseDocument.getJob()));
        res.addTag(build(TagType.PHONE).put("phone", userOrderBaseDocument.getCustPhone()));
        res.addTag(build(TagType.REG_SOURCE).put("regSource", userOrderBaseDocument.getRegSource()));
        res.addTag(build(TagType.REG_SOURCE).put("regSource", userOrderBaseDocument.getRegSource()));



        //order
        List<Tag> tags = new ArrayList<>();
        userOrderBaseDocument.getOrderBase().forEach(o->{
            tags.add(build(TagType.ORDER_GENDER).put("gender", o.getGender()));
            tags.add(build(TagType.ORDER_TAX_TITLE).put("taxTitle", o.getTaxTitle()));
            tags.add(build(TagType.ORDER_AGE).put("age", o.getAge()));
            tags.add(build(TagType.ORDER_START_TIME).put("startTime", o.getStartTime()));
            tags.add(build(TagType.ORDER_END_TIME).put("endTime", o.getEndTime()));
        });

        userSkusDocument.getSkuDetailDocuments().forEach(d->{
            tags.addAll(parseAttri3(d.getSku3()));
            tags.add(build(TagType.ORDER_VENDOR).put("buyVendor", d.getInsureProductDocument().getBuyVendor()));
            tags.add(build(TagType.ORDER_PRODUCT_NAME).put("productName", d.getInsureProductDocument().getProductName()));
            tags.add(build(TagType.ORDER_SEO).put("seo", d.getInsureProductDocument().getSeoKey()));

        });

        res.addTag(tags);
        return res;

    }

    public static Tag build(TagType type){
        Tag tag = new Tag(type);
        return tag;
    }

}

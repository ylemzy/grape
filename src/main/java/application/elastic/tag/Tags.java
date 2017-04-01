package application.elastic.tag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangzebin on 2017/2/23.
 */
public class Tags{

    List<Tag> tags = new ArrayList<>();

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag){
        if (!tags.contains(tag))
            tags.add(tag);
    }

    public void addTag(List<Tag> list){
        list.forEach(t-> addTag(t));
    }
}

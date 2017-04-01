package application.elastic.tag;

import util.JsonHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangzebin on 2017/2/23.
 */
public class Tag {

    TagType type;

    int level;

    Map<String, Object> data;

    public Tag(){}

    public Tag(TagType type) {
        this.type = type;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public TagType getType() {
        return type;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Tag put(String key, Object object){
        if (data == null)
            data = new HashMap<>();
        data.put(key, object);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (level != tag.level) return false;
        if (type != tag.type) return false;
        return data != null ? JsonHelper.toJSON(data).equals(JsonHelper.toJSON(tag.data)) : tag.data == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + level;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}

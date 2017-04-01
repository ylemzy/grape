package util;/**
 * Created by huangzebin on 2017/3/22.
 */

import com.github.pagehelper.PageInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageUtil {
    private static final Logger logger = LogManager.getLogger();

    //转化默认第一页为1开始，PageRequest 是从0开始
    public static Pageable pageRequest(int pageNum, int pageSize){
        return new PageRequest(pageNum - 1, pageSize);
    }

    public static<T> PageInfo<T> toPageInfo(Page<T> page){
        PageInfo<T> pageInfo = new PageInfo<>();
        pageInfo.setList(page.getContent());
        pageInfo.setPageNum(page.getNumber() + 1);
        pageInfo.setPageSize(page.getSize());
        pageInfo.setPages(page.getTotalPages());
        pageInfo.setTotal(page.getTotalElements());
        pageInfo.setHasNextPage(page.hasNext());
        pageInfo.setHasPreviousPage(page.hasPrevious());
        return pageInfo;
    }
}

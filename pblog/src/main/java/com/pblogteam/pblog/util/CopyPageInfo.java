package com.pblogteam.pblog.util;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

public class CopyPageInfo {
    public static<A, F> PageInfo<A> covertPageInfo(List<A> aim, List<F> from) {
        PageInfo<A> ret = new PageInfo<>(aim);
        PageInfo<F> result = new PageInfo<>(from);
        copyPageInfo(ret, result);
        return ret;
    }

    private static void copyPageInfo(PageInfo aim, PageInfo from) {
        aim.setEndRow(from.getEndRow());
        aim.setHasNextPage(from.isHasNextPage());
        aim.setIsFirstPage(from.isIsFirstPage());
        aim.setHasPreviousPage(from.isHasPreviousPage());
        aim.setIsLastPage(from.isIsLastPage());
        aim.setNavigateFirstPage(from.getNavigateFirstPage());
        aim.setNavigateLastPage(from.getNavigateLastPage());
        aim.setNavigatepageNums(from.getNavigatepageNums());
        aim.setNavigatePages(from.getNavigatePages());
        aim.setNextPage(from.getNextPage());
        aim.setPageNum(from.getPageNum());
        aim.setPages(from.getPages());
        aim.setPageSize(from.getPageSize());
        aim.setStartRow(from.getStartRow());
        aim.setTotal(from.getTotal());
        aim.setPrePage(from.getPrePage());
        aim.setSize(from.getSize());
    }
}

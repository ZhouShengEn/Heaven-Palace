package com.heaven.palace.moonpalace.page;

import java.io.Serializable;

/**
 * 分页对象
 * 
 * @author ZhouShengEn
 * @date 2022年8月25日
 */
public class Pager implements Serializable {

    private static final long serialVersionUID = -4230276624909394905L;
    /**
     * 分页大小
     */
    private int               pageSize         = 10;
    /**
     * 当前页数
     */
    private int               page             = 1;
    /**
     * 当前页数-和page同理,主要是和页面统一
     */
    private int               currentPage      = 1;

    public int getCurrentPage() {
        return page > currentPage ? page : currentPage;
    }

    public int getPage() {
        return page > currentPage ? page : currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getRow() {
        return ((page > currentPage ? page : currentPage) - 1) * pageSize;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setCurrentStrPage(String currentPage) {
        this.currentPage = (currentPage == null || currentPage.equals("")) ? 1
            : Integer.valueOf(currentPage);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPage(String page) {
        this.page = (page == null || page.equals("")) ? 1 : Integer.valueOf(page);
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

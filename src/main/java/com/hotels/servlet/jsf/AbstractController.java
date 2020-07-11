package com.hotels.servlet.jsf;

import com.hotels.domain.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.faces.component.UICommand;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractController<T extends AbstractEntity<Long>> implements Serializable {

    private static final Logger LOG  = LogManager.getLogger();
    private static final Boolean DEBUG_TEMPORARY = false;

    public static final int DEFAULT_ROWS_PER_PAGE = 10;

    private long totalRows;
    private int rowsPerPage = DEFAULT_ROWS_PER_PAGE;
    private int totalPages;
    private int currentPage;
    private List<T> dataList;
    private List<Integer> pages=new ArrayList<>();

    protected abstract List<T> findAll(int currentPage, int rowsPerPage) throws Exception;

    protected abstract long getCounts() throws Exception;

    public List<T> getDataList() throws Exception {
        if (this.dataList==null)
            loadDataList();
        return this.dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    private void loadDataList() {
        try {
            this.dataList = findAll(currentPage, rowsPerPage);
            this.totalRows = getCounts();
            this.totalPages = Long.valueOf(totalRows % rowsPerPage == 0 ? totalRows/rowsPerPage : totalRows/rowsPerPage+1).intValue();
            this.pages.clear();
            for (int i=0; i<totalPages;i++)
                this.pages.add(i);
        }
        catch (Exception e) {
            LOG.error(e);
        }
    }

    public void page(int page) {
        if (page<0 || page >= totalPages)
            return;

        currentPage = page;
        loadDataList();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void pageFirst() {
        page(0);
    }

    public void pagePrev() {
        if (currentPage>0)
            page(currentPage-1);
    }

    public void pageNext() {
        if (currentPage<totalPages)
            page(currentPage+1);
    }

    public void pageLast() {
        page(totalPages-1);
    }

    public void page(ActionEvent event) {
        page(((Integer) ((UICommand) event.getComponent()).getValue()));
    }

    protected String toUrl(String url, boolean redirect) {
        return String.format("/%s?faces-redirect=%s", url, redirect?"true":"false");
    }
}

package com.brandon.services.boards.models;

import com.brandon.utils.BoardPageable;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Naver on 2016-07-26.
 */
public class BoardPageableImpl<T> extends PageImpl implements Serializable, BoardPageable {

    private static final long serialVersionUID = -7654012116614019833L;
    private long total;
    private int pageSize;
    private int pageNumber;
    private int pagePerGroupSize;
    private String searchKeyword;
    private List<T> contents;

    public BoardPageableImpl(List<T> contents, long total) {
        super(contents);
        this.total = total;
        this.pageNumber = 1;
        this.pageSize = 25;
        this.pagePerGroupSize = 10;
    }

    public BoardPageableImpl(List<T> contents, long total, int pageSize, int pageNumber, int pagePerGroupSize, String searchKeyword) {
        super(contents);
        this.contents = contents;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNumber = pageNumber;
        this.pagePerGroupSize = pagePerGroupSize;
        this.searchKeyword = searchKeyword;
    }

    @Override
    public int getPreviousBlockPage() {
        return getStartBlockPage() - 1 <= 1 ? 1 : getStartBlockPage() - 1;
    }

    @Override
    public int getNextBlockPage() {
        return getEndBlockPage() + 1 > getTotalPages() ? getTotalPages() : getEndBlockPage() + 1;
    }

    @Override
    public int getStartBlockPage() {
        return ((getCurrentBlock() - 1) * getPagePerGroupSize()) + 1;
    }

    @Override
    public int getEndBlockPage() {
        return (getStartBlockPage() + getPagePerGroupSize() - 1) > getTotalPages() ? getTotalPages() : (getStartBlockPage() + getPagePerGroupSize() - 1);
    }

    private int getCurrentBlock() {
        return (int) Math.floor((double) (getNumber() - 1) / getPagePerGroupSize()) + 1;
    }

    @Override
    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPagePerGroupSize() {
        return pagePerGroupSize;
    }

    public void setPagePerGroupSize(int pagePerGroupSize) {
        this.pagePerGroupSize = pagePerGroupSize;
    }

    @Override
    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public List<T> getContents() {
        return contents;
    }

    public void setContents(List<T> contents) {
        this.contents = contents;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BoardPageableImpl<?> that = (BoardPageableImpl<?>) o;
        return total == that.total &&
                pageSize == that.pageSize &&
                pageNumber == that.pageNumber &&
                pagePerGroupSize == that.pagePerGroupSize &&
                Objects.equals(searchKeyword, that.searchKeyword) &&
                Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), total, pageSize, pageNumber, pagePerGroupSize, searchKeyword, contents);
    }
}

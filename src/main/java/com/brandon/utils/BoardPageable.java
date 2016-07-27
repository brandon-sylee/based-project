package com.brandon.utils;

/**
 * Created by Naver on 2016-07-26.
 */
public interface BoardPageable {
    /**
     * 이전 페이지 블럭
     *
     * @return
     */
    int getPreviousBlockPage();

    /**
     * 다음 페이지 블럭
     *
     * @return
     */
    int getNextBlockPage();

    /**
     * 시작 블럭
     *
     * @return
     */
    int getStartBlockPage();

    /**
     * 마지막 블럭
     *
     * @return
     */
    int getEndBlockPage();

    /**
     * 전체 게시물 수
     *
     * @return
     */
    long getTotal();

    /**
     * 현재 페이지
     *
     * @return
     */
    int getPageNumber();

    /**
     * 검색어
     *
     * @return
     */
    String getSearchKeyword();
}

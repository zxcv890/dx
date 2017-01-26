package com.mytools;

import java.util.List;

/**
 * Created by dx on 2017/1/3.
 */
public class MyPage {
    /**
     * 分页
     * @param page 页码
     * @param rows 每页行数
     * @param total 总条数
     * @return
     */
    public static String pagination(int page, int rows, long total) {
        StringBuffer str = new StringBuffer("");
        try {
            int start = page - 4;
            str.append("<a href=\"javascript:getPage(" + 1+ ");\" class=\"prevPage\">首页</a>");
            if (page==1) {
                str.append("<span class=\"disabled\">上一页</span>");
            }
            if (page > 1) {
                str.append("<a href=\"javascript:getPage(" + (page - 1)+ ");\" class=\"prevPage\">上一页</a>");
            }
            while ((page + 4) >= start && start < page) {
                if (start > 0) {
                    str.append("<a href=\"javascript:getPage("+start+");\" class=\"tcdNumber\">"+start+"</a>");
                }
                start++;
            }
            str.append("<span class=\"current\">"+page+"</span>");
            if (total - rows * page > 0						) {
                str.append("<span style=\"font-weight:900\">. . .</span>");
                str.append("<a href=\"javascript:getPage(" + (page + 1)+ ");\" class=\"prevPage\">下一页</a>");
            } else {
                str.append("<span class=\"disabled\">下一页</span>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.toString();
    }
//    /**
//     * 分页
//     * @param page 页码
//     * @param rows 每页行数
//     * @return 当前页数的集合
//     * @author SuKF
//     */
//    public static List<Record> pageing(int page, int rows, String sql, List<Object> para)throws Exception{
//        List<Record> list = new ArrayList<Record>();
//        int pageNo = page;
//        if( pageNo == 1 ){
//            pageNo = 0;
//        }else{
//            pageNo = (pageNo-1) * rows;
//        }
//        sql += "  limit ?, "+rows;
//        para.add(pageNo);
//        list = Db.find(sql, para.toArray());
//        return list;
//    }
//    /**
//     * 下一页是否有数据
//     * @param page
//     * @param sql
//     * @param para
//     * @return 返回是否
//     * @author SuKF
//     */
//    public static boolean isNextPage(int page, int rows, String sql, List<Object> para){
//        int pageNo = page + 1;
//        if( pageNo == 1 ){
//            pageNo = 0;
//        }else{
//            pageNo = (pageNo-1) * rows;
//        }
//        sql += "  limit ?,1 ";
//        para.add( pageNo );
//        return Db.findFirst(sql, para.toArray()) != null;
//    }
//
//    /**
//     * 分页
//     * @param page 页码
//     * @param rows 每页行数
//     * @param con  当前的controller
//     * @param listName 传到页面集合的名称
//     * @param htmlName 传到页面分页html的名称
//     * @return 返回是否分页成功生成
//     * @author SuKF
//     */
//    public static boolean setPagination(Controller con, String listName,String htmlName, int page, int rows, String sql, List<Object> para) {
//        StringBuffer str = new StringBuffer("");
//        try {
//            int start = page - 4;
//            str.append("<a href=\"javascript:getPage(" + 1+ ");\" class=\"prevPage\">首页</a>");
//            if (page==1) {
//                str.append("<span class=\"disabled\">上一页</span>");
//            }
//            if (page > 1) {
//                str.append("<a href=\"javascript:getPage(" + (page - 1)+ ");\" class=\"prevPage\">上一页</a>");
//            }
//            while ((page + 4) >= start && start < page) {
//                if (start > 0) {
//                    str.append("<a href=\"javascript:getPage("+start+");\" class=\"tcdNumber\">"+start+"</a>");
//                }
//                start++;
//            }
//            str.append("<span class=\"current\">"+page+"</span>");
//            if ( isNextPage(page, rows, sql, para) ) {
//                str.append("<span style=\"font-weight:900\">. . .</span>");
//                str.append("<a href=\"javascript:getPage(" + (page + 1)+ ");\" class=\"prevPage\">下一页</a>");
//            } else {
//                str.append("<span class=\"disabled\">下一页</span>");
//            }
//            para.remove(para.size()-1);
//            con.setAttr(listName, pageing(page, rows, sql, para));
//            con.setAttr(htmlName, str);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return true;
//    }
}

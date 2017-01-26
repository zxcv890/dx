package com.mytools;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dx on 2017/1/3.
 */
public class MyTools {
//    /**
//     * 将jFinal的RecordList转换为JSON的简单格式
//     * @param r
//     * @return
//     */
//    protected List<Map<String, Object>> recordList2Json(List<Record> r ){
//        List<Map<String, Object>> lm = new ArrayList<Map<String, Object>>();
//        for ( int i = 0; i < r.size(); i++ ){
//            lm.add(r.get(i).getColumns());
//        }
//        return lm;
//    }
//    /**
//     * 将Record列表转换成简单JSON返回到界面
//     * @param attrName
//     * @param list
//     */
//    protected void setRecordList2Attr(String attrName, List<Record> list){
//        List<Map<String, Object>> maplist = recordList2Json(list);
//        this.setAttr(attrName, JSONArray.fromObject(maplist).toString());
//    }

    /**
     * 判断是否为NULL和空
     * @param str
     * @return
     */
    protected boolean isNotNullAndEmpty( String str ){
        if ( str == null ) return true;
        if ( str.trim().isEmpty() ) return true;
        return false;
    }

    /**
     * 判断是否数字
     * @param str
     * @return true代表不是数字 false代表数字
     */
    protected boolean isNotNumber(String str){
        if ( str == null || str.trim().isEmpty() ) return true;
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为空
     * @param o
     * @return true为空 false非空
     */
    public boolean isNotNull(Object o){
        String s = (String)o;
        if(s!=null && !s.trim().isEmpty() && !s.equals("0")){
            return true;
        }
        return false;
    }
}

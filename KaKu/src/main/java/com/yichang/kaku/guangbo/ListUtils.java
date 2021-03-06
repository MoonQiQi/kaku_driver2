package com.yichang.kaku.guangbo;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 集合工具类
 */
public class ListUtils {

    public static boolean equalsNull(List<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    public static boolean equalsNull(SparseArray<?> list) {
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 移除ArrayList集合重复数据
     *
     * @param data
     * @return
     */
    public static ArrayList removeSameElement(ArrayList data) {
        if (equalsNull(data)) {
            return null;
        }
        int size = data.size();
        if (size == 1) {
            return data;
        }
        ArrayList tempList = (ArrayList) data.clone();
        int outerSize = size - 1;
        try {
            for (int i = 0; i < outerSize; i++) {
                for (int j = i + 1; j < size; j++) {
                    if (data.size() == 1) {
                        return data;
                    }
                    if (tempList.get(i).equals(tempList.get(j))) {
                        data.remove(i);
                        j--;
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return data;
    }

    /**
     * 去重
     *
     * @param list
     * @return
     */
    public static <T> List<T> deDuplicate(List<T> list) {
        ArrayList<T> result = new ArrayList<>();
        if (list != null) {
            result.addAll(new HashSet<T>(list));
        }
        return result;
    }
}

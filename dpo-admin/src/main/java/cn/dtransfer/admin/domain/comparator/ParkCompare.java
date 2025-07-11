package cn.dtransfer.admin.domain.comparator;


import cn.dtransfer.admin.domain.Park;

import java.util.Comparator;


/**
 * 园区经纬度距离排序
 */
public class ParkCompare implements Comparator<Park> {


    @Override
    public int compare(Park p1, Park p2) {
        if (p1.getDistance() > p2.getDistance()) {
            return 1;
        } else if (p1.getDistance() < p2.getDistance()) {
            return -1;
        }
        return 0;
    }
}

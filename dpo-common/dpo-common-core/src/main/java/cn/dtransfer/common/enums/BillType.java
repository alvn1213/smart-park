package cn.dtransfer.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;


public enum BillType implements IEnum<Integer> {

    /**
     * 固定账单
     */
    GENERAL("固定账单", 1),

    /**
     * 其他账单
     */
    OTHER("其他账单", 2);

    private String name;

    @EnumValue
    private int    value;

    BillType(String name, int value) {
        this.name  = name;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }

    public static BillType parse(Integer value) {
        for (BillType item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}

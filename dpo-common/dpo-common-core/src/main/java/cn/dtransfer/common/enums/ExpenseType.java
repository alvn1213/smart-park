package cn.dtransfer.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import com.fasterxml.jackson.annotation.JsonFormat;


@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ExpenseType implements IEnum<Integer> {

    /**
     * 系统费项
     */
    SYS("系统费项", 0),

    /**
     * 一次性费项
     */
    ONETIME("一次性", 1),


    /**
     * 周期性费项
     */
    CYCLIST("周期性费项", 2);

    private String name;
    private int    value;

    ExpenseType(String name, int value) {
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

    public static ExpenseType parse(Integer value) {
        for (ExpenseType item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}

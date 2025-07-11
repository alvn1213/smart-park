package cn.dtransfer.common.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 款项类型
 */
public enum MoneyType implements IEnum<Integer> {

    /**
     * 应收
     */
    IN("应收", 1),

    /**
     * 应退
     */
    OUT("应退", 2);

    private String name;

    @EnumValue
    private int    value;

    MoneyType(String name, int value) {
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

    public static MoneyType parse(Integer value) {
        for (MoneyType item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}

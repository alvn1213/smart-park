package cn.dtransfer.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

public enum PaymentCycle implements IEnum<Integer> {

    /**
     * 年付
     */
    YEAR("年付", 1),

    /**
     * 月付
     */
    MONTH("月付", 2),


    /**
     * 半年付
     */
    HALF_YEAR("半年付", 3),


    /**
     * 季付
     */
    SEASON("季付", 4),

    /**
     * 其他
     */
    OTHER("其他", 5);

    private String name;
    private int    value;

    PaymentCycle(String name, int value) {
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

    public static PaymentCycle parse(Integer value) {
        for (PaymentCycle item : values()) {
            if (item.getValue().equals(value)) {
                return item;
            }
        }
        return null;
    }
}

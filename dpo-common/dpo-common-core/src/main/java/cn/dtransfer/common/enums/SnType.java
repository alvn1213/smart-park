package cn.dtransfer.common.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * 类型
 */
public enum SnType implements IEnum<Integer> {

    /**
     * 报修单
     */
    PRODUCT("商品", 0);

    private String name;
    private int value;

    SnType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getName() {
        return this.name;
    }
}

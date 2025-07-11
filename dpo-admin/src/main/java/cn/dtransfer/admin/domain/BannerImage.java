package cn.dtransfer.admin.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;

/**
 * 园区banner图片
 *
 * @author dtransfer
 */
@Data
public class BannerImage implements Serializable, Comparable<BannerImage> {

    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
    private Integer uid;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String url;

    @Override
    public int compareTo(BannerImage bannerImage) {
        if (bannerImage == null) {
            return 1;
        }
        return new CompareToBuilder().append(getUid(), bannerImage.getUid()).toComparison();
    }
}

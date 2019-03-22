package com.adachina.web.entity;

import java.util.Date;
import javax.persistence.*;

public class Product {
    /**
     * 自增ID
     */
    @Id
    private Long id;

    /**
     * ezbuy的spuid
     */
    @Column(name = "ref_spu_id")
    private Long refSpuId;

    /**
     * 商品标题(默认英文)
     */
    private String title;

    /**
     * 品牌
     */
    private String brand;

    /**
     * 类目ID
     */
    @Column(name = "category_id1")
    private Integer categoryId1;

    /**
     * 类目Name
     */
    @Column(name = "category_name1")
    private String categoryName1;

    /**
     * 类目ID
     */
    @Column(name = "category_id2")
    private Integer categoryId2;

    /**
     * 类目Name
     */
    @Column(name = "category_name2")
    private String categoryName2;

    /**
     * 类目ID
     */
    @Column(name = "category_id3")
    private Integer categoryId3;

    /**
     * 类目Name
     */
    @Column(name = "category_name3")
    private String categoryName3;

    /**
     * 类目ID
     */
    @Column(name = "category_id4")
    private Integer categoryId4;

    /**
     * 类目Name
     */
    @Column(name = "category_name4")
    private String categoryName4;

    /**
     * 保证类型
     */
    @Column(name = "warrantyType")
    private Integer warrantytype;

    /**
     * 视频地址URL
     */
    @Column(name = "video_url")
    private String videoUrl;

    /**
     * 是否电子产品
     */
    @Column(name = "is_battery")
    private Integer isBattery;

    /**
     * 是否易燃烧
     */
    @Column(name = "is_flammable")
    private Integer isFlammable;

    /**
     * 是否液体
     */
    @Column(name = "is_liquid")
    private Integer isLiquid;

    /**
     * 是否敏感品
     */
    @Column(name = "is_sensitive")
    private Integer isSensitive;

    /**
     * 0：offsale 1：onsale
     */
    @Column(name = "is_sale")
    private Integer isSale;

    /**
     * 供应商CODE
     */
    @Column(name = "vendorCode")
    private String vendorcode;

    /**
     * 供应商名称
     */
    @Column(name = "vendorName")
    private String vendorname;

    /**
     * 采购地
     */
    @Column(name = "originCode")
    private String origincode;

    /**
     * 备注
     */
    private String notice;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人
     */
    @Column(name = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否有效   0 有效   1 无效
     */
    @Column(name = "is_delete")
    private Integer isDelete;

    /**
     * 版本
     */
    private Date version;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取ezbuy的spuid
     *
     * @return ref_spu_id - ezbuy的spuid
     */
    public Long getRefSpuId() {
        return refSpuId;
    }

    /**
     * 设置ezbuy的spuid
     *
     * @param refSpuId ezbuy的spuid
     */
    public void setRefSpuId(Long refSpuId) {
        this.refSpuId = refSpuId;
    }

    /**
     * 获取商品标题(默认英文)
     *
     * @return title - 商品标题(默认英文)
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置商品标题(默认英文)
     *
     * @param title 商品标题(默认英文)
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取品牌
     *
     * @return brand - 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置品牌
     *
     * @param brand 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获取类目ID
     *
     * @return category_id1 - 类目ID
     */
    public Integer getCategoryId1() {
        return categoryId1;
    }

    /**
     * 设置类目ID
     *
     * @param categoryId1 类目ID
     */
    public void setCategoryId1(Integer categoryId1) {
        this.categoryId1 = categoryId1;
    }

    /**
     * 获取类目Name
     *
     * @return category_name1 - 类目Name
     */
    public String getCategoryName1() {
        return categoryName1;
    }

    /**
     * 设置类目Name
     *
     * @param categoryName1 类目Name
     */
    public void setCategoryName1(String categoryName1) {
        this.categoryName1 = categoryName1;
    }

    /**
     * 获取类目ID
     *
     * @return category_id2 - 类目ID
     */
    public Integer getCategoryId2() {
        return categoryId2;
    }

    /**
     * 设置类目ID
     *
     * @param categoryId2 类目ID
     */
    public void setCategoryId2(Integer categoryId2) {
        this.categoryId2 = categoryId2;
    }

    /**
     * 获取类目Name
     *
     * @return category_name2 - 类目Name
     */
    public String getCategoryName2() {
        return categoryName2;
    }

    /**
     * 设置类目Name
     *
     * @param categoryName2 类目Name
     */
    public void setCategoryName2(String categoryName2) {
        this.categoryName2 = categoryName2;
    }

    /**
     * 获取类目ID
     *
     * @return category_id3 - 类目ID
     */
    public Integer getCategoryId3() {
        return categoryId3;
    }

    /**
     * 设置类目ID
     *
     * @param categoryId3 类目ID
     */
    public void setCategoryId3(Integer categoryId3) {
        this.categoryId3 = categoryId3;
    }

    /**
     * 获取类目Name
     *
     * @return category_name3 - 类目Name
     */
    public String getCategoryName3() {
        return categoryName3;
    }

    /**
     * 设置类目Name
     *
     * @param categoryName3 类目Name
     */
    public void setCategoryName3(String categoryName3) {
        this.categoryName3 = categoryName3;
    }

    /**
     * 获取类目ID
     *
     * @return category_id4 - 类目ID
     */
    public Integer getCategoryId4() {
        return categoryId4;
    }

    /**
     * 设置类目ID
     *
     * @param categoryId4 类目ID
     */
    public void setCategoryId4(Integer categoryId4) {
        this.categoryId4 = categoryId4;
    }

    /**
     * 获取类目Name
     *
     * @return category_name4 - 类目Name
     */
    public String getCategoryName4() {
        return categoryName4;
    }

    /**
     * 设置类目Name
     *
     * @param categoryName4 类目Name
     */
    public void setCategoryName4(String categoryName4) {
        this.categoryName4 = categoryName4;
    }

    /**
     * 获取保证类型
     *
     * @return warrantyType - 保证类型
     */
    public Integer getWarrantytype() {
        return warrantytype;
    }

    /**
     * 设置保证类型
     *
     * @param warrantytype 保证类型
     */
    public void setWarrantytype(Integer warrantytype) {
        this.warrantytype = warrantytype;
    }

    /**
     * 获取视频地址URL
     *
     * @return video_url - 视频地址URL
     */
    public String getVideoUrl() {
        return videoUrl;
    }

    /**
     * 设置视频地址URL
     *
     * @param videoUrl 视频地址URL
     */
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    /**
     * 获取是否电子产品
     *
     * @return is_battery - 是否电子产品
     */
    public Integer getIsBattery() {
        return isBattery;
    }

    /**
     * 设置是否电子产品
     *
     * @param isBattery 是否电子产品
     */
    public void setIsBattery(Integer isBattery) {
        this.isBattery = isBattery;
    }

    /**
     * 获取是否易燃烧
     *
     * @return is_flammable - 是否易燃烧
     */
    public Integer getIsFlammable() {
        return isFlammable;
    }

    /**
     * 设置是否易燃烧
     *
     * @param isFlammable 是否易燃烧
     */
    public void setIsFlammable(Integer isFlammable) {
        this.isFlammable = isFlammable;
    }

    /**
     * 获取是否液体
     *
     * @return is_liquid - 是否液体
     */
    public Integer getIsLiquid() {
        return isLiquid;
    }

    /**
     * 设置是否液体
     *
     * @param isLiquid 是否液体
     */
    public void setIsLiquid(Integer isLiquid) {
        this.isLiquid = isLiquid;
    }

    /**
     * 获取是否敏感品
     *
     * @return is_sensitive - 是否敏感品
     */
    public Integer getIsSensitive() {
        return isSensitive;
    }

    /**
     * 设置是否敏感品
     *
     * @param isSensitive 是否敏感品
     */
    public void setIsSensitive(Integer isSensitive) {
        this.isSensitive = isSensitive;
    }

    /**
     * 获取0：offsale 1：onsale
     *
     * @return is_sale - 0：offsale 1：onsale
     */
    public Integer getIsSale() {
        return isSale;
    }

    /**
     * 设置0：offsale 1：onsale
     *
     * @param isSale 0：offsale 1：onsale
     */
    public void setIsSale(Integer isSale) {
        this.isSale = isSale;
    }

    /**
     * 获取供应商CODE
     *
     * @return vendorCode - 供应商CODE
     */
    public String getVendorcode() {
        return vendorcode;
    }

    /**
     * 设置供应商CODE
     *
     * @param vendorcode 供应商CODE
     */
    public void setVendorcode(String vendorcode) {
        this.vendorcode = vendorcode;
    }

    /**
     * 获取供应商名称
     *
     * @return vendorName - 供应商名称
     */
    public String getVendorname() {
        return vendorname;
    }

    /**
     * 设置供应商名称
     *
     * @param vendorname 供应商名称
     */
    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    /**
     * 获取采购地
     *
     * @return originCode - 采购地
     */
    public String getOrigincode() {
        return origincode;
    }

    /**
     * 设置采购地
     *
     * @param origincode 采购地
     */
    public void setOrigincode(String origincode) {
        this.origincode = origincode;
    }

    /**
     * 获取备注
     *
     * @return notice - 备注
     */
    public String getNotice() {
        return notice;
    }

    /**
     * 设置备注
     *
     * @param notice 备注
     */
    public void setNotice(String notice) {
        this.notice = notice;
    }

    /**
     * 获取创建人
     *
     * @return create_user - 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建人
     *
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新人
     *
     * @return update_user - 更新人
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 设置更新人
     *
     * @param updateUser 更新人
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否有效   0 有效   1 无效
     *
     * @return is_delete - 是否有效   0 有效   1 无效
     */
    public Integer getIsDelete() {
        return isDelete;
    }

    /**
     * 设置是否有效   0 有效   1 无效
     *
     * @param isDelete 是否有效   0 有效   1 无效
     */
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    /**
     * 获取版本
     *
     * @return version - 版本
     */
    public Date getVersion() {
        return version;
    }

    /**
     * 设置版本
     *
     * @param version 版本
     */
    public void setVersion(Date version) {
        this.version = version;
    }
}
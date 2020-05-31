package cn.huanhu.entity;

import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * (Goods)实体类
 *
 * @author m
 * @since 2020-05-29
 */

@Data
@Entity
@Table(name = "goods",schema="book")
public class Goods implements Serializable {
    private static final long serialVersionUID = -28446465040284743L;
    /**
    * 商品ID
    */    
    @Column(name= "id")
    private Long id;
   
    /**
    * 商品名称
    */    
    @Column(name= "goods_name")
    private String goodsName;
   
    /**
    * 商品标题
    */    
    @Column(name= "goods_title")
    private String goodsTitle;
   
    /**
    * 商品的图片
    */    
    @Column(name= "goods_img")
    private String goodsImg;
   
    /**
    * 商品的详情介绍
    */    
    @Column(name= "goods_detail")
    private Object goodsDetail;
   
    /**
    * 商品单价
    */    
    @Column(name= "goods_price")
    private Double goodsPrice;
   
    /**
    * 商品库存，-1表示设没有限制
    */    
    @Column(name= "goods_stock")
    private Integer goodsStock;
   


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public Object getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(Object goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

}
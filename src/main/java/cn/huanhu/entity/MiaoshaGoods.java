package cn.huanhu.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * (MiaoshaGoods)实体类
 *
 * @author m
 * @since 2020-05-29
 */

@Data
@Entity
@Table(name = "miaosha_goods",schema="book")
public class MiaoshaGoods implements Serializable {
    private static final long serialVersionUID = 991311913680572444L;
    /**
    * 秒杀的商品表
    */    
    @Column(name= "id")
    private Long id;
   
    /**
    * 商品ld
    */    
    @Column(name= "goods_id")
    private Long goodsId;
   
    /**
    * 秒杀价
    */    
    @Column(name= "miaosha_price")
    private Double miaoshaPrice;
   
    /**
    * 库存数量
    */    
    @Column(name= "stock_count")
    private Integer stockCount;
   
    /**
    * 秒杀开始时间
    */    
    @Column(name= "start_date")
    private Date startDate;
   
    /**
    * 秒杀结束时间
    */    
    @Column(name= "end_date")
    private Date endDate;
   


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
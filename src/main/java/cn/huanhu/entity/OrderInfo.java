package cn.huanhu.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
/**
 * (OrderInfo)实体类
 *
 * @author m
 * @since 2020-05-29 10:39:04
 */

@Data
@Entity
@Table(name = "order_info",schema="book")
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 977453422231470551L;
        
    @Column(name= "id")
    private Long id;
   
    /**
    * 用户ID
    */    
    @Column(name= "user_id")
    private Long userId;
   
    /**
    * 商品ID
    */    
    @Column(name= "goods_id")
    private Long goodsId;
   
    /**
    * 收获地址ID
    */    
    @Column(name= "delivery_addr_id")
    private Long deliveryAddrId;
   
    /**
    * 冗余过来的商品名称
    */    
    @Column(name= "goods_name")
    private String goodsName;
   
    /**
    * 商品数量
    */    
    @Column(name= "goods_count")
    private Integer goodsCount;
   
    /**
    * 商品单价
    */    
    @Column(name= "goods_price")
    private Double goodsPrice;
   
    /**
    * 1pc, 2android, 3ios
    */    
    @Column(name= "order_channel")
    private Integer orderChannel;
   
    /**
    * 订单状态,0新建未支付,1已支付,2已发货,3已收货,4已退款,5已完成
    */    
    @Column(name= "status")
    private Integer status;
   
    /**
    * 订单的创建时间
    */    
    @Column(name= "create_date")
    private Date createDate;
   
    /**
    * 支付时间
    */    
    @Column(name= "pay_date")
    private Date payDate;
   


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }

    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
    }

    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getOrderChannel() {
        return orderChannel;
    }

    public void setOrderChannel(Integer orderChannel) {
        this.orderChannel = orderChannel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

}
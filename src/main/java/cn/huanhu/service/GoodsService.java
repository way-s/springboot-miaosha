package cn.huanhu.service;

import cn.huanhu.dao.GoodsDao;
import cn.huanhu.entity.MiaoshaGoods;
import cn.huanhu.entity.vo.GoodsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author m
 * @className GoodsService
 * @description GoodsService
 * @date 2020/5/29
 */
@Service
public class GoodsService {

    private static final Logger log = LoggerFactory.getLogger(GoodsService.class);

    @Resource
    private GoodsDao goodsDao;

    public List<GoodsVO> listGoodsVo() {
        return goodsDao.listGoodsVo();
    }

    public GoodsVO getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    /**
     * 减库存
     * @param goodsVO
     */
    public boolean reduceStock(GoodsVO goodsVO) {
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoodsId(goodsVO.getId());
        int stock = goodsDao.reduceStock(goods);
        log.info("减库存："+stock);
        return stock >= 0;
    }
}

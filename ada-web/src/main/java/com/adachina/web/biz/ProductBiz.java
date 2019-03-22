package com.adachina.web.biz;

import com.adachina.common.biz.BaseBiz;
import com.adachina.web.entity.Product;
import com.adachina.web.mapper.ProductMapper;
import org.springframework.stereotype.Service;

/**
 * @author ezbuy on ${date}
 */
@Service
public class ProductBiz extends BaseBiz<ProductMapper, Product> {
}

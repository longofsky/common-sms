package com.adachina.web.controller;

import com.adachina.common.controller.BaseController;
import com.adachina.web.biz.ProductBiz;
import com.adachina.web.entity.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ezbuy on ${date}
 */
@RestController
@RequestMapping("/product")
public class ProductController extends BaseController<ProductBiz, Product> {


}

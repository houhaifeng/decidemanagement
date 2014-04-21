package com.decide.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decide.dao.ProductDAO;
import com.decide.model.ListParam;
import com.decide.model.Product;

@Service
public class ProductBiz {
	@Autowired
    ProductDAO productDAO;
   
	public Product queryById(long id){
		return productDAO.queryById(id);
	}
	
	public long insert(Product product){
		return productDAO.insert(product);
	}
	
	public void update(Product product){
		productDAO.update(product);
	}
	
	public void delete(Product product){
		productDAO.delete(product);
	}
	
	public List<Product> queryAllByPage(ListParam param){
		return productDAO.queryAllByPage(param);
	}
	
	public List<Product> queryByProperties(String name, String value){
		return productDAO.queryByProperties(name, value);
	}
}

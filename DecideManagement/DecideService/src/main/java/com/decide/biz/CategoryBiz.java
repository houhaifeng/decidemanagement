package com.decide.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decide.dao.CategoryDAO;
import com.decide.model.ListParam;
import com.decide.model.Product;

@Service
public class CategoryBiz {
   @Autowired
   CategoryDAO categoryDAO;
   
	public Product queryById(long id){
		return categoryDAO.queryById(id);
	}
	
	public long insert(Product product){
		return categoryDAO.insert(product);
	}
	
	public void update(Product product){
	    categoryDAO.update(product);
	}
	
	public void delete(Product product){
		categoryDAO.delete(product);
	}
	
	public List<Product> queryAllByPage(ListParam param){
		return categoryDAO.queryAllByPage(param);
	}
	
	public List<Product> queryByProperties(String name, String value){
		return categoryDAO.queryByProperties(name, value);
	}
}

package com.decide.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.decide.dao.PriceDAO;
import com.decide.model.ListParam;
import com.decide.model.Price;

@Service
public class PriceBiz {
	@Autowired
    PriceDAO priceDAO;
   
	public Price queryById(long id){
		return priceDAO.queryById(id);
	}
	
	public long insert(Price price){
		return priceDAO.insert(price);
	}
	
	public void update(Price price){
		priceDAO.update(price);
	}
	
	public void delete(Price price){
		priceDAO.delete(price);
	}
	
	public List<Price> queryAllByPage(ListParam param){
		return priceDAO.queryAllByPage(param);
	}
	
	public List<Price> queryByProperties(String name, String value){
		return priceDAO.queryByProperties(name, value);
	}
}

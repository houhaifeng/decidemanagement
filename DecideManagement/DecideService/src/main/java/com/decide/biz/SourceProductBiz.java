package com.decide.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decide.dao.SourceProductDAO;
import com.decide.model.ListParam;
import com.decide.model.SourceProduct;

@Service
public class SourceProductBiz {
	@Autowired
    SourceProductDAO sourceProductDAO;
   
	public SourceProduct queryById(long id){
		return sourceProductDAO.queryById(id);
	}
	
	public long insert(SourceProduct sourceProduct){
		return sourceProductDAO.insert(sourceProduct);
	}
	
	public void update(SourceProduct sourceProduct){
		sourceProductDAO.update(sourceProduct);
	}
	
	public void delete(SourceProduct sourceProduct){
		sourceProductDAO.delete(sourceProduct);
	}
	
	public List<SourceProduct> queryAllByPage(ListParam param){
		return sourceProductDAO.queryAllByPage(param);
	}
	
	public List<SourceProduct> queryByProperties(String name, String value){
		return sourceProductDAO.queryByProperties(name, value);
	}
}

package com.decide.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decide.dao.UserDAO;
import com.decide.model.ListParam;
import com.decide.model.User;

@Service
public class UserBiz {
	@Autowired
    UserDAO userDAO;
   
	public User queryById(long id){
		return userDAO.queryById(id);
	}
	
	public long insert(User price){
		return userDAO.insert(price);
	}
	
	public void update(User price){
		userDAO.update(price);
	}
	
	public void delete(User price){
		userDAO.delete(price);
	}
	
	public List<User> queryAllByPage(ListParam param){
		return userDAO.queryAllByPage(param);
	}
	
	public List<User> queryByProperties(String name, String value){
		return userDAO.queryByProperties(name, value);
	}
}

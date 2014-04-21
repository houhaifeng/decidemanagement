package com.decide.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decide.dao.CommentDAO;
import com.decide.model.Comment;
import com.decide.model.ListParam;

@Service
public class CommentBiz {
	@Autowired
    CommentDAO commentDAO;
   
	public Comment queryById(long id){
		return commentDAO.queryById(id);
	}
	
	public long insert(Comment comment){
		return commentDAO.insert(comment);
	}
	
	public void update(Comment comment){
		commentDAO.update(comment);
	}
	
	public void delete(Comment comment){
		commentDAO.delete(comment);
	}
	
	public List<Comment> queryAllByPage(ListParam param){
		return commentDAO.queryAllByPage(param);
	}
	
	public List<Comment> queryByProperties(String name, String value){
		return commentDAO.queryByProperties(name, value);
	}
}

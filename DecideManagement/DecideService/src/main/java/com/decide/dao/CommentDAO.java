package com.decide.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.decide.model.Comment;
import com.decide.model.ListParam;

@DAO
public interface CommentDAO {
	String columns = "id, name, category, brand, image, view_rate, collect_rate, visibilty, score, parameters, create_date";
	String fields = ":1.id, :1.name, :1.category, :1.brand, :1.image, :1.viewRate, :1.collectRate, :1.visibilty, :1.score, :1.parameters, :1.createDate";
    String updateFields = "id=:1.id, name=:1.name, category=:1.category, brand=:1.brand, image=:1.image, view_rate=:1.viewRate, collect_rate=:1.collectRate, visibilty=:1.visibilty, score=:1.score, parameters=:1.parameters, create_date=:1.createDate";
    String tableName = "comment";
    
    @SQL("SELECT " + columns + " FROM " + tableName + " WHERE id=:1.id")
    public Comment queryById(long id);
    
    @ReturnGeneratedKeys
    @SQL("INSERT INTO  " + tableName + " (" + columns + ")VALUES(" + fields + ")")
    public long insert(Comment comment);
    
    @SQL("UPDATE  " + tableName + " (SET " + updateFields + " WHERE id=:1.id")
    public void update(Comment comment);
    
    @SQL("DELETE FROM  " + tableName + "  WHERE id=:1.id")
    public void delete(Comment comment);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE id=:1.id ORDER BY id LIMIT :1.offset,:1.length")
    public List<Comment> queryAllByPage(ListParam param);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE :1=:2")
    public List<Comment> queryByProperties(String name, String value);
}

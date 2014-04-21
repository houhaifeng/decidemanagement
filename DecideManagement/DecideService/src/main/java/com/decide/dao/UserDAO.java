package com.decide.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.decide.model.ListParam;
import com.decide.model.User;

@DAO
public interface UserDAO {
	String columns = "id, name, category, brand, image, view_rate, collect_rate, visibilty, score, parameters, create_date";
	String fields = ":1.id, :1.name, :1.category, :1.brand, :1.image, :1.viewRate, :1.collectRate, :1.visibilty, :1.score, :1.parameters, :1.createDate";
    String updateFields = "id=:1.id, name=:1.name, category=:1.category, brand=:1.brand, image=:1.image, view_rate=:1.viewRate, collect_rate=:1.collectRate, visibilty=:1.visibilty, score=:1.score, parameters=:1.parameters, create_date=:1.createDate";
    String tableName = "user";
    
    @SQL("SELECT " + columns + " FROM " + tableName + " WHERE id=:1.id")
    public User queryById(long id);
    
    @ReturnGeneratedKeys
    @SQL("INSERT INTO  " + tableName + " (" + columns + ")VALUES(" + fields + ")")
    public long insert(User user);
    
    @SQL("UPDATE  " + tableName + " (SET " + updateFields + " WHERE id=:1.id")
    public void update(User user);
    
    @SQL("DELETE FROM  " + tableName + "  WHERE id=:1.id")
    public void delete(User user);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE id=:1.id ORDER BY id LIMIT :1.offset,:1.length")
    public List<User> queryAllByPage(ListParam param);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE :1=:2")
    public List<User> queryByProperties(String name, String value);
}

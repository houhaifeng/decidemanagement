package com.decide.dao;

import java.util.List;

import com.decide.model.ListParam;
import com.decide.model.Product;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

@DAO
public interface ProductDAO {
	String columns = "id, name,link, category, brand, image, view_rate, collect_rate, visibilty, score, parameters, create_date,source, source_id, exist, price";
	String fields = ":1.id, :1.name, :1.link, :1.category, :1.brand, :1.image, :1.viewRate, :1.collectRate, :1.visibilty, :1.score, :1.parameters, :1.createDate, :1.source, :1.sourceId, :1.exist, :1.price";
    String updateFields = "id=:1.id, name=:1.name, link=:1.link, category=:1.category, brand=:1.brand, image=:1.image, view_rate=:1.viewRate, collect_rate=:1.collectRate, visibilty=:1.visibilty, score=:1.score, parameters=:1.parameters, create_date=:1.createDate, source=:1.source, source_id=:1.sourceId, exist=:1.exist, price=:1.price";
    String tableName = "product";
    
    @SQL("SELECT " + columns + " FROM " + tableName + " WHERE id=:1.id")
    public Product queryById(long id);
    
    @ReturnGeneratedKeys
    @SQL("INSERT INTO  " + tableName + " (" + columns + ")VALUES(" + fields + ")")
    public long insert(Product product);
    
    @SQL("UPDATE  " + tableName + " (SET " + updateFields + " WHERE id=:1.id")
    public void update(Product product);
    
    @SQL("DELETE FROM  " + tableName + "  WHERE id=:1.id")
    public void delete(Product product);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE id=:1.id ORDER BY id LIMIT :1.offset,:1.length")
    public List<Product> queryAllByPage(ListParam param);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE :1=:2")
    public List<Product> queryByProperties(String name, String value);
}

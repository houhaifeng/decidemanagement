package com.decide.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.decide.model.ListParam;
import com.decide.model.Price;

@DAO
public interface PriceDAO {
	String columns = "id, product_id, price, promotion, festival, weather, city, new_model, create_date";
	String fields = ":1.id, :1.productId, :1.price, :1.pomotion, :1.festival, :1.weather, :1.city, :1.new_model, :1.create_date";
    String updateFields = "id=:1.id, product_id=:1.productId, price=:1.price, pomotion=:1.pomotion, festival=:1.festival, weather=:1.weather, city=:1.city, new_model=:1.newModel, create_date=:1.createDate";
    String tableName = "price";
    
    @SQL("SELECT " + columns + " FROM " + tableName + " WHERE id=:1.id")
    public Price queryById(long id);
    
    @ReturnGeneratedKeys
    @SQL("INSERT INTO  " + tableName + " (" + columns + ")VALUES(" + fields + ")")
    public long insert(Price price);
    
    @SQL("UPDATE  " + tableName + " (SET " + updateFields + " WHERE id=:1.id")
    public void update(Price price);
    
    @SQL("DELETE FROM  " + tableName + "  WHERE id=:1.id")
    public void delete(Price price);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE id=:1.id ORDER BY id LIMIT :1.offset,:1.length")
    public List<Price> queryAllByPage(ListParam param);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE :1=:2")
    public List<Price> queryByProperties(String name, String value);
}

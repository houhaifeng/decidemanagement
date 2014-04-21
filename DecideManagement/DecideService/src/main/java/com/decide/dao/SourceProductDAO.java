package com.decide.dao;

import java.util.List;

import net.paoding.rose.jade.annotation.DAO;
import net.paoding.rose.jade.annotation.ReturnGeneratedKeys;
import net.paoding.rose.jade.annotation.SQL;

import com.decide.model.ListParam;
import com.decide.model.SourceProduct;

@DAO
public interface SourceProductDAO {
	String columns = "id, product_id, source, source_id, exist";
	String fields = ":1.id, :1.productId, :1.source, :1.sourceId, :1.exist";
    String updateFields = "id=:1.id, product_id=:1.productId, source=:1.source, source_id=:1.sourceId, exist=:1.exist";
    String tableName = "source_product";
    
    @SQL("SELECT " + columns + " FROM " + tableName + " WHERE id=:1.id")
    public SourceProduct queryById(long id);
    
    @ReturnGeneratedKeys
    @SQL("INSERT INTO  " + tableName + " (" + columns + ")VALUES(" + fields + ")")
    public long insert(SourceProduct product);
    
    @SQL("UPDATE  " + tableName + " (SET " + updateFields + " WHERE id=:1.id")
    public void update(SourceProduct product);
    
    @SQL("DELETE FROM  " + tableName + "  WHERE id=:1.id")
    public void delete(SourceProduct product);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE id=:1.id ORDER BY id LIMIT :1.offset,:1.length")
    public List<SourceProduct> queryAllByPage(ListParam param);
    
    @SQL("SELECT " + columns + " FROM  " + tableName + "  WHERE :1=:2")
    public List<SourceProduct> queryByProperties(String name, String value);
}

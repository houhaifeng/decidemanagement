package com.decide.service.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.decide.biz.CategoryBiz;
import com.decide.biz.CommentBiz;
import com.decide.biz.PriceBiz;
import com.decide.biz.ProductBiz;
import com.decide.biz.SourceProductBiz;
import com.decide.biz.UserBiz;

public class DecideServiceFactory {
	private static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	public static CategoryBiz getCategoryBiz(){
		return (CategoryBiz)context.getBean("categoryBiz");
	}
	
	public static CommentBiz getCommentBiz(){
		return (CommentBiz)context.getBean("commentBiz");
	}
	
	public static PriceBiz getPriceBiz(){
		return (PriceBiz)context.getBean("priceBiz");
	}
	
	public static ProductBiz getProductBiz(){
		return (ProductBiz)context.getBean("productBiz");
	}
	
	public static SourceProductBiz getSourceProductBiz(){
		return (SourceProductBiz)context.getBean("sourceProductBiz");
	}
	
	public static UserBiz getUserBiz(){
		return (UserBiz)context.getBean("userBiz");
	}
}

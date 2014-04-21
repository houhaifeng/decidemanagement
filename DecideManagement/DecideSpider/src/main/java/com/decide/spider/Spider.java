package com.decide.spider;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.tags.CompositeTag;
import org.htmlparser.tags.LinkTag;

import com.decide.model.Product;

public abstract class Spider {
	protected Set<String> history = new HashSet<String>();
	protected static class WmlGoTag extends CompositeTag {
		private static final long serialVersionUID = 1L;
		private static final String[] jumpTag = new String[]{"GO"};
		private static final String[] endTag = new String[]{"ANCHOR"};
		public static String[] getJumptag() {
			return jumpTag;
		}
		public static String[] getEndtag() {
			return endTag;
		}
		public String getLink() {
			return super.getAttribute("href");
		}
		public String getMethod() {
			return super.getAttribute("method");
		}
	}
	
	public static class CustomerNodeFilter implements NodeFilter {
		private static final long serialVersionUID = 1L;
		@Override
		public boolean accept(Node node) {
			// TODO Auto-generated method stub
			if (node instanceof WmlGoTag) {
				return true;
			}
			if (node instanceof LinkTag) {
				return true;
			}
			return false;
		}
	};
	
	public String genPrefix(int level){
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < level; i++){
			sb.append("****|");
		}
		return sb.toString();
	}
	/**
	 * 获取页面所有的网页链接
	 * @param url
	 * @return map<anchortext,link>
	 */
	public abstract Map<String,String> getLinkFromPage(String url);
	
	/**
	 * 网页爬去
	 * @param url
	 * @param level
	 */
	public abstract void spide(String url,int level);
	
	/**
	 * 根据产品的详情页获取产品的详情
	 * @param url
	 * @return
	 */
	public abstract Product getProduct(String url);
}

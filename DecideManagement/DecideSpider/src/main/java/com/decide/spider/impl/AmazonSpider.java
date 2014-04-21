package com.decide.spider.impl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.decide.biz.ProductBiz;
import com.decide.constants.AmazonConstants;
import com.decide.constants.Constants;
import com.decide.model.Product;
import com.decide.service.factory.DecideServiceFactory;
import com.decide.spider.Spider;
import com.decide.utils.CheckUtil;
import com.decide.utils.HTTPUtil;

public class AmazonSpider extends Spider {
	private static final Logger log = Logger.getLogger(AmazonSpider.class);
	private static final ProductBiz productBiz = DecideServiceFactory.getProductBiz();
	private static int count = 0;

	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 
	
	@Override
	public Map<String, String> getLinkFromPage(String url) {
		// TODO Auto-generated method stub
		Map<String, String> result = new LinkedHashMap<String, String>();
		Parser htmlParser = null;
		try {
			htmlParser = new Parser(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		NodeList nodes = null;
		try {
			nodes = htmlParser
					.extractAllNodesThatMatch(new CustomerNodeFilter());
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if (nodes != null) {
			for (int i = 0; i < nodes.size(); i++) {
				LinkTag linkTag = (LinkTag) nodes.elementAt(i);
				String linkUrl="";
				linkUrl = linkTag.getLink();
				Node contentNode = linkTag.getFirstChild();
				String content = linkTag.getAttribute("title");
				if(Constants.NEXT_PAGE.equals(content)){
					if (!history.contains(linkUrl)) {
						log.debug(content + ":" + linkUrl);
						result.put(content, linkUrl);
						history.add(linkUrl);
						continue;
					}
				}
				if (contentNode != null) {
					if (contentNode instanceof TextNode) {
						content = contentNode.getText().trim();
						if (HTTPUtil.checkUrl(linkUrl)) {
							if (StringUtils.isEmpty(content)
									|| content.equals(Constants.BUXIAN)
									|| content.equals(Constants.JIAGE)
									|| content.equals(Constants.PINGLUNSHU)
									|| content.equals(Constants.SHANGJIASHIJIAN)
									|| content.equals(Constants.XIAOLIANG)
									|| content.equals(Constants.CHAKANXIANGQING)
									|| isNumeric(content)) {
								continue;
							}
//							if (!history.contains(linkUrl)&&linkUrl.startsWith(AmazonConstants.LIST_PAGE)) {
//								log.debug(content + ":" + linkUrl);
//								result.put(content, linkUrl);
//								history.add(linkUrl);
//							}
						}
					}else if (contentNode instanceof Span) {
						Span spanTag = (Span) contentNode;
						if (spanTag.getAttribute("class").equals("lrg")) {
							content =this.expandEntities(spanTag.getChild(0).getText());
						}else if("pagnNextString".equals(spanTag.getAttribute("id"))){
							content =this.expandEntities(spanTag.getChild(0).getText());
						}
						if (HTTPUtil.checkUrl(linkUrl)) {
							if (StringUtils.isEmpty(content)
									|| content.equals(Constants.BUXIAN)
									|| content.equals(Constants.JIAGE)
									|| content.equals(Constants.PINGLUNSHU)
									|| content.equals(Constants.SHANGJIASHIJIAN)
									|| content.equals(Constants.XIAOLIANG)
									|| content.equals(Constants.CHAKANXIANGQING)) {
								continue;
							}
                            if (!history.contains(linkUrl)) {
								log.debug("**************"+content + ":" + linkUrl);
								result.put(content, linkUrl);
								history.add(linkUrl);
								count++;
							}
						}
					}
				}

			}
		}
		return result;
	}

	public void spide(String url, int level) {
		if (StringUtils.isEmpty(url)) {
			return;
		}
		if (level > 5) {
			return;
		}
		Map<String, String> linkMap = this.getLinkFromPage(url);
		try {
			if (CheckUtil.checkMap(linkMap)) {
				for (Map.Entry<String, String> entry : linkMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (key.equals(Constants.NEXT_PAGE)) {
						log.debug(key + ":" + value);
						spide(value, level);
					}
					if (value.startsWith(AmazonConstants.LIST_PAGE)) {
						spide(value, level + 1);
					} else {
						Product product = this.getProduct(value);
						log.debug(product);
						//productBiz.insert(product);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Product getProduct(String url) {
		Product product = new Product();
		Parser htmlParser = null;
		String productId = "";
		String categoryId = "";
		String backCategoryId = "";
		String topCategoryId = "";
		String brandId = "";
		String brandName = "";
		String newBrandLogoUrl = "";
		String lastCategory = "";
		String stockNum = "";
		String companyName = "";
		String storeId = "";
		String image = "";
		String title = "";
		String price = "";
		try {
			htmlParser = new Parser(url);
			NodeFilter spanFilter = new AndFilter(
					new HasAttributeFilter("id"), new TagNameFilter("span"));
			NodeFilter tdFilter = new TagNameFilter("td");
			NodeFilter filter = new OrFilter(tdFilter,spanFilter);
			NodeList nodes = htmlParser.extractAllNodesThatMatch(filter);
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node node = nodes.elementAt(i);
					if (node instanceof Span) {
						Span span = (Span) node;
						String id = span.getAttribute("id");
						if (id.equals("btAsinTitle")) {
							NodeList nodeList = span.getChildren();
							if(nodeList != null && nodeList.size() > 0){
								for (int j = 0; j < nodeList.size(); j++) {
									if(nodeList.elementAt(j) instanceof Span){
										Span titleSpan = (Span)nodeList.elementAt(j);
									    NodeList nl = titleSpan.getChildren();
									    if(nl != null && nl.size() > 0){
									    	for(int k = 0 ; k < nl.size(); k++){
									    		if(nl.elementAt(k) instanceof TextNode){
									    			TextNode tn = (TextNode)nl.elementAt(k);
									    			title = tn.getText().trim();
									    		}
									    	}
									    }
									}
								}
							}
						}else if(id.equals("actualPriceValue")){
							NodeList nodeList = span.getChildren();
							if(nodeList != null && nodeList.size() > 0){
								for (int j = 0; j < nodeList.size(); j++) {
									Node n = nodeList.elementAt(j);
									if(n != null && n instanceof TextNode){
										price = n.getText().substring(n.getText().indexOf(" ")+1).replaceAll(",", "");
									}
								}
							}
						}
					}else if(node instanceof TableColumn){
						TableColumn tc = (TableColumn)node;
						String id = tc.getAttribute("id");
						if("fbt_x_img".equals(id)){
							NodeList nl = tc.getChildren();
							if(nl != null){
								for(int j = 0; j < nl.size(); j++){
									Node n = nl.elementAt(j);
									if(n instanceof ImageTag){
										ImageTag it = (ImageTag)n;
										image=it.getAttribute("src");
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		product.setBrand(brandId);
		product.setCategory("[" + topCategoryId + "," + backCategoryId + ","
				+ categoryId + "]");
		product.setCreateDate(System.currentTimeMillis());
		product.setLink(url);
		//product.setSourceId(Long.parseLong(productId));
		product.setSource("Amazon");
		product.setName(title);
		product.setImage(image);
		product.setPrice(Double.parseDouble(price));
		return product;
	}

	private int lookupEntity(char[] buff, int offset, int length) {
		int result = 0;
		if (length < 1){
			return result;
		}
		if (buff[offset] == '#') {
			if (length > 1 && (buff[offset + 1] == 'x' || buff[offset + 1] == 'X')) {
				try {
					return Integer.parseInt(new String(buff, offset + 2,
							length - 2), 16);
				} catch (NumberFormatException e) {
					return 0;
				}
			}
			try {
				return Integer.parseInt(new String(buff, offset + 1, length - 1), 10);
			} catch (NumberFormatException e) {
				return 0;
			}
		}
		return 0;
	}

	public  String expandEntities(String src) {
		int refStart = -1;
		int len = src.length();
		char[] dst = new char[len];
		int dstlen = 0;
		for (int i = 0; i < len; i++) {
			char ch = src.charAt(i);
			dst[dstlen++] = ch;
			if (ch == '&' && refStart == -1) {
				refStart = dstlen;
			} else if (refStart == -1) {
		
			} else if (Character.isLetter(ch) || Character.isDigit(ch) || ch == '#') {
				
			} else if (ch == ';') {
				int ent = lookupEntity(dst, refStart, dstlen - refStart - 1);
				if (ent > 0xFFFF) {
					ent -= 0x10000;
					dst[refStart - 1] = (char) ((ent >> 10) + 0xD800);
					dst[refStart] = (char) ((ent & 0x3FF) + 0xDC00);
					dstlen = refStart + 1;
				} else if (ent != 0) {
					dst[refStart - 1] = (char) ent;
					dstlen = refStart;
				}
				refStart = -1;
			} else {
				refStart = -1;
			}
		}
		return new String(dst, 0, dstlen);
	}

	public static void main(String[] args) {
		AmazonSpider amazonSpider = new AmazonSpider();
	    amazonSpider.spide("http://www.amazon.cn/b/ref=sr_aj?node=874269051&ajr=0",0);
		//amazonSpider.getProduct("http://www.amazon.cn/gp/product/B00AXUK534/ref=noref?ie=UTF8&psc=1&s=pc");
		System.out.println(count);
	}
}

package com.decide.spider.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONObject;

import com.decide.biz.ProductBiz;
import com.decide.constants.Constants;
import com.decide.constants.YXConstants;
import com.decide.model.Product;
import com.decide.service.factory.DecideServiceFactory;
import com.decide.spider.Spider;
import com.decide.utils.CheckUtil;
import com.decide.utils.HTTPUtil;

public class YXSpider extends Spider {
	private static final Logger log = Logger.getLogger(YXSpider.class);
	private static final ProductBiz productBiz = DecideServiceFactory
			.getProductBiz();
	private static int count = 0;

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
				String linkUrl = linkTag.getLink();
				Node contentNode = linkTag.getFirstChild();
				if (contentNode != null) {
					if (contentNode instanceof TextNode) {
						String content = contentNode.getText().trim();
						log.debug(content + ":" + linkUrl);
						if (HTTPUtil.checkUrl(linkUrl)) {
							if (StringUtils.isEmpty(content)
									|| content.equals(Constants.BUXIAN)
									|| content.equals(Constants.JIAGE)
									|| content.equals(Constants.PINGLUNSHU)
									|| content
											.equals(Constants.SHANGJIASHIJIAN)
									|| content.equals(Constants.XIAOLIANG)
									|| content
											.equals(Constants.CHAKANXIANGQING)) {
								continue;
							}
							if (linkUrl.startsWith(YXConstants.LIST_PAGE)
									|| linkUrl
											.startsWith(YXConstants.ITEM_PAGE)) {
								if (url.startsWith(YXConstants.LIST_PAGE)) {
									if (!history.contains(linkUrl)
											&& (linkUrl
													.startsWith(YXConstants.ITEM_PAGE)
													|| content
															.equals(Constants.NEXT_PAGE)
													|| content.equals("&gt;") || content
														.equals("\""
																+ Constants.NEXT_PAGE
																+ "\""))) {
										result.put(content, linkUrl);
										history.add(linkUrl);
									}
								} else {
									if (!history.contains(linkUrl)) {
										result.put(content, linkUrl);
										history.add(linkUrl);
									}
								}
							}
						}
					}
				}

			}
		}
		return result;
	}

	@Override
	public void spide(String url, int level) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(url)) {
			return;
		}
		if (level > 5) {
			return;
		}
		log.debug("**************" + url);
		Map<String, String> linkMap = this.getLinkFromPage(url);
		try {
			if (CheckUtil.checkMap(linkMap)) {
				for (Map.Entry<String, String> entry : linkMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();

					if (key.equals(Constants.NEXT_PAGE) || key.equals("&gt;")) {
						log.debug("下一页:" + value);
						spide(value, level);
					}
					if (value.startsWith(YXConstants.LIST_PAGE)) {
						spide(value, level + 1);
					} else if (value.startsWith(YXConstants.ITEM_PAGE)) {
						Product product = this.getProduct(value);
						if (product != null) {
							log.debug(product);
							productBiz.insert(product);
							count++;
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Product getProduct(String url) {
		Product product = null;
		Parser htmlParser = null;
		String productId = "-1";
		String c1ids = "";
		String c1name = "";
		String c2ids = "";
		String c2name = "";
		String c3ids = "";
		String c3name = "";
		String categoryId = "";
		String brandId = "";
		String brandName = "";
		String newBrandLogoUrl = "";
		String lastCategory = "";
		String stockNum = "";
		String companyName = "";
		String storeId = "";
		String image = "";
		String title = "";
		String price = "0.0";
		try {
			htmlParser = new Parser(url);
			NodeFilter scriptFilter = new TagNameFilter("script");
			NodeList nodes = htmlParser.extractAllNodesThatMatch(scriptFilter);
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node node = nodes.elementAt(i);
					if (node instanceof ScriptTag) {
						ScriptTag scriptTag = (ScriptTag) node;
						NodeList childs = scriptTag.getChildren();
						if (childs != null && childs.size() > 0) {
							String content = childs.elementAt(0).getText();
							if (content.contains("var itemInfo")) {
								String[] array = content.split(";");
								for (String str : array) {
									if (str.startsWith("setItemInfo(")) {
										String jsonStr = str.substring(
												str.indexOf("{"),
												str.lastIndexOf("}") + 1);
										log.debug(jsonStr);
										JSONObject jsonObj = new JSONObject(
												jsonStr);
										brandName = jsonObj.optString(
												"brand_name", "");
										brandId = jsonObj.optString("brand_no",
												"");
										c1ids = jsonObj.optString("c1ids", "");
										c2ids = jsonObj.optString("c2ids", "");
										c3ids = jsonObj.optString("c3ids", "");
										c1name = jsonObj
												.optString("c1name", "");
										c2name = jsonObj
												.optString("c2name", "");
										c3name = jsonObj
												.optString("c3name", "");
										categoryId = jsonObj.optString(
												"category_id", "");
										companyName = jsonObj.optString(
												"cooper_name", "");
										image = jsonObj
												.optString("mainpic", "");
										price = jsonObj
												.getJSONObject("mult_price")
												.getJSONObject("icson_price")
												.optString("price", "0.0");
										title = jsonObj.optString("name", "");
										productId = jsonObj.optString("pid",
												"-1");
									}
								}
							}

						}

					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("Parse product detail parameters error:", e);
			return null;
		}
		if (!productId.equals("-1")) {
			product = new Product();
			product.setBrand(brandId);
			product.setCategory("[" + c1ids + "," + c2ids + "," + c3ids + ","
					+ categoryId + "]");
			product.setCreateDate(System.currentTimeMillis());
			product.setLink(url);
			product.setSourceId(Long.parseLong(productId));
			product.setSource("YX");
			product.setName(title);
			product.setImage(image);
			product.setPrice(Double.parseDouble(price));
		} else {
			log.debug(product);
		}
		return product;
	}

	public static void main(String[] args) {
		YXSpider yxSpider = new YXSpider();
		// yxSpider.getLinkFromPage("http://jiadian.yixun.com/?YTAG=2.20000802");
		yxSpider.getProduct("http://item.yixun.com/item-634233.html?YTAG=3.705893212000");
		// yxSpider.spide("http://searchex.yixun.com/html?YTAG=2.1159606070100&path=705852t705856t707218t707231",
		// 0);
		System.out.println("count:" + count);
	}
}

package com.decide.spider.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONArray;
import org.json.JSONObject;

import com.decide.biz.ProductBiz;
import com.decide.common.util.HTTPHelper;
import com.decide.constants.Constants;
import com.decide.constants.JDConstants;
import com.decide.model.Product;
import com.decide.service.factory.DecideServiceFactory;
import com.decide.spider.Spider;
import com.decide.utils.CheckUtil;
import com.decide.utils.HTTPUtil;

public class JDSpider extends Spider {
	private static Map<String, String> categoryMap = new LinkedHashMap<String, String>();
	static {
		categoryMap.put("家用电器", "http://channel.jd.com/electronic.html");
		categoryMap.put("电脑办公", "http://channel.jd.com/computer.html");
		categoryMap.put("手机", "http://shouji.jd.com/");
		categoryMap.put("数码", "http://channel.jd.com/digital.html");
		categoryMap.put("母婴", "http://channel.jd.com/baby.html");
	}
	
	private static ProductBiz productBiz = DecideServiceFactory.getProductBiz();
	private static Logger log = Logger.getLogger(JDSpider.class);

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
			System.out.println(nodes.size());
			for (int i = 0; i < nodes.size(); i++) {
				LinkTag linkTag = (LinkTag) nodes.elementAt(i);
				String linkUrl = linkTag.getLink();
				Node contentNode = linkTag.getFirstChild();
				if (contentNode != null) {
					if (contentNode instanceof TextNode) {
						String content = contentNode.getText();
						System.out.println(content + ":" + linkUrl);
						if (HTTPUtil.checkUrl(linkUrl)) {
							if (StringUtils.isEmpty(content)
									|| content.equals(Constants.BUXIAN)
									|| content.equals(Constants.JIAGE)
									|| content.equals(Constants.PINGLUNSHU)
									|| content
											.equals(Constants.SHANGJIASHIJIAN)
									|| content.equals(Constants.XIAOLIANG)) {
								continue;
							}
							if (url.startsWith(JDConstants.LIST_PAGE)
									|| url.startsWith(JDConstants.JD_PRODUCT)) {
								if (!history.contains(linkUrl)
										&& (linkUrl
												.startsWith(JDConstants.ITEM_PAGE) || content
												.equals(Constants.NEXT_PAGE))) {
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

		if (CheckUtil.checkMap(linkMap)) {
			for (Map.Entry<String, String> entry : linkMap.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				if (key.equals(Constants.NEXT_PAGE)) {
					spide(value, level);
				}
				if (value.startsWith(JDConstants.JD_PRODUCT)
						|| value.startsWith(JDConstants.LIST_PAGE)
						|| value.startsWith(JDConstants.CHANNEL_PAGE)) {
					spide(value, level + 1);
				} else if (value.startsWith(JDConstants.ITEM_PAGE)) {
					Product product = this.getProduct(value);
					if (product != null) {
						long productId = productBiz.insert(product);
					}
				}
			}
		}
	}

	public Product getProduct(String url) {
		Product product = new Product();
		JSONObject productObj = new JSONObject();
		Parser htmlParser = null;
		HttpClient client = new DefaultHttpClient();
		try {
			htmlParser = new Parser(url);
			NodeFilter scriptFilter = new TagNameFilter("script");
			NodeList nodes = htmlParser.extractAllNodesThatMatch(scriptFilter);
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node scriptNode = nodes.elementAt(i);
					Node contentNode = scriptNode.getFirstChild();
					if (contentNode != null) {
						String content = contentNode.getText();
						if (content.contains("window.pageConfig")) {
							content = content.trim().replace(
									"window.pageConfig = ", "");
							JSONObject jsonObj = new JSONObject(content);
							productObj = jsonObj.getJSONObject("product");
							productObj.put("link", url);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			client.getConnectionManager().shutdown();
		}

		if (productObj != null) {
			product = new Product();
			String name = productObj.optString("name", "");
			String imageUrl = productObj.optString("src", "");
			String category = productObj.optString("cat", "");
			String brand = productObj.optString("brand", "");
			String link = productObj.optString("link", "");
			String skuid = productObj.optString("skuid", "");
			product.setName(name);
			product.setCategory(category);
			product.setBrand(brand);
			product.setCreateDate(System.currentTimeMillis());
			product.setImage(JDConstants.JD_IMAGE + Constants.HTTP_SEPARATOR
					+ imageUrl);
			product.setLink(link);
			product.setSource("JD");
			product.setSourceId(productObj.optLong("skuid", 0));
			if (StringUtils.isNotEmpty(skuid)) {
				client = new DefaultHttpClient();
				try {
					String response = HTTPHelper.get(client,
							JDConstants.JD_PRICE + "?skuid=J_" + skuid);
					if (StringUtils.isNotEmpty(response)) {
						JSONArray jsonArray = new JSONArray(response);
						JSONObject jsonObj = jsonArray.getJSONObject(0);
						product.setPrice(jsonObj.optDouble("p", 0.0));
					}
				} catch (Exception e) {
					log.error(e);
				} finally {
					client.getConnectionManager().shutdown();
				}
			}

		}

		return product;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Spider jdSpider = new JDSpider();
		jdSpider.spide("http://channel.jd.com/electronic.html", 0);
		long end = System.currentTimeMillis();
		System.out.println("Time costs:" + (end - start) + "ms");
	}
}

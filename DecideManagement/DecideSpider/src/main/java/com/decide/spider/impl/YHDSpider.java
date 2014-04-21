package com.decide.spider.impl;

import java.util.LinkedHashMap;
import java.util.Map;

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
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONException;

import com.decide.biz.ProductBiz;
import com.decide.constants.Constants;
import com.decide.constants.YHDConstants;
import com.decide.model.Product;
import com.decide.service.factory.DecideServiceFactory;
import com.decide.spider.Spider;
import com.decide.utils.CheckUtil;
import com.decide.utils.HTTPUtil;

public class YHDSpider extends Spider {
	private static final Logger log = Logger.getLogger(YHDSpider.class);
	private static final ProductBiz productBiz = DecideServiceFactory
			.getProductBiz();
	private static Map<String, String> categoryMap = new LinkedHashMap<String, String>();

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
							if (url.startsWith(YHDConstants.LIST_PAGE)) {
								if (!history.contains(linkUrl)
										&& (linkUrl
												.startsWith(YHDConstants.ITEM_PAGE)
												|| content
														.equals(Constants.NEXT_PAGE) || content
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
					if (value.startsWith(YHDConstants.LIST_PAGE)
							|| value.startsWith(YHDConstants.CHANNEL_PAGE)) {
						spide(value, level + 1);
					} else if (value.startsWith(YHDConstants.ITEM_PAGE)) {
						Product product = this.getProduct(value);
						log.debug(product);
						productBiz.insert(product);
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
		try {
			htmlParser = new Parser(url);
			NodeFilter spanFilter = new AndFilter(new AndFilter(
					new HasAttributeFilter("id"), new TagNameFilter("span")),
					new HasAttributeFilter("class"));
			NodeFilter h1Filter = new AndFilter(new TagNameFilter("h1"),
					new HasAttributeFilter("class"));
			NodeFilter imgFilter = new AndFilter(new TagNameFilter("img"),
					new HasAttributeFilter("id"));
			NodeFilter inputFilter = new AndFilter(new TagNameFilter("input"),
					new HasAttributeFilter("id"));
			NodeFilter filter = new OrFilter(new OrFilter(inputFilter,
					spanFilter), new OrFilter(h1Filter, imgFilter));
			NodeList nodes = htmlParser.extractAllNodesThatMatch(filter);
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node node = nodes.elementAt(i);
					if (node instanceof Span) {
						Span span = (Span) node;
						String id = span.getAttribute("id");
						if (id.equals("current_price")) {
							NodeList nodeList = span.getChildren();
							for (int j = 0; j < nodeList.size(); j++) {
								Node subNode = nodeList.elementAt(j);
								if (subNode instanceof TextNode) {
									if (!subNode.getText().equals("&yen"))
										product.setPrice(Double
												.parseDouble(subNode.getText()));
								}
							}
						}
					} else if (node instanceof InputTag) {
						InputTag inputTag = (InputTag) node;
						String id = inputTag.getAttribute("id");
						String value = "value";
						if (StringUtils.isEmpty(id)) {
							continue;
						}
						if (id.equals("categoryId")) {
							categoryId = inputTag.getAttribute(value);
						} else if (id.equals("backCategoryId")) {
							backCategoryId = inputTag.getAttribute(value);
						} else if (id.equals("isYiHaoDian")) {

						} else if (id.equals("topCategoryId")) {
							topCategoryId = inputTag.getAttribute(value);
						} else if (id.equals("lastCategory")) {
							lastCategory = inputTag.getAttribute(value);
						} else if (id.equals("brandID")) {
							brandId = inputTag.getAttribute(value);
						} else if (id.equals("brandName")) {
							brandName = inputTag.getAttribute(value);
						} else if (id.equals("newBrandLogoUrl")) {
							newBrandLogoUrl = inputTag.getAttribute(value);
						} else if (id.equals("hasStockNum")) {
							stockNum = inputTag.getAttribute(value);
						} else if (id.equals("isCanSale")) {

						} else if (id.equals("storeId")) {
							storeId = inputTag.getAttribute(value);
						} else if (id.equals("companyName")) {
							companyName = inputTag.getAttribute(value);
						} else if (id.equals("isSecondHandProduct")) {

						} else if (id.equals("productId")) {
							productId = inputTag.getAttribute(value);
						} else if (id.equals("isCanSale")) {

						}
					} else if (node instanceof ImageTag) {
						ImageTag img = (ImageTag) node;
						if (img.getAttribute("id").equals("J_prodImg")) {
							image = img.getAttribute("src");
						}
					} else if (node instanceof HeadingTag) {
						HeadingTag htag = (HeadingTag) node;
						if (htag.getAttribute("id").equals("productMainName")) {
							title = htag.getChild(0).getText();
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
		product.setSourceId(Long.parseLong(productId));
		product.setSource("YHD");
		product.setName(title);
		product.setImage(image);
		return product;
	}

	public static void main(String[] args) throws JSONException {
		YHDSpider yhdSpider = new YHDSpider();
		long start = System.currentTimeMillis();
		yhdSpider
				.spide("http://www.yhd.com/ctg/s2/c21307-%E6%89%8B%E6%9C%BA%E9%80%9A%E8%AE%AF/?ref=gl.1.1.31.[CatMenu_Site_100000003_6800].0.649OIX",
						0);
		long end = System.currentTimeMillis();
		System.out.println("Time costs:" + (end - start) + "ms");
	}

}

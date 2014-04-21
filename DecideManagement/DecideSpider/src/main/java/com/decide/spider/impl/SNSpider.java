package com.decide.spider.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONObject;

import com.decide.biz.ProductBiz;
import com.decide.constants.Constants;
import com.decide.constants.SNConstants;
import com.decide.image.PatternRecogonize;
import com.decide.model.Product;
import com.decide.service.factory.DecideServiceFactory;
import com.decide.spider.Spider;
import com.decide.utils.CheckUtil;
import com.decide.utils.HTTPUtil;

public class SNSpider extends Spider {
	private static final Logger log = Logger.getLogger(SNSpider.class);
	private PatternRecogonize pr = new PatternRecogonize();
	private ProductBiz productBiz = DecideServiceFactory.getProductBiz();

	@Override
	public Map<String, String> getLinkFromPage(String url) {
		if (StringUtils.isEmpty(url)) {
			return null;
		}
		Map<String, String> result = new LinkedHashMap<String, String>();
		Parser htmlParser = null;
		try {
			htmlParser = new Parser(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
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
				String title = "";
				int totalPage = 0;
				Node contentNode = linkTag.getFirstChild();
				if (contentNode != null) {
					String content = contentNode.getText();
					if (StringUtils.isNotEmpty(content)) {
						title = content;
						if (linkTag.getAttribute("id") != null
								&& linkTag.getAttribute("id")
										.equals("pageLast")) {
							totalPage = Integer.parseInt(content);
							log.debug("Total page:" + totalPage);
						}
					}

				}
				if (StringUtils.isNotEmpty(linkTag
						.getAttribute(Constants.TITLE))) {
					title = linkTag.getAttribute(Constants.TITLE);
				}

				if (StringUtils.isNotEmpty(title.trim())) {
					if (title.startsWith(Constants.IMG)) {
						continue;
					}

					if (HTTPUtil.checkUrl(linkUrl)) {
						if (linkUrl.endsWith("#pro_detail_tab111")) {
							continue;
						} else if (url.startsWith(SNConstants.LIST_PAGE)
								|| url.startsWith(SNConstants.ITEM_PAGE)
								|| url.startsWith(SNConstants.NEXT_PAGE)) {
							if (!history.contains(linkUrl)) {
								if (title.equals(Constants.NEXT_PAGE)
										&& linkTag.getAttribute("id") != null
										&& linkTag.getAttribute("id").equals(
												"nextPage")) {
									String pageValue = HTTPUtil.getParam(url,
											"cp");
									String nextPageUrl = "";
									int pageNum = 1;
									if (StringUtils.isNotEmpty(pageValue)) {
										pageNum = (Integer.parseInt(pageValue) + 1);
									}
									if (totalPage > 0 && pageNum >= totalPage) {
										break;
									}
									nextPageUrl = HTTPUtil.setParam(linkUrl,
											"cp", pageNum + "");
									if (!history.contains(nextPageUrl)) {
										result.put(title, nextPageUrl);
										history.add(nextPageUrl);
									}
								} else if ((linkUrl
										.startsWith(SNConstants.ITEM_PAGE))) {
									result.put(title, linkUrl);
									history.add(linkUrl);
								}
							}
						} else {
							if (!history.contains(linkUrl)) {
								result.put(title, linkUrl);
								history.add(linkUrl);
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
		if (StringUtils.isEmpty(url)) {
			return;
		}
		if (level > 5) {
			return;
		}
		Map<String, String> linkMap = getLinkFromPage(url);
		if (!CheckUtil.checkMap(linkMap)) {
			return;
		}

		if (linkMap.size() == 1) {
			if (linkMap.keySet().contains(Constants.NEXT_PAGE)) {
				return;
			}
		}
		Map<Long, Double> map = new HashMap<Long, Double>();
		if (url.startsWith(SNConstants.LIST_PAGE)) {
			map = this.getPrice(url);
		}

		for (Map.Entry<String, String> entry : linkMap.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			if (key.equals(Constants.NEXT_PAGE)) {
				spide(value, level);
			} else if (value.startsWith(SNConstants.LIST_PAGE)) {
				spide(value, level + 1);
			} else if (value.startsWith(SNConstants.ITEM_PAGE)) {
				Product product = this.getProduct(value);
				if (product.getPrice() == 0.0) {
					product.setPrice(map.get(product.getSourceId()));
				}
			}
		}

	}

	public JSONObject getProductDetail1(String url) {
		JSONObject productObj = new JSONObject();
		Parser htmlParser = null;
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
						if (StringUtils.isNotEmpty(content)) {
							if (content.contains(SNConstants.SN_QA)) {
								String snContent = content.substring(content
										.indexOf(SNConstants.SN + "="
												+ SNConstants.SN));
								snContent = snContent.substring(
										snContent.indexOf("{"),
										snContent.indexOf("}") + 1);
								snContent = snContent
										.replace(
												"\"elecProductDomain\":'http://product.suning.com',",
												"");
								JSONObject jsonObj = new JSONObject(snContent);
								productObj.put("price",
										jsonObj.optString("currPrice", ""));
								productObj.put("productId",
										jsonObj.optString("productId", ""));
								productObj.put("categoryId",
										jsonObj.optString("categoryId", ""));
								productObj.put("storeId",
										jsonObj.optString("storeId", ""));
								productObj.put("partNumber",
										jsonObj.optString("partNumber", ""));
								productObj.put("link", url);
								String snqaContent = content.substring(content
										.indexOf(SNConstants.SN_QA));
								jsonObj = new JSONObject(snqaContent.substring(
										snqaContent.indexOf("{"),
										snqaContent.indexOf("}") + 1));
								String category1 = jsonObj.optString(
										"category1", "");
								String category2 = jsonObj.optString(
										"category2", "");
								String category3 = jsonObj.optString(
										"category3", "");
								productObj.put("category", "[" + category1
										+ "," + category2 + "," + category3
										+ "]");
								String picContent = content.substring(content
										.indexOf(SNConstants.SN_PIC));
								String image = picContent.substring(
										picContent.indexOf("\"") + 1,
										picContent.indexOf(";") - 1);
								productObj.put("image", image);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return productObj;
	}

	public JSONObject getProductDetail2(String url) {
		JSONObject productObj = new JSONObject();
		Parser htmlParser = null;
		try {
			htmlParser = new Parser(url);
			NodeFilter nameFilter = new HasAttributeFilter("name");
			NodeFilter inputFilter = new TagNameFilter("input");
			NodeFilter filter = new AndFilter(nameFilter, inputFilter);
			NodeList nodes = htmlParser.extractAllNodesThatMatch(filter);
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node inputNode = nodes.elementAt(i);
					if (inputNode instanceof InputTag) {
						InputTag inputTag = (InputTag) inputNode;
						String name = inputTag.getAttribute("name");
						String value = inputTag.getAttribute("value");
						if (name.equals("catEntryId_1")) {
							name = "productId";
						} else if (name.equals("returnUrl")) {
							name = "url";
						}
						productObj.put(name, value);
					}
				}
			}
			productObj.put("link", url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return productObj;
	}

	public boolean getFileFromUrl(String url, File file) {
		boolean sucess = false;
		InputStream is = null;
		OutputStream os = null;
		HttpURLConnection con = null;
		try {
			URL URLObj = new URL(url);
			con = (HttpURLConnection) URLObj.openConnection();
			con.setInstanceFollowRedirects(false);
			con.setUseCaches(false);
			con.setAllowUserInteraction(false);
			con.connect();
			is = con.getInputStream();
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[1024];
			while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			sucess = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e);
		} finally {
			con.disconnect();
			try {
				if (os != null) {
					os.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error(e);
			} finally {
				os = null;
				is = null;
			}
		}
		return sucess;
	}

	public Map<Long, Double> getPrice(String url) {
		System.out.println(url);
		Map<Long, Double> map = new HashMap<Long, Double>();
		Parser htmlParser = null;
		try {
			htmlParser = new Parser(url);
			NodeFilter classFilter = new HasAttributeFilter("class");
			NodeFilter srcFilter = new HasAttributeFilter("src2");
			NodeFilter liFilter = new TagNameFilter("img");
			NodeFilter filter = new AndFilter(new AndFilter(classFilter,
					liFilter), srcFilter);
			NodeList nodes = htmlParser.extractAllNodesThatMatch(filter);
			if (nodes != null) {
				for (int i = 0; i < nodes.size(); i++) {
					Node node = nodes.elementAt(i);
					if (node instanceof ImageTag) {
						ImageTag imageTag = (ImageTag) node;
						String type = imageTag.getAttribute("class");
						String imgUrl = imageTag.getAttribute("src2");
						if (type.equals("liprice")) {
							System.out.println(imgUrl);
							File file = new File(imgUrl.substring(imgUrl
									.lastIndexOf("/") + 1));
							boolean isSuccess = getFileFromUrl(imgUrl, file);
							if (isSuccess) {
								String priceStr = pr.recogoinze(file);
								if (StringUtils.isNotEmpty(priceStr)) {
									String imageName = imgUrl.substring(imgUrl
											.lastIndexOf("/") + 1);
									String productId = imageName.substring(0,
											imageName.indexOf("_"));
									map.put(Long.parseLong(productId),
											Double.parseDouble(priceStr));
								}
								if (file.exists()) {
									file.delete();
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
		return map;
	}

	@Override
	public Product getProduct(String url) {
		// TODO Auto-generated method stub
		Product product = new Product();
		JSONObject productJson1 = this.getProductDetail1(url);
		JSONObject productJson2 = this.getProductDetail2(url);
		if (productJson1 != null && productJson2 != null) {
			product.setCategory(productJson1.optString("category"));
			product.setCreateDate(System.currentTimeMillis());
			product.setImage(productJson1.optString("image"));
			product.setVisibilty(true);
			product.setLink(productJson1.optString("link"));
			product.setSource("SN");
			product.setSourceId(productJson1.optLong("productId", 0l));
			product.setPrice(productJson1.optDouble("price", 0.0));
			product.setName(productJson2.optString("name"));
			log.debug(product);
		}
		return product;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		Spider snSpider = new SNSpider();
		snSpider.spide(
				"http://list.suning.com/%E7%A7%BB%E5%8A%A8%E7%94%B5%E6%BA%90/179001/cityId=9017&iy=-1&si=5&st=14",
				0);
		long end = System.currentTimeMillis();
		System.out.println("Time costs:" + (end - start) + "ms");
	}
}

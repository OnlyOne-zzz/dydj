package com.bestfeng.dydj.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

public class MapUtil<T> {

	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null)
			return null;

		Object obj = beanClass.newInstance();

		Field[] fields = obj.getClass().getDeclaredFields();
		for (Field field : fields) {
			int mod = field.getModifiers();
			if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
				continue;
			}

			field.setAccessible(true);
			field.set(obj, map.get(field.getName()));
		}

		return obj;
	}

	/**  
	 * 将一个 Map 对象转化为一个 JavaBean  
	 * @param clazz 要转化的类型  
	 * @param map 包含属性值的 map  
	 * @return 转化出来的 JavaBean 对象  
	 * @throws IntrospectionException 如果分析类属性失败  
	 * @throws IllegalAccessException 如果实例化 JavaBean 失败  
	 * @throws InstantiationException 如果实例化 JavaBean 失败  
	 * @throws InvocationTargetException 如果调用属性的 setter 方法失败  
	 */
	public static <T> T toBean(Map<String, Object> map, Class<T> clazz) {
		T obj = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			obj = clazz.newInstance(); // 创建 JavaBean 对象

			// 给 JavaBean 对象的属性赋值
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
					Object value = map.get(propertyName);
					if ("".equals(value)) {
						value = null;
					}
					Object[] args = new Object[1];

					if (value instanceof Long) {
						args[0] = Integer.parseInt(value.toString());
					} else {
						args[0] = value;
					}

					try {
						descriptor.getWriteMethod().invoke(obj, args);
					} catch (InvocationTargetException e) {
						System.out.println("字段映射失败");
					}
				}
			}
		} catch (IllegalAccessException e) {
			System.out.println("实例化 JavaBean 失败");
		} catch (IntrospectionException e) {
			System.out.println("分析类属性失败");
		} catch (IllegalArgumentException e) {
			System.out.println("字段类型映射错误");
		} catch (InstantiationException e) {
			System.out.println("实例化 JavaBean 失败");
		}
		return (T) obj;
	}

	public static Map<String, Object> objectToMap(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}

		Map<String, Object> map = new HashMap<String, Object>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(obj));
		}

		return map;
	}

	/**  
	 * Map转换成Xml  
	 * @param map  
	 * @return  
	 */
	public static String map2Xmlstring(Map<String, Object> map) {
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");

		Set<String> set = map.keySet();
		for (Iterator<String> it = set.iterator(); it.hasNext();) {
			String key = it.next();
			Object value = map.get(key);
			sb.append("<").append(key).append(">");
			sb.append(value);
			sb.append("</").append(key).append(">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**  
	 * Xml string转换成Map  
	 * @param xmlStr  
	 * @return  
	 */
	public static Map<String, Object> xmlString2Map(String xmlStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		Document doc;
		try {
			doc = DocumentHelper.parseText(xmlStr);
			Element el = doc.getRootElement();
			map = recGetXmlElementValue(el, map);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	/**  
	 * 循环解析xml  
	 * @param ele  
	 * @param map  
	 * @return  
	 */
	@SuppressWarnings({ "unchecked" })
	private static Map<String, Object> recGetXmlElementValue(Element ele, Map<String, Object> map) {
		List<Element> eleList = ele.elements();
		if (eleList.size() == 0) {
			map.put(ele.getName(), ele.getTextTrim());
			return map;
		} else {
			for (Iterator<Element> iter = eleList.iterator(); iter.hasNext();) {
				Element innerEle = iter.next();
				recGetXmlElementValue(innerEle, map);
			}
			return map;
		}
	}

	/**
	 * 根据List<Map<String, Object>>数据转换为List<JavaBean>数据
	 * @param datas 
	 * @param beanClass
	 * @return List<T>
	 * @throws CommonException
	 */
	public List<T> ListMap2ListJavaBean(List<Map<String, Object>> datas, Class<T> beanClass) throws Exception {
		// 返回数据集合
		List<T> list = new ArrayList<T>();
		// 对象字段名称
		String fieldname = "";
		// 对象方法名称
		String methodname = "";
		// 对象方法需要赋的值
		Object methodsetvalue = "";
		// 得到对象所有字段
		Field fields[] = beanClass.getDeclaredFields();
		// 遍历数据
		for (Map<String, Object> mapdata : datas) {
			// 创建一个泛型类型实例
			T t = beanClass.newInstance();
			// 遍历所有字段，对应配置好的字段并赋值
			for (Field field : fields) {
				if (null != field) {
					// 全部转化为大写
					String dbfieldname = field.getName().toUpperCase();
					// 获取字段名称
					fieldname = field.getName();
					// 拼接set方法
					methodname = "set" + MapUtil.capitalize(fieldname);
					// 获取data里的对应值
					methodsetvalue = mapdata.get(dbfieldname);
					// 赋值给字段
					Method m = beanClass.getDeclaredMethod(methodname, field.getType());
					m.invoke(t, methodsetvalue);
				}
			}
			// 存入返回列表
			list.add(t);
		}
		// 返回
		return list;
	}

	/**
	 * 根据List<Map<String, Object>>数据转换为Set<JavaBean>数据
	 * @param datas 
	 * @param beanClass
	 * @return Set<T>
	 * @throws CommonException
	 */
	public Set<T> ListMap2SetJavaBean(List<Map<String, Object>> datas, Class<T> beanClass) throws Exception {
		// 返回数据集合
		Set<T> set = new HashSet<T>();
		// 对象字段名称
		String fieldname = "";
		// 对象方法名称
		String methodname = "";
		// 对象方法需要赋的值
		Object methodsetvalue = "";
		// 得到对象所有字段
		Field fields[] = beanClass.getDeclaredFields();
		// 遍历数据
		for (Map<String, Object> mapdata : datas) {
			// 创建一个泛型类型实例
			T t = beanClass.newInstance();
			// 遍历所有字段，对应配置好的字段并赋值
			for (Field field : fields) {
				if (null != field) {
					// 全部转化为大写
					String dbfieldname = field.getName().toUpperCase();
					// 获取字段名称
					fieldname = field.getName();
					// 拼接set方法
					methodname = "set" + MapUtil.capitalize(fieldname);
					// 获取data里的对应值
					methodsetvalue = mapdata.get(dbfieldname);
					// 赋值给字段
					Method m = beanClass.getDeclaredMethod(methodname, field.getType());
					m.invoke(t, methodsetvalue);
				}
			}
			// 存入返回列表
			set.add(t);
		}
		// 返回
		return set;
	}

	/**
	 * 根据List<PO>对象转List<VO>对象
	 * 
	 * @param poList
	 * @param voClass
	 * @return List<VO>
	 * @throws Exception
	 */
	public List<T> copyList(List<? extends Object> poList, Class<T> voClass) throws Exception {
		ArrayList<T> voList = new ArrayList<>();
		T voObj = null;
		for (Object poObj : poList) {
			voObj = voClass.newInstance();
			BeanUtils.copyProperties(poObj, voObj);
			voList.add(voObj);
		}
		return voList;
	}

	/**
	 * 根据List<PO>对象转Set<VO>对象
	 * 
	 * @param poList
	 * @param voClass
	 * @return Set<VO>
	 * @throws Exception
	 */
	public Set<T> copySet(List<? extends Object> poList, Class<T> voClass) throws Exception {
		Set<T> voSet = new HashSet<>();
		T voObj = null;
		for (Object poObj : poList) {
			voObj = voClass.newInstance();
			BeanUtils.copyProperties(poObj, voObj);
			voSet.add(voObj);
		}
		return voSet;
	}

	/**
	 * 字符串第一个字母大写
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuilder(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	
	/**
     * 将 List<JavaBean>对象转化为List<Map>
     * @param beanList
     * @return
     * @throws Exception
     */
    public static <T> List<Map<String, Object>> convertListBean2ListMap(List<T> beanList, Class<T> T)
        throws Exception
    {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (int i = 0, n = beanList.size(); i < n; i++)
        {
            Object bean = beanList.get(i);
            Map<String, Object> map = objectToMap(bean);
            mapList.add(map);
        }
        return mapList;
    }

	/**
	 　　* 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
	 　　* @param params 需要排序并参与字符拼接的参数组
	 　　* @return 拼接后字符串
	 　　* @throws UnsupportedEncodingException
	 */
	public static String createLinkStringByGet(Map<String, String> params) throws UnsupportedEncodingException {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);//排序
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
//			value = URLEncoder.encode(value, "utf-8");
			if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}
}
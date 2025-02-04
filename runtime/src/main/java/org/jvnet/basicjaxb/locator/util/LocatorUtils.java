package org.jvnet.basicjaxb.locator.util;

import org.jvnet.basicjaxb.locator.PropertyObjectLocator;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.jvnet.basicjaxb.locator.ItemObjectLocator;
import org.jvnet.basicjaxb.locator.ObjectLocator;
import org.xml.sax.Locator;

public class LocatorUtils
{
	private LocatorUtils()
	{
	}

	public static String getLocation(Locator locator)
	{
		if (locator == null)
			return "<unknown>";
		else
			return locator.getPublicId() + ":" + locator.getSystemId() + ":" + locator.getLineNumber() + ":" + locator.getColumnNumber();
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, Object value)
	{
		return locator == null ? null : locator.property(name, value);
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, boolean value)
	{
		return locator == null ? null : locator.property(name, Boolean.valueOf(value));
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, byte value)
	{
		return locator == null ? null : locator.property(name, Byte.valueOf(value));
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, char value)
	{
		return locator == null ? null : locator.property(name, Character.valueOf(value));
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, double value)
	{
		return locator == null ? null : locator.property(name, Double.valueOf(value));
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, float value)
	{
		return locator == null ? null : locator.property(name, Float.valueOf(value));
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, int value)
	{
		return locator == null ? null : locator.property(name, Integer.valueOf(value));
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, long value)
	{
		return locator == null ? null : locator.property(name, Long.valueOf(value));
	}

	public static PropertyObjectLocator property(ObjectLocator locator, String name, short value)
	{
		return locator == null ? null : locator.property(name, Short.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, Object value)
	{
		return locator == null ? null : locator.item(index, value);
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, boolean value)
	{
		return locator == null ? null : locator.item(index, Boolean.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, byte value)
	{
		return locator == null ? null : locator.item(index, Byte.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, char value)
	{
		return locator == null ? null : locator.item(index, Character.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, double value)
	{
		return locator == null ? null : locator.item(index, Double.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, float value)
	{
		return locator == null ? null : locator.item(index, Float.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, int value)
	{
		return locator == null ? null : locator.item(index, Integer.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, long value)
	{
		return locator == null ? null : locator.item(index, Long.valueOf(value));
	}

	public static ItemObjectLocator item(ObjectLocator locator, int index, short value)
	{
		return locator == null ? null : locator.item(index, Short.valueOf(value));
	}
	
	public static boolean isPublic(Method method)
	{
		return (method != null) && ( (method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC);
	}
	
	public static int countPublicProperties(Class<?> clazz)
	{
	    int count = 0;
	    Method[] declaredMethods = clazz.getDeclaredMethods();
	    
	    Map<String,Method> methodMap = new HashMap<>();
	    for ( Method method : declaredMethods )
	    	methodMap.put(method.getName(), method);
	    
	    for ( Method method : declaredMethods )
	    {
	    	if ( isPublic(method) ) 
	    	{
		        String methodName = method.getName();
		        if ( methodName.startsWith("get") )
		        {
		        	String mutatorName = methodName.replaceFirst("get", "set");
		        	if ( isPublic(methodMap.get(mutatorName)) )
		        		++count;
		        }
		        else if ( methodName.startsWith("is") )
		        {
		        	String mutatorName = methodName.replaceFirst("is", "set");
		        	if ( isPublic(methodMap.get(mutatorName)) )
		        		++count;
		        }
	    	}
	    }
	    return count;
	}
}

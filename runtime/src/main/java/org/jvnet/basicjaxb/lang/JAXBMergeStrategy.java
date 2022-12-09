package org.jvnet.basicjaxb.lang;

import java.util.Collection;

import org.jvnet.basicjaxb.locator.ObjectLocator;

public class JAXBMergeStrategy extends DefaultMergeStrategy
{
	private static final JAXBMergeStrategy INSTANCE = new JAXBMergeStrategy();
	public static JAXBMergeStrategy getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, Object left, Object right) {
		if (left instanceof Collection && right instanceof Collection) {
			@SuppressWarnings("rawtypes")
			Collection leftCollection = (Collection) left;
			@SuppressWarnings("rawtypes")
			Collection rightCollection = (Collection) right;
			return mergeInternal(leftLocator, rightLocator, leftCollection,
					rightCollection);
		} else {
			return super.mergeInternal(leftLocator, rightLocator, left, right);
		}
	}

	protected Object mergeInternal(ObjectLocator leftLocator,
			ObjectLocator rightLocator, @SuppressWarnings("rawtypes") Collection leftCollection,
			@SuppressWarnings("rawtypes") Collection rightCollection) {
		return !leftCollection.isEmpty() ? leftCollection : rightCollection;
	}
}
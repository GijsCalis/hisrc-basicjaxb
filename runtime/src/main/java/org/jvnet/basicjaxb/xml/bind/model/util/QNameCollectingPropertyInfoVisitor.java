package org.jvnet.basicjaxb.xml.bind.model.util;

import javax.xml.namespace.QName;

import org.jvnet.basicjaxb.lang.Validate;
import org.jvnet.basicjaxb.xml.bind.model.MAnyAttributePropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MAnyElementPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MElement;
import org.jvnet.basicjaxb.xml.bind.model.MElementPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MElementRefPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MElementTypeRef;
import org.jvnet.basicjaxb.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MPropertyInfoVisitor;
import org.jvnet.basicjaxb.xml.bind.model.MValuePropertyInfo;

public class QNameCollectingPropertyInfoVisitor<T, C extends T> implements
		MPropertyInfoVisitor<T, C, Void> {

	private final QNameCollector collector;

	public QNameCollectingPropertyInfoVisitor(QNameCollector collector) {
		Validate.notNull(collector);
		this.collector = collector;
	}

	public Void visitElementPropertyInfo(MElementPropertyInfo<T, C> info) {
		QName wrapperElementName = info.getWrapperElementName();
		if (wrapperElementName != null) {
			collector.element(wrapperElementName);
		}
		QName elementName = info.getElementName();
		collector.element(elementName);
		return null;
	}

	public Void visitElementsPropertyInfo(MElementsPropertyInfo<T, C> info) {
		QName wrapperElementName = info.getWrapperElementName();
		if (wrapperElementName != null) {
			collector.element(wrapperElementName);
		}
		for (MElementTypeRef<T, C> elementTypeInfo : info.getElementTypeInfos()) {
			QName elementName = elementTypeInfo.getElementName();
			collector.element(elementName);
		}
		return null;
	}

	public Void visitAnyElementPropertyInfo(MAnyElementPropertyInfo<T, C> info) {
		return null;
	}

	public Void visitAttributePropertyInfo(MAttributePropertyInfo<T, C> info) {
		collector.attribute(info.getAttributeName());
		return null;
	}

	public Void visitAnyAttributePropertyInfo(
			MAnyAttributePropertyInfo<T, C> info) {
		return null;
	}

	public Void visitValuePropertyInfo(MValuePropertyInfo<T, C> info) {
		return null;
	}

	public Void visitElementRefPropertyInfo(MElementRefPropertyInfo<T, C> info) {
		QName wrapperElementName = info.getWrapperElementName();
		if (wrapperElementName != null) {
			collector.element(wrapperElementName);
		}
		QName elementName = info.getElementName();
		collector.element(elementName);
		return null;
	}

	public Void visitElementRefsPropertyInfo(MElementRefsPropertyInfo<T, C> info) {
		QName wrapperElementName = info.getWrapperElementName();
		if (wrapperElementName != null) {
			collector.element(wrapperElementName);
		}
		for (MElement<T, C> elementTypeInfo : info.getElementTypeInfos()) {
			QName elementName = elementTypeInfo.getElementName();
			collector.element(elementName);
		}
		return null;
	}
}

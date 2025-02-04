package org.jvnet.basicjaxb.util;

import org.jvnet.basicjaxb.xjc.outline.FieldAccessorEx;

import com.sun.codemodel.JExpression;
import com.sun.tools.xjc.outline.FieldOutline;

public interface FieldAccessorFactory {

	public FieldAccessorEx createFieldAccessor(FieldOutline fieldOutline,
			JExpression targetObject);
}

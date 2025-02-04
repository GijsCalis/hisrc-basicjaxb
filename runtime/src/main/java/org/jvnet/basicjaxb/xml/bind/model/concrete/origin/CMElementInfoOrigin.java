package org.jvnet.basicjaxb.xml.bind.model.concrete.origin;

import org.jvnet.basicjaxb.lang.Validate;
import org.jvnet.basicjaxb.xml.bind.model.origin.MElementInfoOrigin;

import org.glassfish.jaxb.core.v2.model.core.ElementInfo;

public class CMElementInfoOrigin<T, C, EI extends ElementInfo<T, C>> implements
		MElementInfoOrigin, ElementInfoOrigin<T, C, EI> {

	private final EI source;

	public CMElementInfoOrigin(EI source) {
		Validate.notNull(source);
		this.source = source;
	}

	public EI getSource() {
		return source;
	}

}

package org.jvnet.basicjaxb.tests.one;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.File;

import org.jvnet.basicjaxb.lang.CopyStrategy;
import org.jvnet.basicjaxb.lang.HashCodeStrategy;
import org.jvnet.basicjaxb.lang.JAXBCopyStrategy;
import org.jvnet.basicjaxb.lang.JAXBHashCodeStrategy;
import org.jvnet.basicjaxb.locator.DefaultRootObjectLocator;
import org.jvnet.basicjaxb.test.AbstractSamplesTest;
import org.jvnet.basicjaxb.util.ClassUtils;

import jakarta.xml.bind.JAXBElement;

public class HashCodeTest extends AbstractSamplesTest
{
	@Override
	protected void checkSample(File sample) throws Exception
	{
		final Object lhs = createContext().createUnmarshaller().unmarshal(sample);
		final Object rhs = createContext().createUnmarshaller().unmarshal(sample);
		
		DefaultRootObjectLocator lhsLocator = new DefaultRootObjectLocator(lhs);
		DefaultRootObjectLocator rhsLocator = new DefaultRootObjectLocator(rhs);
		
		CopyStrategy copyStategy = JAXBCopyStrategy.getInstance();
		
		final Object chs = copyStategy.copy(lhsLocator, rhs, true);
		
		DefaultRootObjectLocator chsLocator = new DefaultRootObjectLocator(chs);

		String lhsIdentity = ClassUtils.identify(lhs);
		String rhsIdentity = ClassUtils.identify(rhs);
		String chsIdentity = ClassUtils.identify(chs);
		
		assertNotEquals(lhsIdentity, rhsIdentity, "Unmarshaling creates unique objects.");
		assertNotEquals(lhsIdentity, chsIdentity, "Copy creates new instance.");
		assertNotEquals(rhsIdentity, chsIdentity, "Copy creates new instance.");
		
		HashCodeStrategy hashStrategy = JAXBHashCodeStrategy.getInstance();
		
		final int leftHashCode = hashStrategy.hashCode(lhsLocator, 0, lhs, true);
		final int rightHashCode = hashStrategy.hashCode(rhsLocator, 0, rhs, true);
		final int copyHashCode = hashStrategy.hashCode(chsLocator, 0, chs,true);
		
		assertEquals(leftHashCode, rightHashCode, "Hash values must be equal.");
		assertEquals(leftHashCode, copyHashCode, "Hash values must be equal.");
		
		// Deeper Analysis
		if ( lhs instanceof JAXBElement && rhs instanceof JAXBElement && chs instanceof JAXBElement )
		{
			JAXBElement<?> lhsJAXBElement = (JAXBElement<?>) lhs;
			JAXBElement<?> rhsJAXBElement = (JAXBElement<?>) rhs;
			JAXBElement<?> chsJAXBElement = (JAXBElement<?>) chs;
			
			Object lhsValue = lhsJAXBElement.getValue();
			Object rhsValue = rhsJAXBElement.getValue();
			Object chsValue = chsJAXBElement.getValue();
			
			String lhsValueIdentity = ClassUtils.identify(lhsValue);
			String rhsValueIdentity = ClassUtils.identify(rhsValue);
			String chsValueIdentity = ClassUtils.identify(chsValue);
			
			assertNotEquals(lhsValueIdentity, rhsValueIdentity, "Unmarshaling creates unique value objects.");
			assertNotEquals(lhsValueIdentity, chsValueIdentity, "Copy creates new instance of value.");
			assertNotEquals(rhsValueIdentity, chsValueIdentity, "Copy creates new instance of value.");
		}
	}
}

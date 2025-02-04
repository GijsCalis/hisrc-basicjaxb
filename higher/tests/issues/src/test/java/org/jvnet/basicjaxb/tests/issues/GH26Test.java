package org.jvnet.basicjaxb.tests.issues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jvnet.basicjaxb.xjc.model.concrete.XJCCMInfoFactory;
import org.jvnet.basicjaxb.xml.bind.model.MAttributePropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MClassInfo;
import org.jvnet.basicjaxb.xml.bind.model.MElementPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MElementRefsPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MElementsPropertyInfo;
import org.jvnet.basicjaxb.xml.bind.model.MModelInfo;

import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.ConsoleErrorReporter;
import com.sun.tools.xjc.ModelLoader;
import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.model.Model;
import com.sun.tools.xjc.model.nav.NClass;
import com.sun.tools.xjc.model.nav.NType;

public class GH26Test
{
	@BeforeEach
	public void setUp()
	{
		System.setProperty("javax.xml.accessExternalSchema", "all");
	}

	@Test
	public void compilesSchema() throws Exception
	{
		new File("target/generated-sources/xjc").mkdirs();

		URL schema = getClass().getResource("/schema.xsd");
		URL binding = getClass().getResource("/binding.xjb");
		final String[] arguments = new String[] { "-xmlschema",
				schema.toExternalForm(), "-b", binding.toExternalForm(), "-d",
				"target/generated-sources/xjc", "-extension", "-XhashCode",
				"-Xequals", "-XtoString", "-Xcopyable", "-Xmergeable",
				"-Xinheritance", "-Xsetters", "-Xsetters-mode=direct",
				"-Xwildcard", "-XenumValue"
		// "-XsimpleToString"

		};

		Options options = new Options();
		options.parseArguments(arguments);
		ConsoleErrorReporter receiver = new ConsoleErrorReporter();
		Model model = ModelLoader.load(options, new JCodeModel(), receiver);
		final XJCCMInfoFactory factory = new XJCCMInfoFactory(model);
		final MModelInfo<NType, NClass> mmodel = factory.createModel();

		final MClassInfo<NType, NClass> classInfo =
			mmodel.getClassInfo("org.jvnet.basicjaxb.tests.issues.IssueGH26Type");
		assertNotNull(classInfo);

		final MElementPropertyInfo<NType, NClass> a =
			(MElementPropertyInfo<NType, NClass>) classInfo.getProperty("a");
		assertEquals("a", a.getDefaultValue());
		assertNotNull(a.getDefaultValueNamespaceContext());
		final MElementsPropertyInfo<NType, NClass> bOrC =
			(MElementsPropertyInfo<NType, NClass>) classInfo.getProperty("bOrC");
		
//		assertEquals("b", bOrC.getElementTypeInfos().get(0).getDefaultValue());
		assertNotNull(bOrC.getElementTypeInfos().get(0).getDefaultValueNamespaceContext());
		
//		assertEquals("3", bOrC.getElementTypeInfos().get(1).getDefaultValue());
		assertNotNull(bOrC.getElementTypeInfos().get(1).getDefaultValueNamespaceContext());

		final MElementRefsPropertyInfo<NType, NClass> dOrE =
			(MElementRefsPropertyInfo<NType, NClass>) classInfo.getProperty("dOrE");
//		assertEquals("e", dOrE.getElementTypeInfos().get(0).getDefaultValue());
		assertNotNull(dOrE.getElementTypeInfos().get(0).getDefaultValueNamespaceContext());
		
//		assertEquals("d", dOrE.getElementTypeInfos().get(1).getDefaultValue());
		assertNotNull(dOrE.getElementTypeInfos().get(1).getDefaultValueNamespaceContext());

		final MAttributePropertyInfo<NType, NClass> z =
			(MAttributePropertyInfo<NType, NClass>) classInfo.getProperty("z");
		assertEquals("z", z.getDefaultValue());
		assertNotNull(z.getDefaultValueNamespaceContext());
		// model.generateCode(options, receiver);
		// com.sun.codemodel.CodeWriter cw = options.createCodeWriter();
		// model.codeModel.build(cw);
	}
}

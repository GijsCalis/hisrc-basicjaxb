package org.jvnet.jaxb2_commons.test;

import java.io.File;
import java.util.Collection;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Assert;
import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractSamplesTest extends TestCase {

	protected Logger logger = LoggerFactory.getLogger(getTestClass());

	protected String getContextPath() {
		return getTestClass().getPackage().getName();
	}

	protected abstract void checkSample(File sample) throws Exception;

	public void testSamples() throws Exception {
		logger.debug("Testing samples.");
		int failed = 0;
		final File[] sampleFiles = getSampleFiles();
		for (final File sampleFile : sampleFiles) {
			logger.debug("Testing sample [" + sampleFile.getName() + "].");
			try {
				checkSample(sampleFile);
			} catch (Throwable ex) {
				logger.error("Sample [" + sampleFile.getName()
						+ "] failed the check.", ex);
				failed++;
			}
			logger.debug("Finished testing sample [" + sampleFile.getName()
					+ "].");
		}
		logger.debug("Finished testing samples.");

		Assert.assertTrue("Summary [" + failed + "/" + sampleFiles.length
				+ "] failed the check. Use DEBUG level. Check previous errors for details.", failed == 0);
	}

	protected File getBaseDir() {
		try {
			return (new File(getTestClass().getProtectionDomain()
					.getCodeSource().getLocation().getFile())).getParentFile()
					.getParentFile().getAbsoluteFile();
		} catch (Exception ex) {
			throw new AssertionError(ex);
		}
	}

	protected Class<? extends Object> getTestClass() {
		return getClass();
	}

	protected File getSamplesDirectory() {
		return new File(getBaseDir(), getSamplesDirectoryName());
	}

	public static final String DEFAULT_SAMPLES_DIRECTORY_NAME = "src/test/samples";

	protected String getSamplesDirectoryName() {
		return DEFAULT_SAMPLES_DIRECTORY_NAME;
	}

	protected File[] getSampleFiles() {
		File samplesDirectory = getSamplesDirectory();
		logger.debug("Sample directory [" + samplesDirectory.getAbsolutePath()
				+ "].");
		final Collection<File> files = FileUtils.listFiles(samplesDirectory,
				new String[] { "xml" }, true);
		return files.toArray(new File[files.size()]);
	}

	protected ClassLoader getContextClassLoader() {
		return getTestClass().getClassLoader();
	}

	protected Map<String, ?> getContextProperties() {
		return null;
	}

	public JAXBContext createContext() throws JAXBException {
		final String contextPath = getContextPath();
		final ClassLoader classLoader = getContextClassLoader();
		final Map<String, ?> properties = getContextProperties();
		if (classLoader == null) {
			return JAXBContext.newInstance(contextPath);
		} else {
			if (properties == null) {
				return JAXBContext.newInstance(contextPath, classLoader);
			} else {
				return JAXBContext.newInstance(contextPath, classLoader,
						properties);
			}
		}
	}
}

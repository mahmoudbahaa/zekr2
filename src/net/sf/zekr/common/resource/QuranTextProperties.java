/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Oct 5, 2004
 */

package net.sf.zekr.common.resource;

import net.sf.zekr.common.config.ApplicationConfig;
import net.sf.zekr.common.config.ZekrConfigNaming;
import net.sf.zekr.engine.xml.XmlReader;
import net.sf.zekr.engine.xml.XmlUtils;

import org.w3c.dom.Element;

/**
 * This class consists of detail of the quran text file located at
 * <code>ApplicationPath.QURAN_TEXT</code>.<br>
 * <code>QuranTextConfigNaming</code> is not really a base class, but it is extended so
 * that final <code>String</code>s in that class can be easily used.
 * 
 * @author Mohsen Saboorian
 * @since Zekr 1.0
 * @see TODO
 * @version 0.1
 */
final public class QuranTextProperties extends QuranTextConfigNaming {

	private static QuranTextProperties thisInstance = null;
	private ApplicationConfig appConfig = ApplicationConfig.getInsatnce();
	private XmlReader reader = null;

	private QuranTextProperties() {
		reader = new XmlReader(appConfig.getConfigFile(ZekrConfigNaming.QURAN_CONFIG_ID));
	}

	public static QuranTextProperties getInstance() {
		if (thisInstance == null)
			thisInstance = new QuranTextProperties();
		return thisInstance;
	}

	public String getSooraStartSign() {
		return XmlUtils.getAttr(reader.getNode(SOORA_TITLE), START_STRING_ATTR);
	}

	public boolean hasBismillah() {
		return Boolean.getBoolean(XmlUtils.getAttr(reader.getNode(BIMILLAH), EXIST_ATTR));
	}

	/**
	 * @return a regular expression matches the aya delimiter signs and numbers (if any)
	 */
	public String getAyaDelimiter() {
		String pattern = "\\d+"; // a digit one or more times.

		String leftAya = getAyaSignLeftString();
		String rightAya = getAyaSignRightString();
		return rightAya + pattern + leftAya;
	}

	public String getSooraNumberLeftString() {
		return reader.getElement(SOORA_SIGN).getAttribute(LEFT_STRING_ATTR);
	}

	/**
	 * @return the left aya delimiter string
	 */
	public String getAyaSignLeftString() {
		return reader.getElement(AYA_SIGN).getAttribute(LEFT_STRING_ATTR);
	}

	/**
	 * @return the right aya delimiter string
	 */
	public String getAyaSignRightString() {
		return reader.getElement(AYA_SIGN).getAttribute(RIGHT_STRING_ATTR);
	}

	public String getLineBreakString() {
		String style = reader.getElement(TEXT_FILE).getAttribute(LINE_BREAK_ATTR);
		if (style.equalsIgnoreCase("unix") || style.equalsIgnoreCase("linux"))
			return "\n";
		if (style.equalsIgnoreCase("pc") || style.equalsIgnoreCase("windows")
				|| style.equalsIgnoreCase("win32"))
			return "\r\n";
		if (style.equalsIgnoreCase("mac") || style.equalsIgnoreCase("macintosh"))
			return "\r";
		return null;
	}

	public String getCharset() {
		return reader.getElement(TEXT_FILE).getAttribute(CHARSET_ATTR);
	}

	public String getMinorSujdaSign() {
		return getSujdaSign(false);
	}

	public String getMajorSujdaSign() {
		return getSujdaSign(true);
	}

	public String getJozRightString() {
		return reader.getElement(JOZ_SIGN).getAttribute(RIGHT_STRING_ATTR);
	}

	public String getJozLeftString() {
		return reader.getElement(JOZ_SIGN).getAttribute(LEFT_STRING_ATTR);
	}

	public String getJozValue() {
		return reader.getElement(JOZ_SIGN).getAttribute(VALUE_ATTR);
	}

	/**
	 * @return the regular expression corresponding to the Quran joz sign
	 */
	public String getJozRegex() {
		String value = getJozValue().equalsIgnoreCase(DIGIT_VALUE) ? "\\d+" : getJozValue();
		return "\\" + getJozRightString() + value + "\\" + getJozLeftString();
	}

	/**
	 * @param type
	 *            <code>true</code> is considered as <code>MAJOR_SUJDA</code> and
	 *            <code>false</code> is considered as <code>MINOR_SUJDA</code>
	 * @return Vajib or Mustahab Sujda sign
	 */
	private String getSujdaSign(boolean type) {
		Element elem = XmlUtils.getElementByNamedAttr(reader.getNodes(SUJDA_SIGN), SUJDA_TAG, TYPE_ATTR,
			type ? MAJOR_SUJDA : MINOR_SUJDA);
		return elem.getAttribute(RIGHT_STRING_ATTR) + elem.getAttribute(VALUE_ATTR)
				+ elem.getAttribute(LEFT_STRING_ATTR);
	}

}
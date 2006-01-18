/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Feb 17, 2005
 */
package net.sf.zekr.common.resource;

/**
 * This class consists of tags, attributes and values of attributes of the quran text XML
 * config file (<code>quran-text-config.xml</code>).
 * 
 * @author Mohsen Saboorian
 * @since Zekr 1.0
 * @version 0.1
 */
public class QuranTextConfigNaming {
	public static final String TEXT_FILE = "text-file";
	public static final String SURA_TITLE = "text-notation.sura.title";
	public static final String BIMILLAH = "text-notation.sura.bismillah";
	public static final String AYA_SIGN = "text-notation.aya.sign";
	public static final String SURA_SIGN = "text-notation.sura.title.sign";
	public static final String SAJDA_SIGN = "text-notation.sajda.sign";
	public static final String JUZ_SIGN = "text-notation.juz.sign";

	public static final String SAJDA_TAG = "sajda";
	/** Mustahab sajda */
	public static final String MINOR_SAJDA = "minor";
	/** Vajib sajda */
	public static final String MAJOR_SAJDA = "major";

	public static final String RIGHT_STRING_ATTR = "rightString";
	public static final String LEFT_STRING_ATTR = "leftString";
	public static final String START_STRING_ATTR = "startString";
	public static final String LINE_BREAK_ATTR = "lineBreak";
	public static final String VALUE_ATTR = "value";
	public static final String TYPE_ATTR = "type";
	public static final String EXIST_ATTR = "exist";
	public static final String CHARSET_ATTR = "charset";
	public static final String DIGIT_VALUE = "{digit}";

}

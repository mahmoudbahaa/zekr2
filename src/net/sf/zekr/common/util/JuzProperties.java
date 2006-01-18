/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Mar 20, 2005
 */
package net.sf.zekr.common.util;

import net.sf.zekr.engine.language.LanguageEngine;

/**
 * @author Mohsen Saboorian
 * @since Zekr 1.0
 * @version 0.1
 */
public class JuzProperties {
	private int index;

	/** juz start sura number */
	private int suraNumber;

	/** juz start aya number */
	private int ayaNumber;

	/**
	 * @return Returns the ayaNumber.
	 */
	public int getAyaNumber() {
		return ayaNumber;
	}

	/**
	 * @param ayaNumber
	 *            The ayaNumber to set.
	 */
	public void setAyaNumber(int ayaNumber) {
		this.ayaNumber = ayaNumber;
	}

	/**
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return Returns the suraNumber.
	 */
	public int getSuraNumber() {
		return suraNumber;
	}

	/**
	 * @param suraNumber
	 *            The suraNumber to set.
	 */
	public void setSuraNumber(int suraNumber) {
		this.suraNumber = suraNumber;
	}

	public String toString() {
		return LanguageEngine.getInstance().getMeaning("JUZ") + ": " + getIndex();
	}
}
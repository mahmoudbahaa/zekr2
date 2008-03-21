/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Mar 21, 2008
 */
package net.sf.zekr.engine.search.tanzil;

import java.util.List;

import net.sf.zekr.engine.search.SearchResultItem;

/**
 * Calculate search result items' score based on a simple formula:<br />
 * <code>sum of (len(part) / len(SearchResultItem.ayaText) ^ 2), for all <i>parts</i> in
 * <i>SearchResultItem.matchedParts</i></code>
 * <p />
 * Result items which are matched because of exclusion term in query are scored 0.
 * 
 * @author Mohsen Saboorian
 */
class DefaultSearchScorer implements ISearchScorer {
	public double score(SearchResultItem sri) {
		double score = 0;
		List parts = sri.matchedParts;
		if (parts == null) // result item matched because of exlusion
			return 0;
		for (int i = 0; i < parts.size(); i++) {
			String part = (String) parts.get(i);
			int len = sri.ayaText.length();
			score += ((double) part.length()) / (len * len);
		}
		return score;
	}
}
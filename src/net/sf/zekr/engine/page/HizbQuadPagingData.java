/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Jun 27, 2008
 */
package net.sf.zekr.engine.page;

import java.util.ArrayList;
import java.util.List;

import net.sf.zekr.common.resource.JuzProperties;
import net.sf.zekr.common.resource.QuranLocation;
import net.sf.zekr.common.resource.QuranProperties;
import net.sf.zekr.common.resource.QuranPropertiesUtils;
import net.sf.zekr.common.resource.SuraProperties;

/**
 * This class holds paging data of type hizb quad, which means each page contains a single hizb quad of the
 * Quran.
 * 
 * @author Mohsen Saboorian
 */
public class HizbQuadPagingData extends AbstractQuranPagingData {
	public HizbQuadPagingData() {
		this.name = meaning("HIZB");
		this.id = "<hizb>";

		pageList = new ArrayList();
		List juzList = QuranProperties.getInstance().getJuzList();

		for (int i = 0; i < juzList.size(); i++) {
			JuzProperties juz = (JuzProperties) juzList.get(i);
			QuranLocation[] hizbQuads = juz.getHizbQuarters();
			QuranPage prevPage = null;
			for (int j = 0; j < hizbQuads.length; j++) {
				QuranPage page = new QuranPage();
				page.setIndex(i * 4 + j + 1);
				page.setFrom(hizbQuads[j]);
				if (prevPage != null)
					prevPage.setTo(page.getFrom().getPrev());
				prevPage = page;
				pageList.add(page);
			}
			SuraProperties lastSura = QuranPropertiesUtils.getSura(QuranPropertiesUtils.QURAN_SURA_COUNT);
			prevPage.setTo(new QuranLocation(QuranPropertiesUtils.QURAN_SURA_COUNT, lastSura.getAyaCount()));
		}
	}
}
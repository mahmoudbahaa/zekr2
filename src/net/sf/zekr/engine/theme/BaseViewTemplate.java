/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Feb 23, 2006
 */
package net.sf.zekr.engine.theme;

import org.apache.velocity.tools.generic.MathTool;

import net.sf.zekr.common.config.ApplicationConfig;
import net.sf.zekr.common.config.ApplicationPath;
import net.sf.zekr.common.config.GlobalConfig;
import net.sf.zekr.common.config.ResourceManager;
import net.sf.zekr.common.runtime.Naming;
import net.sf.zekr.common.util.UriUtils;
import net.sf.zekr.engine.language.LanguageEngine;
import net.sf.zekr.engine.log.Logger;

/**
 * @author Mohsen Saboorian
 * @since Zekr 1.0
 */
public abstract class BaseViewTemplate implements ITransformer {
	protected final Logger logger = Logger.getLogger(this.getClass());

	protected TemplateEngine engine = TemplateEngine.getInstance();
	protected ApplicationConfig config = ApplicationConfig.getInstance();
	protected ResourceManager resource = ResourceManager.getInstance();
	protected LanguageEngine langEngine = config.getLanguageEngine();

	/**
	 * This method will generate the result string of a view.
	 * 
	 * @return the String representation of a view
	 */
	public abstract String transform();

	/**
	 * Will put some initial properties into context:
	 * <ul>
	 * <li><tt>DIRECTION</tt>: "rtl" or "ltr" based on the current language pack</li>
	 * <li><tt>TRANS_DIRECTION</tt>: "rtl" or "ltr" based on the current translation</li>
	 * <li><tt>APP_PATH</tt>: an absolute URI ending with slash</li>
	 * <li><tt>CSS_DIR</tt>: an absolute URI ending with slash</li>
	 * <li><tt>THEME_DIR</tt>: relative theme directory path</li>
	 * <li><tt>UI_DIR</tt>: relative UI directory path</li>
	 * </ul>
	 * It will also put all processed properties related to the current theme (<code>ThemeData.processedProps</code>).
	 */
	protected BaseViewTemplate() {
		ThemeData td = config.getTheme().getCurrent();
		engine.put("DIRECTION", langEngine.getDirection());
		engine.put("TRANS_DIRECTION", config.getTranslation().getDefault().direction);
		engine.put("APP_PATH", UriUtils.toURI(GlobalConfig.RUNTIME_DIR));
		engine.put("THEME_DIR", td.getPath());
		engine.put("UI_DIR", ApplicationPath.UI_DIR);
		engine.put("CSS_DIR", UriUtils.toURI(Naming.CACHE_DIR));
		engine.put("MATH", new MathTool());

		engine.putAll(td.processedProps);
	}
}
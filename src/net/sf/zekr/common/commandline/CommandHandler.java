/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Jul 15, 2007
 */
package net.sf.zekr.common.commandline;

import java.io.PrintStream;

public abstract class CommandHandler implements Command {
	protected String options[];
	protected String command;
	protected PrintStream stdout;
	protected PrintStream stderr;

	protected CommandHandler() {
		stdout = System.out;
		stderr = System.err;
	}

	abstract public void execute() throws CommandException;

	/* (non-Javadoc)
	 * @see net.sf.zekr.common.commandline.Command#launchAfter()
	 */
	public boolean launchAfter() {
		return false;
	}
}

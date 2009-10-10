/*
 *               In the name of Allah
 * This file is part of The Zekr Project. Use is subject to
 * license terms.
 *
 * Author:         Mohsen Saboorian
 * Start Date:     Mar 11, 2009
 */
package net.sf.zekr.engine.audio.ui;

import net.sf.zekr.common.config.ApplicationConfig;
import net.sf.zekr.common.resource.IQuranLocation;
import net.sf.zekr.engine.audio.AudioData;
import net.sf.zekr.engine.audio.PlayStatus;
import net.sf.zekr.engine.audio.PlayerController;
import net.sf.zekr.ui.BaseForm;
import net.sf.zekr.ui.QuranForm;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

/**
 * @author Mohsen Saboorian
 */
public class AudioControllerForm extends BaseForm {
	final ApplicationConfig config = ApplicationConfig.getInstance();
	public static final String FORM_ID = "AUDIO_CONTOLLER_FORM";

	private PlayerController playerController;
	private Button playPauseItem;
	private Button stopItem;
	private QuranForm quranForm;

	private Button prevItem;
	private Button nextItem;
	private Image volumeImage0;
	private Image volumeImage1;
	private Image volumeImage2;
	private Image volumeImage3;

	private boolean isLtr = true;
	private int volume = 50;
	private boolean multiAya = true;

	private Canvas volumeCanvas;
	private ProgressBar volumeProgressBar;
	private Composite middleRow;
	private Composite topRow;
	private Composite body;
	private Composite bottomRow;
	private Button contButton;
	private Image multiAyaImage;
	private Image singleAyaImage;
	private Image pauseImage;
	private Image playImage;
	private Image prevAyaImage;
	private Image nextAyaImage;
	private Image stopImage;
	private IQuranLocation quranLocation;
	private AudioData audioData;
	private Label playerLabel;

	public AudioControllerForm(QuranForm quranForm, Shell parent) {
		this.isLtr = config.getLanguageEngine().isLtr();
		this.audioData = config.getAudio().getCurrent();
		this.playerController = config.getPlayerController();
		this.volume = playerController.getVolume();
		this.multiAya = playerController.isMultiAya();
		this.quranLocation = config.getUserViewController().getLocation();

		this.quranForm = quranForm;
		this.parent = parent;
		this.display = parent.getDisplay();
		init();
	}

	private void init() {
		shell = new Shell(display, SWT.CLOSE | SWT.ON_TOP | SWT.TOOL | (isLtr ? SWT.LEFT_TO_RIGHT : SWT.RIGHT_TO_LEFT));

		FillLayout fl = new FillLayout();
		shell.setLayout(fl);
		shell.setText(meaning("TITLE"));

		fl = new FillLayout();
		body = new Composite(shell, SWT.NONE);
		RowLayout rl = getNewRowLayout(SWT.VERTICAL);
		rl.spacing = 1;
		body.setLayout(rl);

		RowData rd = new RowData();
		topRow = new Composite(body, SWT.NONE);
		topRow.setLayoutData(rd);

		new Label(body, SWT.SEPARATOR | SWT.HORIZONTAL);

		rd = new RowData();
		middleRow = new Composite(body, SWT.NONE);
		middleRow.setLayoutData(rd);

		new Label(body, SWT.SEPARATOR | SWT.HORIZONTAL);

		bottomRow = new Composite(body, SWT.NONE);

		cacheImages();

		createTopRow();
		createMiddleRow();
		createBottomRow();

		shell.pack();
	}

	private void cacheImages() {
		volumeImage0 = new Image(display, resource.getString("icon.player.vol0"));
		volumeImage1 = new Image(display, resource.getString("icon.player.vol1"));
		volumeImage2 = new Image(display, resource.getString("icon.player.vol2"));
		volumeImage3 = new Image(display, resource.getString("icon.player.vol3"));

		singleAyaImage = new Image(display, resource.getString("icon.player.singleAya"));
		multiAyaImage = new Image(display, resource.getString("icon.player.multiAya"));

		playImage = new Image(display, isLtr ? resource.getString("icon.player.play") : resource
				.getString("icon.player.playRtl"));
		pauseImage = new Image(display, resource.getString("icon.player.pause"));
		stopImage = new Image(display, resource.getString("icon.player.stop"));
		prevAyaImage = new Image(display, resource.getString("icon.player.prevAya"));
		nextAyaImage = new Image(display, resource.getString("icon.player.nextAya"));
	}

	private void createTopRow() {
		GridLayout gl = new GridLayout(1, false);
		gl.marginHeight = 2;
		topRow.setLayout(gl);
		playerLabel = new Label(topRow, SWT.NONE);
		updatePlayerLabel();
	}

	public void updatePlayerLabel() {
		playerLabel.setText("Al-Zumar(1):2 - Reciter: Al-Husary - Playing");
	}

	protected String getPlayStatus() {
		return "Playing";
	}

	private void createBottomRow() {
		GridData gd;
		GridLayout gl;

		bottomRow.setLayout(new FillLayout());

		gl = new GridLayout(6, false);
		gl.marginWidth = 4;
		gl.horizontalSpacing = 2;
		gl.verticalSpacing = gl.marginHeight = 0;
		Composite bottomComposite = new Composite(bottomRow, SWT.NONE);
		bottomComposite.setLayout(gl);

		Label repeatLabel = new Label(bottomComposite, SWT.NONE);
		repeatLabel.setText("Repeat:");

		Combo selection = new Combo(bottomComposite, SWT.READ_ONLY);
		selection.setItems(new String[] { "No repeat", "2", "2" });
		selection.select(0);

		gd = new GridData();
		gd.horizontalIndent = 10;
		Label waitLabel = new Label(bottomComposite, SWT.NONE);
		waitLabel.setLayoutData(gd);
		waitLabel.setText("Wait:");

		Combo waitCombo = new Combo(bottomComposite, SWT.READ_ONLY);
		waitCombo.setItems(new String[] { "0", "0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0" });
		waitCombo.select(0);

		Label secondsLabel = new Label(bottomComposite, SWT.NONE);
		secondsLabel.setText("s");
		secondsLabel.setToolTipText("Seconds");

		gd = new GridData(SWT.END, SWT.FILL, true, true);
		contButton = new Button(bottomComposite, SWT.PUSH);
		contButton.setToolTipText("Continuous");
		contButton.setLayoutData(gd);
		setContinuityImage(multiAya);
		contButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				multiAya = !multiAya;
				setContinuityImage(multiAya);
			}
		});

	}

	protected void setContinuityImage(boolean continious) {
		if (continious) {
			contButton.setImage(multiAyaImage);
		} else {
			contButton.setImage(singleAyaImage);
		}
	}

	private void createMiddleRow() {
		RowLayout rl;
		GridLayout gl;
		GridData gd;

		gl = new GridLayout(3, false);
		middleRow.setLayout(gl);

		gd = new GridData();
		gd.widthHint = 36;
		gd.heightHint = 36;
		playPauseItem = new Button(middleRow, SWT.PUSH);
		playPauseItem.setLayoutData(gd);
		playerTogglePlayPause(playerController.getStatus() == PlayerController.PLAYING);

		playPauseItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PlayStatus ps = (PlayStatus) ((Widget) e.getSource()).getData();
				quranForm.playerTogglePlayPause(ps == PlayStatus.PAUSE);
			}
		});

		rl = getNewRowLayout(SWT.HORIZONTAL);
		rl.spacing = 2;
		rl.marginLeft = 15;

		gd = new GridData();
		Composite nextPrevComposite = new Composite(middleRow, SWT.NONE);
		nextPrevComposite.setLayoutData(gd);
		nextPrevComposite.setLayout(rl);

		prevItem = new Button(nextPrevComposite, SWT.PUSH);
		prevItem.setImage(isLtr ? prevAyaImage : nextAyaImage);

		stopItem = new Button(nextPrevComposite, SWT.PUSH);
		stopItem.setImage(stopImage);
		stopItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				quranForm.playerStop();
			}
		});

		nextItem = new Button(nextPrevComposite, SWT.PUSH);
		nextItem.setImage(isLtr ? nextAyaImage : prevAyaImage);

		gl = new GridLayout(2, false);
		Composite volumeComposite = new Composite(middleRow, SWT.NONE);
		gl.horizontalSpacing = gl.verticalSpacing = gl.marginHeight = gl.marginWidth = 0;
		volumeComposite.setLayout(gl);

		gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.heightHint = 16;
		gd.widthHint = 20;
		gd.horizontalIndent = 20;
		volumeCanvas = new Canvas(volumeComposite, SWT.NONE);
		volumeCanvas.setLayoutData(gd);

		volumeCanvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				repaintAudioIcon(e);
			}

		});
		volumeCanvas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				if (e.button == 1) {
					muteAudio();
				}
			}
		});

		gd = new GridData(SWT.BEGINNING, SWT.CENTER, true, true);
		gd.heightHint = 15;
		gd.widthHint = 100;
		volumeProgressBar = new ProgressBar(volumeComposite, SWT.SMOOTH | SWT.HORIZONTAL);
		volumeProgressBar.setSelection(volume);
		volumeProgressBar.setLayoutData(gd);
		volumeProgressBar.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if ((e.stateMask & SWT.BUTTON1) != 0) {
					ProgressBar progress = (ProgressBar) e.getSource();
					handleVolume(e, progress);
				}
			}
		});
		volumeProgressBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				ProgressBar progress = (ProgressBar) e.getSource();
				handleVolume(e, progress);
			}

			@Override
			public void mouseUp(MouseEvent e) {
				ProgressBar progressBar = (ProgressBar) e.getSource();
				volume = progressBar.getSelection();
				volumeCanvas.redraw();
				playerController.setVolume(volume);
			}
		});
	}

	private void repaintAudioIcon(PaintEvent e) {
		Image img;
		if (volume <= 0) {
			img = volumeImage0;
		} else if (volume < 33) {
			img = volumeImage1;
		} else if (volume < 66) {
			img = volumeImage2;
		} else {
			img = volumeImage3;
		}
		e.gc.drawImage(img, 0, 0);
	}

	private void muteAudio() {
		volume = 0;
		volumeProgressBar.setSelection(0);
		volumeCanvas.redraw();
	}

	private void handleVolume(MouseEvent e, ProgressBar progressBar) {
		int width = progressBar.getSize().x;
		int progress = 0;
		int x = e.x < 0 ? 0 : e.x > width ? width : e.x;
		int threshold = 7;
		if (x < threshold) {
			progress = 0;
		} else if (width - x < threshold) {
			progress = 100;
		} else {
			float f = (float) x / width;
			progress = (int) (f * 100);
		}
		progressBar.setSelection(progress);
	}

	private RowLayout getNewRowLayout(int direction) {
		RowLayout rl = new RowLayout(direction);
		rl.fill = true;
		rl.marginBottom = rl.marginTop = rl.marginLeft = rl.marginRight = 0;
		rl.spacing = 0;
		return rl;
	}

	public void playerTogglePlayPause(boolean play) {
		if (play) {
			playPauseItem.setImage(pauseImage);
			playPauseItem.setData(PlayStatus.PLAY);
			playPauseItem.setSelection(true);
		} else {
			playPauseItem.setImage(playImage);
			playPauseItem.setData(PlayStatus.PAUSE);
			playPauseItem.setSelection(false);
		}
	}

	public void stop() {
		playerTogglePlayPause(false);
	}

	public void close() {
		shell.close();
	}

	private String meaning(String key) {
		return lang.getMeaningById(FORM_ID, key);
	}

	public Shell getShell() {
		return shell;
	}

}
package asgn2GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import asgn2Codes.ContainerCode;
import asgn2Containers.FreightContainer;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * The main window for the Cargo Manifest Text application.
 *
 * @author Yu Zhang (N8628769)
 */
public class CargoTextFrame extends JFrame {

	private static final int WIDTH = 600;
	private static final int HEIGHT = 400;

	private JButton btnLoad;
	private JButton btnUnload;
	private JButton btnFind;
	private JButton btnNewManifest;

	private CargoTextArea canvas;

	private JPanel pnlControls;
	private JPanel pnlDisplay;

	private CargoManifest cargo;

	/**
	 * Constructs the GUI.
	 *
	 * @param title
	 *            The frame title to use.
	 * @throws HeadlessException
	 *             from JFrame.
	 */
	public CargoTextFrame(String frameTitle) throws HeadlessException {
		super(frameTitle);
		constructorHelper();
		// disableButtons();
		setVisible(true);
	}

	/**
	 * Initialises the container display area.
	 *
	 * @param cargo
	 *            The <code>CargoManifest</code> instance containing necessary
	 *            state for display.
	 */
	private void setCanvas(CargoManifest cargo) {
		if (canvas != null) {
			pnlDisplay.remove(canvas);
		}
		if (cargo == null) {
			disableButtons();
		} else {
			enableButtons();
			canvas = new CargoTextArea(cargo);
			// implementation here
			JScrollPane sp = new JScrollPane(canvas);
			pnlDisplay = new JPanel();
			pnlDisplay.setLayout(new BorderLayout());
			pnlDisplay.add(sp, BorderLayout.CENTER);
			this.add(pnlDisplay, BorderLayout.CENTER);
			
			canvas.updateDisplay();
			canvas.repaint();
	    	this.validate();
	    	//repaint();
		}
		redraw();
	}

	/**
	 * Enables buttons for user interaction.
	 */
	private void enableButtons() {
		// implementation here
		btnLoad.setEnabled(true);
		btnUnload.setEnabled(true);
		btnFind.setEnabled(true);
		btnNewManifest.setEnabled(true);
	}

	/**
	 * Disables buttons from user interaction.
	 */
	private void disableButtons() {
		// implementation here
		btnLoad.setEnabled(false);
		btnUnload.setEnabled(false);
		btnFind.setEnabled(false);
		btnNewManifest.setEnabled(false);
	}

	/**
	 * Initialises and lays out GUI components.
	 */
	private void constructorHelper() {
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		btnLoad = createButton("Load", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable doRun = new Runnable() {
					@Override
					public void run() {
						CargoTextFrame.this.resetCanvas();
						CargoTextFrame.this.doLoad();
					}
				};
				SwingUtilities.invokeLater(doRun);
			}
		});
		btnUnload = createButton("Unload", new ActionListener() {
			// implementation here
			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable doRun = new Runnable() {
					@Override
					public void run() {
						CargoTextFrame.this.resetCanvas();
						CargoTextFrame.this.doUnload();
						;
					}
				};
				SwingUtilities.invokeLater(doRun);
			}
		});
		btnFind = createButton("Find", new ActionListener() {
			// implementation here
			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable doRun = new Runnable() {
					@Override
					public void run() {
						CargoTextFrame.this.resetCanvas();
						CargoTextFrame.this.doFind();
					}
				};
				SwingUtilities.invokeLater(doRun);
			}
		});
		btnNewManifest = createButton("New Manifest", new ActionListener() {
			// implementation here
			@Override
			public void actionPerformed(ActionEvent e) {
				Runnable doRun = new Runnable() {
					@Override
					public void run() {
						CargoTextFrame.this.resetCanvas();
						CargoTextFrame.this.setNewManifest();
					}
				};
				SwingUtilities.invokeLater(doRun);
			}
		});

		// implementation here
		this.setLayout(new BorderLayout());
		pnlControls = createControlPanel();
		this.add(pnlControls, BorderLayout.SOUTH);
		//setNewManifest();
		this.validate();
		//repaint();
	}

	/**
	 * Creates a JPanel containing user controls (buttons).
	 *
	 * @return User control panel.
	 */
	private JPanel createControlPanel() {
		// implementation here
		JPanel panel = new JPanel();
		panel.add(btnNewManifest);
		panel.add(btnLoad);
		panel.add(btnUnload);
		panel.add(btnFind);

		return panel;
	}

	/**
	 * Factory method to create a JButton and add its ActionListener.
	 *
	 * @param name
	 *            The text to display and use as the component's name.
	 * @param btnListener
	 *            The ActionListener to add.
	 * @return A named JButton with ActionListener added.
	 */
	private JButton createButton(String name, ActionListener btnListener) {
		JButton btn = new JButton(name);
		btn.setName(name);
		btn.addActionListener(btnListener);
		return btn;
	}

	/**
	 * Initiate the New Manifest dialog which sets the instance of CargoManifest
	 * to work with.
	 */
	private void setNewManifest() {
		// implementation here
		cargo = ManifestDialog.showDialog(this);
		setCanvas(cargo);
	}

	/**
	 * Turns off container highlighting when an action other than Find is
	 * initiated.
	 */
	private void resetCanvas() {
		// implementation here
		redraw();
	}

	/**
	 * Initiates the Load Container dialog.
	 */
	private void doLoad() {
		// implementation here
		// Don't forget to redraw

		FreightContainer container = LoadContainerDialog.showDialog(this);
		
		if (container == null || cargo == null) {
			return;
		}
		
		try {
			cargo.loadContainer(container);
		} catch (ManifestException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		redraw();
	}

	/**
	 * Initiates the Unload Container dialog.
	 */
	private void doUnload() {
		// implementation here
		// Don't forget to redraw	
		ContainerCode code = ContainerCodeDialog.showDialog(this);
		
		if (code == null || cargo == null) {
			return;
		}
		try {
			cargo.unloadContainer(code);
		} catch (ManifestException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		redraw();
	}

	/**
	 * Initiates the Find Container dialog.
	 */
	private void doFind() {
		// implementation here
		ContainerCode code = ContainerCodeDialog.showDialog(this);
		
		if (code == null || cargo == null) {
			return;
		}
		canvas.setToFind(code);
		
		//redraw();
	}

	/**
	 * 
	 * Updates the display area.
	 *
	 */
	private void redraw() {
		// implementation here
		if (cargo != null)
			canvas.updateDisplay();
	}
}

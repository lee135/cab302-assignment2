package asgn2GUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import asgn2Exceptions.CargoException;
import asgn2Exceptions.ManifestException;
import asgn2Manifests.CargoManifest;

/**
 * Creates a dialog box allowing the user to enter parameters for a new
 * <code>CargoManifest</code>.
 *
 * @author Zehui Zhang (N8646236)
 */
public class ManifestDialog extends AbstractDialog {

	private static final int HEIGHT = 150;
	private static final int WIDTH = 250;

	private JTextField txtNumStacks;
	private JTextField txtMaxHeight;
	private JTextField txtMaxWeight;

	private CargoManifest manifest;

	/**
	 * Constructs a modal dialog box that gathers information required for
	 * creating a cargo manifest.
	 *
	 * @param parent
	 *            the frame which created this dialog box.
	 */
	private ManifestDialog(JFrame parent) {
		super(parent, "Create Manifest", WIDTH, HEIGHT);
		setName("New Manifest");
		setResizable(false);
		manifest = null;
	}

	/**
	 * @see AbstractDialog.createContentPanel()
	 */
	@Override
	protected JPanel createContentPanel() {

		txtNumStacks = createTextField(8, "Number of Stacks");
		txtMaxHeight = createTextField(8, "Maximum Height");
		txtMaxWeight = createTextField(8, "Maximum Weight");

		JPanel toReturn = new JPanel();
		toReturn.setLayout(new GridBagLayout());

		// Implementation here

		JLabel lblStack = new JLabel("Number of Stacks: ");
		JLabel lblHeight = new JLabel("Max Stack Height: ");
		JLabel lblWeight = new JLabel("Max Weight: ");

		GridBagConstraints gbc = new GridBagConstraints();

		this.addToPanel(toReturn, lblStack, gbc, 0, 0, 1, 1);
		this.addToPanel(toReturn, txtNumStacks, gbc, 3, 0, 1, 1);
		this.addToPanel(toReturn, lblHeight, gbc, 0, 1, 1, 1);
		this.addToPanel(toReturn, txtMaxHeight, gbc, 3, 1, 1, 1);
		this.addToPanel(toReturn, lblWeight, gbc, 0, 2, 1, 1);
		this.addToPanel(toReturn, txtMaxWeight, gbc, 3, 2, 1, 1);

		return toReturn;
	}

	/*
	 * Factory method to create a named JTextField
	 */
	private JTextField createTextField(int numColumns, String name) {
		JTextField text = new JTextField();
		text.setColumns(numColumns);
		text.setName(name);
		return text;
	}

	@Override
	protected boolean dialogDone() {
		// Implementation here
		// Parameters and building a new manifest, all the while handling
		// exceptions
		int numStacks;
		int maxHeight;
		int maxWieght;

		if (txtNumStacks.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(null, "CargoException:Invalid Input",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (txtMaxHeight.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(null, "CargoException:Invalid Input",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (txtMaxWeight.getText().trim().length() == 0) {
			JOptionPane.showMessageDialog(null, "CargoException:Invalid Input",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		try {
			numStacks = Integer.parseInt(txtNumStacks.getText());
			maxHeight = Integer.parseInt(txtMaxHeight.getText());
			maxWieght = Integer.parseInt(txtMaxWeight.getText());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "CargoException:Invalid Input",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		// check if the value is valid
		if (numStacks < 0 || maxHeight < 0 || maxWieght < 0) {
			JOptionPane.showMessageDialog(null, "CargoException:Invalid Input",
					"ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		return true;
	}

	/**
	 * Shows the <code>ManifestDialog</code> for user interaction.
	 *
	 * @param parent
	 *            - The parent <code>JFrame</code> which created this dialog
	 *            box.
	 * @return a <code>CargoManifest</code> instance with valid values.
	 */
	public static CargoManifest showDialog(JFrame parent) {
		// Implementation again
		ManifestDialog dialog = new ManifestDialog(parent);
		dialog.setVisible(true);

		int numStacks = 0;
		int maxHeight = 0;
		int maxWieght = 0;
		try {

			numStacks = Integer.parseInt(dialog.txtNumStacks.getText());
			maxHeight = Integer.parseInt(dialog.txtMaxHeight.getText());
			maxWieght = Integer.parseInt(dialog.txtMaxWeight.getText());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			dialog.manifest = new CargoManifest(numStacks, maxHeight, maxWieght);
		} catch (ManifestException e) {
			e.printStackTrace();
		}

		return dialog.manifest;
	}
}

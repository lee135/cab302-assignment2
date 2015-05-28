package asgn2GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import asgn2Codes.ContainerCode;
import asgn2Containers.DangerousGoodsContainer;
import asgn2Containers.FreightContainer;
import asgn2Containers.GeneralGoodsContainer;
import asgn2Containers.RefrigeratedContainer;
import asgn2Manifests.CargoManifest;

/**
 * Creates a JPanel in which graphical components are laid out to represent the
 * cargo manifest.
 *
 * @author  Zehui Zhang (N8646236)
 */
public class CargoCanvas extends JPanel {

	private static final int WIDTH = 120;
	private static final int HEIGHT = 50;
	private static final int HSPACE = 10;
	private static final int VSPACE = 20;

	private final CargoManifest cargo;

	private ContainerCode toFind;

	/**
	 * Constructor
	 *
	 * @param cargo
	 *            The <code>CargoManifest</code> on which the graphics is based
	 *            so that the number of stacks and height can be adhered to.
	 */
	public CargoCanvas(CargoManifest cargo) {
		this.cargo = cargo;
		setName("Canvas");
	}

	/**
	 * Highlights a container.
	 *
	 * @param code
	 *            ContainerCode to highlight.
	 */
	public void setToFind(ContainerCode code) {
		// implementation here - don't forget to repaint
		if (code != null && cargo.whichStack(code) == null) {
			JOptionPane.showMessageDialog(null, "This container is not onboad", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		toFind = code;
	}

	/**
	 * Draws the containers in the cargo manifest on the Graphics context of the
	 * Canvas.
	 *
	 * @param g
	 *            The Graphics context to draw on.
	 */
	@Override
	public void paint(Graphics g) {
		// Implementation here
		ArrayList<ArrayList<FreightContainer>> manifest = cargo.getManifest();

		for (int i = 0; i < manifest.size(); ++i) {
			ArrayList<FreightContainer> currentStack = manifest.get(i);

			g.setColor(Color.BLACK);
			g.drawRect(HSPACE - 5, VSPACE + i * (HEIGHT + VSPACE), 1, HEIGHT);
			g.fillRect(HSPACE - 5, VSPACE + i * (HEIGHT + VSPACE), 1, HEIGHT);

			if (currentStack.size() == 0) {
				continue;
			}

			for (int j = 0; j < currentStack.size(); ++j) {
				FreightContainer container = currentStack.get(j);
				if (toFind != null && container.getCode().equals(toFind)) {
					g.setColor(Color.YELLOW);
				} else {
					if (container instanceof GeneralGoodsContainer) {
						g.setColor(Color.GRAY);
					} else if (container instanceof RefrigeratedContainer) {
						g.setColor(Color.BLUE);
					} else if (container instanceof DangerousGoodsContainer) {
						g.setColor(Color.RED);
					}
				}

				drawContainer(g, container, HSPACE + j * (WIDTH + HSPACE),
						VSPACE + i * (HEIGHT + VSPACE));
			}

		}
	}

	/**
	 * Draws a container at the given location.
	 *
	 * @param g
	 *            The Graphics context to draw on.
	 * @param container
	 *            The container to draw - the type determines the colour and
	 *            ContainerCode is used to identify the drawn Rectangle.
	 * @param x
	 *            The x location for the Rectangle.
	 * @param y
	 *            The y location for the Rectangle.
	 */
	private void drawContainer(Graphics g, FreightContainer container, int x,
			int y) {
		// Implementation here
		// Feel free to use some other method structure here, but this is the
		// basis for the demo.
		// Obviously you need the graphics context and container as parameters.
		// But you can also use images if you wish.
		g.drawRect(x, y, WIDTH, HEIGHT);
		g.fillRect(x, y, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		g.drawString(container.getCode().toString(), x + 20, y + 20);

	}
}

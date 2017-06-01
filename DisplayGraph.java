import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class DisplayGraph extends JFrame {

    public DisplayGraph(Graph graph) { 

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLocationRelativeTo(null);
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        GraphPanel graphPanel = new GraphPanel(graph);

        this.add(graphPanel);
        Dimension dimension = new Dimension(800, 600);
        this.setPreferredSize(dimension);
        this.pack();
    }

    @SuppressWarnings("unused")
	private class ComputeHandler implements ActionListener {
        public ComputeHandler() {
        }

        public void actionPerformed(ActionEvent e) {
        }
    }

}

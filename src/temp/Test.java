package temp;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.BoxLayout;

public class Test {

    public static void main(String... args) {
        JFrame frame = new JFrame();
       
        JPanel contentPane = new JPanel(null);
        contentPane.setPreferredSize(new Dimension(500, 400));
        frame.setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JSplitPane splitPane = new JSplitPane();
        contentPane.add(splitPane);
        
        JScrollPane scrollPane = new JScrollPane();
        splitPane.setLeftComponent(scrollPane);
        
        JPanel panel_1 = new JPanel();
        panel_1.setPreferredSize(new Dimension(200, 300));
        
        scrollPane.setViewportView(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(200, 300));
        splitPane.setRightComponent(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
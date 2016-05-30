package temp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.clausthal.tu.ielf.randomGenrators.distributions.CDFtest;


public class test2 {
	public static void main(String[] args) {
//		JFrame frame = new JFrame("test frame");
//		JPanel panel = new JPanel() {
//			public void paintComponent(Graphics g) {
//
//				super.paintComponent(g);
//				g.setColor(Color.BLACK);
//				g.drawOval(0, 0, 200, 200);
//			}
		double u=0.145;
		double x=CDFtest.INVCDF(u, 200, 100);
		System.out.println("invcdf --> "+ x);
		System.out.println("cdf >>--> "+ CDFtest.CDF(x, 200, 100));
		
		};

//		JLabel label = new JLabel("test");
//
//		panel.add(label);
//		label.setBounds(400, 400, 60, 20);
//
//		panel.setPreferredSize(new Dimension(500, 500));
//		panel.setLayout(null);
//
//		JScrollPane sp = new JScrollPane(panel);
//
//		frame.getContentPane().add(sp, BorderLayout.CENTER);
//		frame.pack();
//		frame.setVisible(true);

//	}
}

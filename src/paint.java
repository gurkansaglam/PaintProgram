

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.dnd.MouseDragGestureRecognizer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.Panel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


public class paint extends JFrame {
	
	
	//creating main pnale and draw panel
	private JPanel contentPane;
	final JPanel drawPane = new JPanel();
	//boolean variable to listening mouse
	private int mousepressed=0;
	//Color variables
	static Color fillColor = Color.black;
	static Color borderColor = Color.black;
	static Color bgColor;
	//creating graphic for saving content 
	Graphics2D graphics2D;
	//initial and end point of rect ,elips...
	 int rectX,rectY,rect2X,rect2Y;
	 int ovalX,ovalY,oval2X,oval2Y;
	 static boolean fill = false;
	 int lineX,lineY,line2X,line2Y;
	 int penX,penY,pendragged;
	 //boolean variables for type of shapes when button pressed its value will be true so user can draw that shape
	static boolean rect = false;
	 static boolean oval = false;
	static boolean line = false;
	static boolean free = false;
	static boolean pen = false;
	/**
	 * Launch the application.
	 */
	 
	  
	 
	public static void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					paint frame = new paint();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public paint() {
		//seting panels
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//adding panels to content pane
		final JPanel panelBtns = new JPanel();
		drawPane.setBounds(125, 0, 515, 480);
		drawPane.setBackground(Color.RED);
		panelBtns.setBackground(Color.LIGHT_GRAY);
		panelBtns.setBounds(0, 0, 125, 480);
		contentPane.add(panelBtns,BorderLayout.WEST);
		panelBtns.setLayout(null);
		contentPane.add(drawPane,BorderLayout.EAST);
		
		JButton btnNew = new JButton("New");
		btnNew.setBounds(10, 11, 95, 23);
		panelBtns.add(btnNew);
		// creating buttons to button panels
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(10, 45, 95, 23);
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				 
                 try{
                     //saving content using method which created end of the codes
                	 SaveScreenShot(drawPane,"drawpaneimage.png");
                	 SaveScreenShot(contentPane,"contentpaneimage.png");
                	 
                     }
                 catch(Exception ex){
                      
                     }
                 
			}
		});
		panelBtns.add(btnSave);
		
		JButton btnSaveAs = new JButton("Save As");
		btnSaveAs.setBounds(10, 79, 95, 23);
		panelBtns.add(btnSaveAs);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setBounds(10, 113, 95, 23);
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(1);
			}
		});
		panelBtns.add(btnExit);
		
		JButton buttonRect = new JButton();
		buttonRect.setIcon(new ImageIcon(paint.class.getResource("/icons/rectangleimage.png")));
		buttonRect.setBounds(10, 147, 38, 38);
		buttonRect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rect = true;
				line = false;
				oval = false;
				free = false;
				pen = false;
				// TODO Auto-generated method stub
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						rectX = e.getX();
						rectY = e.getY();
					}
					public void mouseReleased(MouseEvent e){
						rect2X = e.getX();
						rect2Y = e.getY();
						//if direction of drawing rectangle is reverse , we must change the initial point of our rectangle because of drawing rect method 
						if(rectX > rect2X){
							int temp = rectX;
							rectX = rect2X;
							rect2X = temp;
						}
						if(rectY > rect2Y){
							int temp = rectY;
							rectY = rect2Y;
							rect2Y = temp;
						}
						//controlling Is rectangle button pressed?
							if(rect){
						
								Graphics g = drawPane.getGraphics();
						//controlling that fill choosed
						if(fill){
							//we draw two times to add border to our drawing
							g.setColor(fillColor);
						g.fillRect(rectX-125-10,rectY-25-8, Math.abs(rect2X-rectX), Math.abs(rect2Y-rectY));
						
						g.setColor(borderColor);
						g.drawRect(rectX-125-10,rectY-25-8, Math.abs(rect2X-rectX), Math.abs(rect2Y-rectY));
						
						}
						else{
							g.setColor(borderColor);
							g.drawRect(rectX-125-10,rectY-25-8, Math.abs(rect2X-rectX), Math.abs(rect2Y-rectY));
							
						}
						//drawPane.paintComponents(g);
						g.dispose();
					}}
					
				});
				
			}
		});
		panelBtns.add(buttonRect);
		
		JButton buttonFree = new JButton();
		buttonFree.setIcon(new ImageIcon(paint.class.getResource("/icons/penimage.png")));
		buttonFree.setBounds(67, 147, 38, 38);
buttonFree.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rect = false;
				line = false;
				oval = false;
				free = true;
				pen = false;
				// TODO Auto-generated method stub
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						ovalX = e.getX();
						ovalY = e.getY();
						//System.out.println("ilk");
						//System.out.print(e.getX() + "   ");
						//System.out.println(e.getY());
						if(free){
							Graphics g = drawPane.getGraphics();
							g.setColor(fillColor);
							
								g.setColor(fillColor);
								g.fillOval(ovalX-125-15,ovalY-25-20,15,15);

							g.dispose();
					}
					
					}
				});
				
			}
		});
		panelBtns.add(buttonFree);
		
		JButton buttonOval = new JButton();
		buttonOval.setIcon(new ImageIcon(paint.class.getResource("/icons/elipseimage.png")));
		buttonOval.setBounds(10, 196, 38, 38);
buttonOval.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rect = false;
				line = false;
				oval = true;
				free = false;
				pen = false;
				// TODO Auto-generated method stub
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						ovalX = e.getX();
						ovalY = e.getY();
						System.out.println("ilk");
						System.out.print(e.getX() + "   ");
						System.out.println(e.getY());
					}
					public void mouseReleased(MouseEvent e){
						oval2X = e.getX();
						oval2Y = e.getY();
						//if direction of drawing oval is reverse , we must change the initial point of our oval because of drawing oval method 
						if(ovalX > oval2X){
							int temp = ovalX;
							ovalX =oval2X;
							oval2X = temp;
						}
						 if(ovalY > oval2Y){
							int temp1 = ovalY;
							ovalY = oval2Y;
							oval2Y = temp1;
						}
							if(oval){
						Graphics g = drawPane.getGraphics();
						g.setColor(fillColor);
						if(fill){
							g.setColor(fillColor);
							g.fillOval(ovalX-125-10,ovalY-25-13,oval2X-ovalX,oval2Y-ovalY);
							g.setColor(borderColor);
							g.drawOval(ovalX-125-10,ovalY-25-13,oval2X-ovalX,oval2Y-ovalY);
						
						}
						else{
							
							g.setColor(borderColor);
							g.drawOval(ovalX-125-10,ovalY-25-13,oval2X-ovalX,oval2Y-ovalY);
						}
						g.dispose();
					}
					}
				});
				
			}
		});
		panelBtns.add(buttonOval);
		
		JButton buttonLine = new JButton();
		buttonLine.setIcon(new ImageIcon(paint.class.getResource("/icons/lineimage.png")));
		buttonLine.setBounds(67, 196, 38, 38);
buttonLine.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rect = false;
				line = true;
				oval = false;
				free = false;
				pen = false;
				// TODO Auto-generated method stub
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						lineX = e.getX();
						lineY = e.getY();
					}
					public void mouseReleased(MouseEvent e){
						line2X = e.getX();
						line2Y = e.getY();
						
							if(line){
						Graphics g = drawPane.getGraphics();
						g.setColor(borderColor);
						g.drawLine(lineX-125-10,lineY-25-13, line2X-125-10, line2Y-25-13);
						
						
						g.dispose();
					}
					}
				});
				
			}
		});
		panelBtns.add(buttonLine);
		
		JButton buttonFill = new JButton();
		buttonFill.setIcon(new ImageIcon(paint.class.getResource("/icons/fillimage.png")));
		buttonFill.setBounds(10, 245, 38, 38);
		buttonFill.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fill = true;
				fillColor = JColorChooser.showDialog(
	                     paint.this,
	                     "Choose Fill Color",
	                     Color.YELLOW);
			}
		});
		panelBtns.add(buttonFill);
		
		JButton buttonBorder = new JButton();
		buttonBorder.setIcon(new ImageIcon(paint.class.getResource("/icons/borderimage.png")));
		buttonBorder.setBounds(67, 245, 38, 38);
		buttonBorder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fill = false;
				borderColor = JColorChooser.showDialog(
	                     paint.this,
	                     "Choose Background Color",
	                     Color.YELLOW);
				
			}
		});
		panelBtns.add(buttonBorder);
		
		JButton buttonBg = new JButton("New button");
		buttonBg.setIcon(new ImageIcon(paint.class.getResource("/icons/bgimage.png")));
		buttonBg.setBounds(10, 294, 38, 38);
		buttonBg.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				bgColor = JColorChooser.showDialog(
	                     paint.this,
	                     "Choose Background Color",
	                     Color.YELLOW);
				drawPane.setBackground(bgColor);
			}
		});
		panelBtns.add(buttonBg);
		
		JButton buttonClear = new JButton("New button");
		buttonClear.setIcon(new ImageIcon(paint.class.getResource("/icons/clearimage.png")));
		buttonClear.setBounds(67, 294, 38, 38);
		buttonClear.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				drawPane.removeAll();
				
				repaint();
			}
		});
		panelBtns.add(buttonClear);
		
		JButton buttonPen = new JButton("New button");
		buttonPen.setIcon(new ImageIcon(paint.class.getResource("/icons/freepenimage.png")));
		buttonPen.setBounds(10, 343, 38, 38);
		buttonPen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				rect = false;
				line = false;
				oval = false;
				free = false;
				pen = true;
				
				// TODO Auto-generated method stub
				//I cannot handle the coordinates while mouse dragging
				addMouseListener(new MouseAdapter(){
					public void mousePressed(MouseEvent e){
						mousepressed=1;
					}
					
					public void mouseMoved(MouseEvent e){
						if(mousepressed==1){
							penX=e.getX();
							penY=e.getY();
							if(pen){
								Graphics g = drawPane.getGraphics();
								g.setColor(fillColor);
								g.fillOval(ovalX-125-10,ovalY-25-13,2,2);
								g.dispose();		
							}			
						}						
					}
					public void mouseReleased(MouseEvent e){						
					mousepressed=0;					
					}
				}
				
				);
			
			
			}
		});
		panelBtns.add(buttonPen);
		
		
	}
	
	//*********************************************
	//saving method and its get img method
	public static BufferedImage getScreenShot(Component component){
		BufferedImage image = new BufferedImage(component.getWidth(),component.getHeight(),BufferedImage.TYPE_INT_RGB);
		component.paint(image.getGraphics());
		return image;
	}
	public static void  SaveScreenShot(Component component,String filename) throws Exception{
		BufferedImage img = getScreenShot(component);
		ImageIO.write(img, "png", new File (filename));
	}
 //***********************************************
}

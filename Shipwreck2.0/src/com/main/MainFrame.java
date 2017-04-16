package com.main;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import javax.swing.JTextPane;
import javax.swing.SwingWorker;
import javax.swing.TransferHandler;
import javax.swing.UIManager;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;

public class MainFrame extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	
	// Ship drag identifier
	private static boolean isPlacing = false;
	private static int shipModel = 0;
	
	// Related arrays
	private static Area compAreaArray[][] = new Area[8][8];
	private static Area userAreaArray[][] = new Area[8][8];
	
	private static Random random = new Random();
	
	// Ship models
	private static ShipModel[] shipModelArray = new ShipModel[10];
	private static ArrayList<int[]> compUsedModels = new ArrayList<>();
	
	// Labels
	private static JLabel infoLabel;
	
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
					
					// Fill area arrays' types
					for (int p = 0; p < 8; p++){
						for (int q = 0; q < 8; q++){
							userAreaArray[p][q].setType(AreaType.BLANK);
							compAreaArray[p][q].setType(AreaType.BLANK);
						}
					}
					
					// Inform player
					infoLabel.setText("Computer has placed their ships, you're placing now!");
					
					// Disable computer buttons
					adjustButtons(compAreaArray, "disable");
					
					// Computer places ship
					while(true){
						
						// Check if all ships are placed, (5 + 4 + 3 + 2 + 1 = 15).
						if (countType(compAreaArray, AreaType.SHIP) == 15){
							break;}
						else {
							// Pick random coordinates and shipModel for the current ship
							int sm = (random.nextInt(9) + 1);
							for (int[] p : compUsedModels){
								if (p == sm){
									
								}
							}
							int rx = random.nextInt(8);
							int ry = random.nextInt(8);
							
							// Check if the chosen ship is valid
							System.out.println("sm: " + sm + ", rx: " + rx + ", ry: " + ry);
							if (isSafeToPlace(compAreaArray, compAreaArray[ry][rx], sm)){
								if (sm < 6){
									for (int p = 0; p < sm; p++){
										// Determine the type of current ship piece
										compAreaArray[ry][rx + p].setType(AreaType.SHIP);
									}
								}
								else {
									for (int p = 0; p < (sm - 4); p++){			
										// Determine the type of current ship piece
										compAreaArray[ry + p][rx].setType(AreaType.SHIP);
									}
								}
							} else {continue;}
						}
					} // Computer finishes placing
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		
		// Adjust how the frame looks
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch ( Exception e) {
			e.printStackTrace();}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 928);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Label for computer
		try {
			JLabel labelComputer = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/labelComputer.png"))));
			labelComputer.setBounds(747, 7, 256, 32);
			contentPane.add(labelComputer);
		} catch (Exception e) {e.printStackTrace();}
		
		// Label for user
		try {
			JLabel labelUser = new JLabel(new ImageIcon(ImageIO.read(getClass().getResource("/labelUser.png"))));
			labelUser.setBounds(175, 7, 256, 32);
			contentPane.add(labelUser);
		} catch (Exception e) {e.printStackTrace();}
		
		
		// A label that displays information related to the state of the game
		infoLabel = new JLabel("Information Label");
		infoLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		infoLabel.setToolTipText("Status of the game will be displayed here");
		infoLabel.setBounds(15, 830, 600, 26);
		contentPane.add(infoLabel);
		
		// Build shipModelArray
		for (int p = 1; p < 10; p++){
			ShipModel sm = new ShipModel();
			try {
				if (p == 1){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel2.png"))));
					sm.setBounds((2 + (p * 45)), 600, (32 + (p * 32)), 32);
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 2;
							}
						}
					});
				} else if (p == 2){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel3.png"))));
					sm.setBounds((2 + (p * 45)), 600, (32 + (p * 32)), 32);
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 3;
							}
						}
					});
					sm.setVisible(false);
				} else if (p == 3){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel4.png"))));
					sm.setBounds((2 + (p * 45)), 600, (32 + (p * 32)), 32);
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 4;
							}
						}
					});
					sm.setVisible(false);
				} else if (p == 4){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel5.png"))));
					sm.setBounds((2 + (p * 45)), 600, (32 + (p * 32)), 32);
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 5;
							}
						}
					});
					sm.setVisible(false);
				} else if (p == 5){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel6.png"))));
					sm.setBounds((2 + ((p - 4) * 45)), 644, 32, (32 + ((p - 4) * 32)));
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 6;
							}
						}
					});
				} else if (p == 6){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel7.png"))));
					sm.setBounds((2 + ((p - 4) * 45)), 644, 32, (32 + ((p - 4) * 32)));
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 7;
							}
						}
					});
					sm.setVisible(false);
				} else if (p == 7){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel8.png"))));
					sm.setBounds((2 + ((p - 4) * 45)), 644, 32, (32 + ((p - 4) * 32)));
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 8;
							}
						}
					});
					sm.setVisible(false);
				} else if (p == 8){
					sm.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/shipModel9.png"))));
					sm.setBounds((2 + ((p - 4) * 45)), 644, 32, (32 + ((p - 4) * 32)));
					sm.addMouseListener(new MouseAdapter(){
						@Override
						public void mousePressed(MouseEvent arg0){
							if (sm.isEnabled()){
								isPlacing = true;
								shipModel = 9;
							}
						}
					});
					sm.setVisible(false);
				} 
			} catch (Exception e) {}
			shipModelArray[p] = sm;
			contentPane.add(sm);
		}
		
		// Initialise user button array
		for (int a = 0; a < 8; a++){
			for (int b = 0; b < 8; b++){
				Area area = new Area("");
				area.setBounds((((64 * b) + 47)), ((64 * a) + 48), 64, 64);
				
				// Assign an image to the JButton
				try {area.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/blank.png"))));
				} catch (Exception ex) {System.out.println(ex);}
				
				// Add listeners
				area.addMouseListener(new MouseAdapter(){
					@Override
					public void mouseEntered(MouseEvent arg0){
						
						// Define and build icons, try catch required because of ImageIO
						ImageIcon IMG_XCENTER = null;
						ImageIcon IMG_YCENTER = null;
						ImageIcon IMG_LEFT = null;
						ImageIcon IMG_RIGHT = null;
						ImageIcon IMG_UP = null;
						ImageIcon IMG_DOWN = null;
						try{
							IMG_XCENTER = new ImageIcon(ImageIO.read(getClass().getResource("/shipcenterx.png")));
							IMG_YCENTER = new ImageIcon(ImageIO.read(getClass().getResource("/shipcentery.png")));
							IMG_LEFT = new ImageIcon(ImageIO.read(getClass().getResource("/shipleft.png")));
							IMG_RIGHT = new ImageIcon(ImageIO.read(getClass().getResource("/shipright.png")));
							IMG_UP = new ImageIcon(ImageIO.read(getClass().getResource("/shipup.png")));
							IMG_DOWN = new ImageIcon(ImageIO.read(getClass().getResource("/shipdown.png")));
						} catch (Exception ex) {System.out.println(ex);}
						
						// Get area indexes
						int ax = (int) ((area.getBounds().getCenterX() - 47) / 64);
						int ay = (int) ((area.getBounds().getCenterY() - 48) / 64);
						
						// Render the guideline for ship placement
						if (isPlacing && isSafeToPlace(userAreaArray, area, shipModel)){
							if (shipModel < 6){
								for (int p = 0; p < shipModel; p++){
									// Determine the type of current ship piece
									if (p == 0){
										userAreaArray[ay][ax + p].setIcon(IMG_LEFT);
									} else if (p == (shipModel - 1)) {
										userAreaArray[ay][ax + p].setIcon(IMG_RIGHT);
									} else {
										userAreaArray[ay][ax + p].setIcon(IMG_XCENTER);
									}
								}
							}
							else {
								for (int p = 0; p < (shipModel - 4); p++){
									// Determine the type of current ship piece
									if (p == 0){
											userAreaArray[ay + p][ax].setIcon(IMG_UP);
										} else if (p == (shipModel - 5)) {
											userAreaArray[ay + p][ax].setIcon(IMG_DOWN);
										} else {
											userAreaArray[ay + p][ax].setIcon(IMG_YCENTER);
										}
								}
							}
						}
					
					}
					
					@Override
					public void mouseExited(MouseEvent arg0) {
						
						// Define and build ImageIcon, try catch required because of ImageIO
						ImageIcon IMG_BLANK = null;
						try{
							IMG_BLANK = new ImageIcon(ImageIO.read(getClass().getResource("/blank.png")));
						} catch (Exception ex) {System.out.println(ex);}
						
						// Get area indexes
						int ax = (int) ((area.getBounds().getCenterX() - 47) / 64);
						int ay = (int) ((area.getBounds().getCenterY() - 48) / 64);

						// Erase the guideline
						if (isPlacing){
							if (isSafeToPlace(userAreaArray, area, shipModel)){
								if (shipModel < 6){
									for (int p = 0; p < shipModel; p++){
										userAreaArray[ay][ax + p].setIcon(IMG_BLANK);
									}
								}
								else {
									for (int p = 0; p < (shipModel - 4); p++){
										userAreaArray[ay + p][ax].setIcon(IMG_BLANK);
									}
								}
							}
						}
					}
					
					@Override
					public void mousePressed(MouseEvent arg0){
						
						// Get area indexes
						int ax = (int) ((area.getBounds().getCenterX() - 47) / 64);
						int ay = (int) ((area.getBounds().getCenterY() - 48) / 64);
						
						// If ship placement is successful, register AreaType
						if (isPlacing){
							if (isSafeToPlace(userAreaArray, area, shipModel)){
								isPlacing = false;
								if (shipModel < 6){
									for (int p = 0; p < shipModel; p++){
										userAreaArray[ay][ax + p].setType(AreaType.SHIP);
									}
								}
								else {
									for (int p = 0; p < (shipModel - 4); p++){
										userAreaArray[ay + p][ax].setType(AreaType.SHIP);
									}
								}
								
								// Disable picked option, enable next ship models to be placed
								System.out.println(shipModel);
								if (shipModel < 6){
									shipModelArray[shipModel - 1].setVisible(false);
									shipModelArray[shipModel + 3].setVisible(false);
									if (shipModel != 5){ // Condition so that the previous models won't re-appear
										shipModelArray[shipModel].setVisible(true);
									}
									shipModelArray[shipModel + 4].setVisible(true);
								} else {
									shipModelArray[shipModel - 1].setVisible(false);
									shipModelArray[shipModel - 5].setVisible(false);
									shipModelArray[shipModel].setVisible(true);
									if (shipModel != 9){ // Condition so that the previous models won't re-appear
										shipModelArray[shipModel - 4].setVisible(true);
									}
								}
								
								// If it's the last ship to be placed, initiate computer
								if (shipModel == 9 || shipModel == 5){
									hit();
								}
							} else {
								if (isPlacing){
									infoLabel.setText("You've already placen a ship there!");
								}
							}
						}
					}
				});
				area.addActionListener(this);
				
				// Add to array and frame
				userAreaArray[a][b] = area;
				contentPane.add(area);
				
			}
		}
		
		// Initialise computer button array
		for (int a = 0; a < 8; a++){
			for (int b = 0; b < 8; b++){
				Area area = new Area("");
				area.setBounds((1114 - ((64 * b) + 47)), ((64 * a) + 48), 64, 64);
				
				// Assign an image to the JButton
				try {area.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/blank.png"))));
				} catch (Exception ex) {System.out.println(ex);}
				
				// Add action listener
				area.addActionListener(this);
				
				// Add to array and frame
				compAreaArray[a][b] = area;
				contentPane.add(area);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		// Define and build icons, try catch required because of ImageIO
		ImageIcon IMG_MISS = null;
		ImageIcon IMG_HIT = null;
		try{
		IMG_MISS = new ImageIcon(ImageIO.read(getClass().getResource("/miss.png")));
		IMG_HIT = new ImageIcon(ImageIO.read(getClass().getResource("/hit.png")));
		} catch (Exception ex) {System.out.println(ex);}
		
		// Go through button array and check source and state then act accordingly
		for (int p = 0; p < userAreaArray.length; p++){
			for (int q = 0; q < userAreaArray.length; q++){
				if (arg0.getSource() == compAreaArray[p][q]) {
					
					// If button is a hit or a miss
					if (compAreaArray[p][q].getType() == AreaType.MISS || compAreaArray[p][q].getType() == AreaType.HIT){
						infoLabel.setText("You've hit that area already!");
					}
					
					// If button is a blank
					else if (compAreaArray[p][q].getType() == AreaType.BLANK) {
						infoLabel.setText("You missed!");
						compAreaArray[p][q].setIcon(IMG_MISS);
						compAreaArray[p][q].setType(AreaType.MISS);
						hit(); // Trigger SwingWorker
					}
					
					// If button is a ship
					else if (compAreaArray[p][q].getType() == AreaType.SHIP){
						infoLabel.setText("You've hit a ship!");
						compAreaArray[p][q].setIcon(IMG_HIT);
						compAreaArray[p][q].setType(AreaType.HIT);
						hit(); // Trigger SwingWorker
					}
				}
			}
		}	 
	}
	
	public static void hit(){
		SwingWorker<Void, Void> sw = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				
				// Winning condition
				if (countType(userAreaArray, AreaType.HIT) == 14){
					JOptionPane.showMessageDialog(infoLabel, "Computer wins!");
					adjustButtons(compAreaArray, "disable");
					adjustButtons(userAreaArray, "disable");
				} else if (countType(compAreaArray, AreaType.HIT) == 14){
					JOptionPane.showMessageDialog(infoLabel, "User wins!");
					adjustButtons(compAreaArray, "disable");
					adjustButtons(userAreaArray, "disable");}
				
				Thread.sleep(1000);
				adjustButtons(compAreaArray, "disable");
				Thread.sleep(500);infoLabel.setText("Computer is deciding");
				Thread.sleep(500);infoLabel.setText("Computer is deciding.");
				Thread.sleep(500);infoLabel.setText("Computer is deciding..");
				Thread.sleep(500);infoLabel.setText("Computer is deciding...");
				int rx = random.nextInt(8);
				int ry = random.nextInt(8);
				
				// This code segment makes it so that the computer won't hit an already hit spot.
				while (userAreaArray[rx][ry].getType() == AreaType.MISS || userAreaArray[rx][ry].getType() == AreaType.HIT){
					rx = random.nextInt(8);
					ry = random.nextInt(8);}
				
				// Computer missed
				if (userAreaArray[rx][ry].getType() == AreaType.BLANK){
					try{
						ImageIcon img = new ImageIcon(ImageIO.read(getClass().getResource("/miss.png")));
						userAreaArray[rx][ry].setIcon(img);
					} catch (Exception E) {E.printStackTrace();}
					userAreaArray[rx][ry].setType(AreaType.MISS);
					infoLabel.setText("Computer missed!");}
				
				// Computer hit a ship
				if (userAreaArray[rx][ry].getType() == AreaType.SHIP){
					try{
						ImageIcon img = new ImageIcon(ImageIO.read(getClass().getResource("/hit.png")));
						userAreaArray[rx][ry].setIcon(img);
					} catch (Exception E) {E.printStackTrace();}
					infoLabel.setText("Computer hit one of your ships!");
					userAreaArray[rx][ry].setType(AreaType.HIT);}
				Thread.sleep(500);adjustButtons(compAreaArray, "enable");
				return null;
			}
		};
		
		// Start the SwingWorker
		sw.execute();
		
	}

	public static void adjustButtons(JButton[][] array, String statement){
		// Disables or enables buttons in an array
		if (statement.equals("disable")){
			for (int p = 0; p < array.length; p++){
				for (int q = 0; q < array.length; q++){
					array[p][q].setEnabled(false);
				}
			}
		}
		else {
			for (int p = 0; p < array.length; p++){
				for (int q = 0; q < array.length; q++){
					array[p][q].setEnabled(true);
				}
			}
		}
	}

	public static int countType(Area[][] array, AreaType type){
		// Counts the amount of specified AreaTypes in an Area array
		int count = 0;
		for (int p = 0; p < array.length; p++){
			for (int q = 0; q < array.length; q++){
				if (array[p][q].getType() == type){
					count++;
				}
			}
		}
		return count;
	}

	public static boolean isSafeToPlace(Area[][] array, Area area, int shipModel){
		// Checks collision and out-of-bounds possibilites
		boolean result = true;
		int ax = (int) (((area.getBounds().getCenterX() - 47) / 64));
		if (array == compAreaArray){
			ax = (16 - ax);
		}
		int ay = (int) ((area.getBounds().getCenterY() - 48) / 64);
		if (shipModel < 6){
			for (int p = 0; p < shipModel; p++){
				if ((ax + p > 7) || array[ay][ax + p].getType() == AreaType.SHIP){
					result = false;
				}
			}
		} else {
			System.out.println("#");
			System.out.println("ax: " + ax + ", ay: " + ay);
			for (int p = 0; p < (shipModel - 4); p++){
				System.out.println("p: " + p);
				if ((ay + p > 7) || array[ay + p][ax].getType() == AreaType.SHIP){
					result = false;
				}
			}
		}
		return result;
	}
}


package gridEditor.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import files.WriteFile;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import gridEditor.common.*;

import javax.swing.*;



public class GridEditorGui extends JFrame {

	private JPanel contentPane;
	private JPanel gridPanel;
	private JFileChooser openFileChooser;
	private int gridRows = 20;
	private int gridCols = 10;
	private int gridCellWidth = 25;
	private int gridCellHeight = 25;
	private JLabel[][] btnGrid;
	private JMenuItem mntmOpen;
	private JMenuItem mntmNew;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmExit;
	private JMenuItem mntmGridSize;
	private JCheckBoxMenuItem chckbxmntmSingleNodeMode;
	private JCheckBoxMenuItem chckbxmntmMultiNodeMode;
	private JMenuItem mntmAbout;
//	private String currentFile; //stores currently opened file for saving purposes
	private ArrayList<Frame> frames;
//	private ArrayList<JButton> btnFrame;
	private int currentFrame=0;
	private JPanel previewPanel;
	private JPanel addframePanel;
	private JPanel frameEditPanel;
	private JScrollPane scrollPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					GridEditorGui frame = new GridEditorGui();
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
	public GridEditorGui() {
		
		initComponents();
		createEvents();
		
	}
	
	/////////////////////////////////////////////////////
	// This method contains all of the code for creating 
	// and initializing components
	/////////////////////////////////////////////////////
	private void initComponents() {
		
		setTitle("GoofyGlasses Editor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GridEditorGui.class.getResource("/gridEditor/resources/glassesIcon_626.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Quick-Fix for now, the "+" and "-" Frame Button display over the rest of graph if too small
		setBounds(100, 100, 1000, 800);
		
		contentPane = new JPanel();
		gridPanel = new JPanel();
		scrollPane = new JScrollPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		frameEditPanel = new JPanel();
		
		openFileChooser = new JFileChooser();
		
		// Sets directory to user's home directory
		openFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TAN files", "tan"));
		
		initGrid();
				
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		mntmSaveAs = new JMenuItem("Save as...");
		mnFile.add(mntmSaveAs);
		
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		mntmGridSize = new JMenuItem("Grid Size");
		mnEdit.add(mntmGridSize);
		
		chckbxmntmSingleNodeMode = new JCheckBoxMenuItem("Single Node Mode");
		mnEdit.add(chckbxmntmSingleNodeMode);
		
		chckbxmntmMultiNodeMode = new JCheckBoxMenuItem("Multi Node Mode");
		mnEdit.add(chckbxmntmMultiNodeMode);
		
		JMenu mnWindow = new JMenu("Window");
		menuBar.add(mnWindow);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		if(frames==null){
			frames=new ArrayList<Frame>();
			frames.add(new Frame(gridRows, gridCols));
			
		// Add Frame Edit Panel
		contentPane.add(frameEditPanel, BorderLayout.CENTER);
		
		// Add a "+" Button to add a frame (Added here as button should never move or change)
		addframePanel = new JPanel();
		frameEditPanel.add(addframePanel);
		addframePanel.add(new JButton("Add Frame (+)"));
		
		createAddFrameEventHandler();
		//TODO: add the "-" button to remove frames as well
		
		
		}
	}
		
	////////////////////////////////////////////////////
	// This method contains all of the code for creating
	// events.
	////////////////////////////////////////////////////
	private void createEvents() {
		// TODO Double check to make sure all event handlers for the gui are handled
		
		/////////////////////////////////////////////////
		// Event Handlers for the "File" Menu
		////////////////////////////////////////////////
		
		// New File handler
		mntmNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		//TODO Code to create new TAN file
			}
		});
		
		// Open File handler
		mntmOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int returnValue = openFileChooser.showOpenDialog(mntmOpen);
				
				if (returnValue == JFileChooser.APPROVE_OPTION){
					try{
						File tanFile = openFileChooser.getSelectedFile();
						//currentFile=tanFile.getAbsolutePath();
						//System.out.println("Selected File: " + currentFile);
						//Loads Frames from file into temp ArrayList
						//If temp is empty do nothing
						ArrayList<Frame> temp=new ArrayList<Frame>();
						temp = TanFile.readFile(tanFile);
						if(temp.size()==0){
							return;
						}
						//Re-initialize grid based off of values from file
						frames=temp;
						//System.out.println(temp.get(0).getNodeColor(1, 1));
						
						gridRows=frames.get(0).getHeight();
						gridCols=frames.get(0).getWidth();
						initGrid();
						createNodeButtonEventHandlers();
						
					}
					catch(Exception e){
						System.err.println("Error opening file!");
						e.printStackTrace();
					}
				}
				else {
					//TODO Add message to inform no file was selected
					System.out.println("No file selected");
				}
			}
		});
		
		// Save File handler
		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		// code for saving tan file		
				try {
					File tanSaveFile = openFileChooser.getSelectedFile();
					if(!tanSaveFile.getName().endsWith(".tan")) {
						String path = tanSaveFile.getAbsolutePath() + ".tan";
						File newSaveFile = new File(path);
						TanFile.writeFile(newSaveFile, frames);
					}
					else {
						TanFile.writeFile(tanSaveFile, frames);	
					}
				}
				catch(Exception e1) {
					// show message for no filename given
					System.err.println("Error no filename specified!");
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "no filename given");
				}
			}
		});
		
		// Save as file handler
		mntmSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// code for the Save as function
				int returnValue = openFileChooser.showSaveDialog(mntmSaveAs);
				
				if(returnValue == JFileChooser.APPROVE_OPTION) {
					File tanSaveFile = openFileChooser.getSelectedFile();
					// check for tan extension
					if(!tanSaveFile.getName().endsWith(".tan")) {
						String path = tanSaveFile.getAbsolutePath() + ".tan";
						File newSaveFile = new File(path);
						TanFile.writeFile(newSaveFile, frames);
					}
					else {
						TanFile.writeFile(tanSaveFile, frames);	
					}
				}
			}
		});
		
		// Exit handler
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
		// TODO Check if current file is "saved" before exit		
				System.exit(0);
			}
		});
		
		////////////////////////////////////////////////////////
		// Event Handlers for the "Edit" Menu
		////////////////////////////////////////////////////////
		
		// Grid Size Event Handler
		mntmGridSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
		// TODO code to adjust grid size		
			}
		});
		
		//node buttons
		createNodeButtonEventHandlers();
	
	}
	
	/////////////////////////////////////////////////////
	// This method will be used to initialize the grid
	// Called at the start, and when a file is opened
	/////////////////////////////////////////////////////
	private void initGrid(){
		if(gridPanel!=null){
			gridPanel.removeAll();
			contentPane.remove(gridPanel);
			contentPane.invalidate();
		}
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(gridPanel, BorderLayout.NORTH);
		
		
		/* GridLayout seems to be responsible for the pane-width buttons
		 * According to SO this can be fixed by nesting the GridLayout inside
		 * of a flowLayout. I will work more on this later (SethF)
		 * 
		 * http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#border
		 */ 
		gridPanel.setLayout(new GridLayout(gridRows, gridCols, 1, 1)); // ... , 1, 1) adds buffer space to cells
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		// Add Frame Edit Panel (Below Grid, but above scroll pane)
		contentPane.add(frameEditPanel, BorderLayout.CENTER);
		
		previewPanel = new JPanel();
		scrollPane.setViewportView(previewPanel);
		
		if (frames != null){
			for(int f = 0; f < frames.size(); f++){
				JButton tempBtn = new JButton("Frame: " + f);
				previewPanel.add(tempBtn);
				tempBtn.setIcon(frames.get(f).getFrameIcon());
				
			}
		}
		
		createFrameButtonEventHandlers();
		
		btnGrid = new JLabel[gridRows][gridCols];
		for(int r = 0; r < gridRows; r++){
			for(int c = 0; c < gridCols; c++){
				createNodeButton(r, c);
				gridPanel.add(btnGrid[r][c]);
				if (frames != null){
					Color current=frames.get(currentFrame).getNodeColor(r, c);
					if (current != null){
						btnGrid[r][c].setOpaque(true);
						btnGrid[r][c].setBackground(current);
					}
				}
			}
		}
		
		contentPane.revalidate();
		//TODO: repainting is probably not the best solution. Improve if/when possible
		contentPane.repaint();
	}

	/////////////////////////////////////////////////////
	// This method creates the event handlers for the 
	// frame buttons
	/////////////////////////////////////////////////////
	private void createFrameButtonEventHandlers(){
		for(int i=0; i < previewPanel.getComponentCount(); i++){
			previewPanel.getComponent(i).addMouseListener(new FrameButtonActionListener(i){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					
					int newFrameNumber = this.getFrameNumber();
					if(newFrameNumber < frames.size()){
						currentFrame = newFrameNumber;
						initGrid();
						createNodeButtonEventHandlers();	
					}
					
					//previewPanel[newFrameNumber].setBackground(black);
				}
			});
		}
	}

	/////////////////////////////////////////////////////
	// This method creates event handlers for the add 
	// frame button
	/////////////////////////////////////////////////////
	private void createAddFrameEventHandler(){
		for(int l=0; l < addframePanel.getComponentCount(); l++){
			addframePanel.getComponent(l).addMouseListener(new FrameButtonActionListener(l){
				@Override
				public void mouseClicked(MouseEvent arg1) {
					
					// Acts very similar to the ReadFile.java class, need to get copy of current frame
					// and put it in the frames array list (add a new frame to out current list of frames)
					Frame tempFrame = new Frame();
					tempFrame.setStartingTime(frames.size() + 1);			// Not sure what value goes here yet (later functionality?)
					Node[][] tempNodeArr = new Node[gridRows][gridCols];
						
					for(int j=0; j<gridRows; j++)
					{
						for(int k=0; k<gridCols; k++) 
						{
							//get color value
							Color tempColor = frames.get(currentFrame).getNodeColor(j, k);
							//assign color to current node
							Node tempNode = new Node();
							tempNode.setColor(tempColor);
							tempNodeArr[j][k] = tempNode;
						}
					}
					// add node to the Frame
					//tempFrame.setFrameNum(frames.size() + 1);				// Will need to be change if more advanced frame editing
					tempFrame.setNodeGrid(tempNodeArr);						// is required
				
					// Add this copied frame to our list of frames
					frames.add(tempFrame);
					
					initGrid();
					createNodeButtonEventHandlers();
				}
			});
		}
	}

	/////////////////////////////////////////////////////
	// This method creates the event handlers for the node
	// buttons called at start, and when a file is opened
	/////////////////////////////////////////////////////
	private void createNodeButtonEventHandlers(){
		
		////////////////////////////////////////////////////////
		// node buttons
		////////////////////////////////////////////////////////
		for(int r = 0; r < gridRows; r++){
			for(int c = 0; c < gridCols; c++){
				btnGrid[r][c].addMouseListener(new NodeActionListener(r, c){

					@Override
					public void mouseClicked(MouseEvent arg0) {
						//System.out.println("Row: " + this.getRow() + " Col: " + this.getCol());
					    JTextField RedField = new JTextField(4);
					    JTextField GreenField = new JTextField(4);
					    JTextField BlueField = new JTextField(4);
					    Color current=frames.get(currentFrame).getNodeColor(this.getRow(), this.getCol());
					    //populate fields with current values
					    if(current!=null){
					    	RedField.setText(Integer.toString(current.getRed()));
					    	GreenField.setText(Integer.toString(current.getGreen()));
					    	BlueField.setText(Integer.toString(current.getBlue()));
					    }
					    JPanel ColorPanel = new JPanel();
					    ColorPanel.add(new JLabel("RED:"));
					    ColorPanel.add(RedField);
					    ColorPanel.add(Box.createHorizontalStrut(15)); // a spacer
					    ColorPanel.add(new JLabel("GREEN:"));
					    ColorPanel.add(GreenField);
					    ColorPanel.add(Box.createHorizontalStrut(15));
					    ColorPanel.add(new JLabel("BLUE:"));
					    ColorPanel.add(BlueField);
					     
					     int result = JOptionPane.showConfirmDialog(null, ColorPanel, 
					              "Please Enter RGB Values", JOptionPane.OK_CANCEL_OPTION);
					     if (result == JOptionPane.OK_OPTION){
					    	String red_s = RedField.getText();
					    	String green_s = GreenField.getText();
					    	String blue_s = BlueField.getText();
					    	int red_i = red_s.isEmpty() ? 0 : Integer.parseInt(red_s);
						    int green_i = green_s.isEmpty() ? 0 : Integer.parseInt(green_s);
							int blue_i = blue_s.isEmpty() ? 0 : Integer.parseInt(blue_s);
							//System.out.printf("RED: %d, GREEN: %d, BLUE: %d\n",red_i,green_i,blue_i);
							java.awt.Color temp_color = new Color(red_i,green_i,blue_i);
							btnGrid[this.getRow()][this.getCol()].setOpaque(true);
							//btnGrid[this.getRow()][this.getCol()].setContentAreaFilled(false);
							btnGrid[this.getRow()][this.getCol()].setBackground(temp_color);
							frames.get(currentFrame).setNodeColor(this.getRow(), this.getCol(), temp_color);
							// redraw the grid
							initGrid();
					     }
						
					}
					@Override
					public void mousePressed(MouseEvent e) {
						btnGrid[this.getRow()][this.getCol()].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
						
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						btnGrid[this.getRow()][this.getCol()].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
						
					}
				});
				
			
			}
		}
	}

	/////////////////////////////////////////////////////
	// Given row/column values, this method creates a 
	// button and sets its color
	/////////////////////////////////////////////////////
	private void createNodeButton(int row, int column){
			btnGrid[row][column] = new JLabel("R:" + row + " " + "C:" + column);
			btnGrid[row][column].setHorizontalAlignment(SwingConstants.CENTER);
			btnGrid[row][column].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			
			/* Supposedly setPreferredSize is bad practice, and this seems to not do anything anyways
			 * looking at another solution(SethF) 
			 * This is connected to the GridLayout issue @line ~295 (SethF)
			 */ 
//			btnGrid[row][column].setPreferredSize(new Dimension(gridCellWidth, gridCellHeight));
			
			if(frames != null){
				Color nodeColor = frames.get(currentFrame).getNodeColor(row, column);
				btnGrid[row][column].setOpaque(true);
				btnGrid[row][column].setBackground(nodeColor);
			}
		}
}

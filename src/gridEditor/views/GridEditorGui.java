package gridEditor.views;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.colorchooser.ColorSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.MenuItem;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;
import files.WriteFile;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import gridEditor.common.*;
import javafx.scene.control.ColorPicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.colorchooser.*;


public class GridEditorGui extends JFrame {
	int FRAME_AFTER=0;
	int FRAME_BEFORE=-1;
	int FRAME_END=1;
	private JPanel contentPane;
	private JPanel gridPanel;
	private JFileChooser openFileChooser;
	private int gridRows = 20;
	private int gridCols = 20;
	private JLabel[][] btnGrid;
	private FramePopupMenu popup;
	private JMenuItem mntmOpen;
	private JMenuItem mntmNew;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmExit;
	private JMenuItem mntmGridSize;
	private JCheckBoxMenuItem chckbxmntmSingleNodeMode;
	private JCheckBoxMenuItem chckbxmntmMultiNodeMode;
	private JMenuItem mntmAbout;
	private JButton addFrame;
	private JButton shiftUp; 
	private JButton shiftDown;
	private JButton shiftLeft;
	private JButton shiftRight;
	private JButton fillGrid;
	private JColorChooser colorChooser;
	private JTextField durationField;
	private JTextField startTimeField;
	private JLabel durationLabel;
	private JLabel startTimeLabel;
	//private ColorPicker colorPicker;
	private String currentFile; //stores currently opened file for saving purposes
	private ArrayList<Frame> frames;
	private int currentFrame=0;
	private JPanel gridConfigurePanel;
	private JPanel previewPanel;
	private JPanel frameActionPanel;
	private JPanel frameEditPanel;
	private JPanel colorPanel;
	private JPanel buttonPanel;
	private Color color;
	private Point scrollPoint = new Point(0,0);
	private JScrollPane scrollPane;
	private AnimationStatus animationStatus = AnimationStatus.STOPPED;
	private int revertFrame;
	private int defaultFrameDuration = 100;
	private int lastPressedRow=-1, lastPressedCol=-1;

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
		if(currentFile == null){
			setTitle("GoofyGlasses Editor");
		}
		else {
			setTitle("GoofyGlasses Editor " + currentFile);
		}
		setIconImage(Toolkit.getDefaultToolkit().getImage(GridEditorGui.class.getResource("/gridEditor/resources/glassesIcon_626.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Quick-Fix for now, the "+" and "-" Frame Button display over the rest of graph if too small
		setBounds(100, 100, 1000, 800);
		
		contentPane = new JPanel();
		gridPanel = new JPanel();
		scrollPane = new JScrollPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		gridConfigurePanel = new JPanel();
		gridConfigurePanel.setBorder(new EmptyBorder(5, 40, 5, 40));
		gridConfigurePanel.setSize(100, 400);
		gridConfigurePanel.setLayout(new BorderLayout(0, 0));
		frameEditPanel = new JPanel();
		colorPanel = new JPanel();
		
		
		openFileChooser = new JFileChooser();
		
		// Sets directory to user's home directory
		openFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TAN files", "tan"));
		
		createFrameRightClickMenu();
				
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
		
		
			// Add direction buttons Panel to right of Grid Panel
			contentPane.add(gridConfigurePanel, BorderLayout.EAST);
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(2,3));
			buttonPanel.setPreferredSize(new Dimension(60,60));
			
			// Add addFrame button
			addFrame = new JButton("Add Frame (+)");
			addFrame.setToolTipText("add blank frame to end of show");
			buttonPanel.add(addFrame);
			
			// Add directional up button
			shiftUp = new JButton();
			try 
			{
				Image img = ImageIO.read(getClass().getResource("/gridEditor/resources/icon-arrow-up.png"));
				shiftUp.setIcon(new ImageIcon(img));
			}catch (Exception ex) {
				System.out.println(ex);
			}
			shiftUp.setToolTipText("shift all nodes one node up");
			buttonPanel.add(shiftUp);
					
			// Add fill grid button
			fillGrid = new JButton("Fill Full Grid");
			fillGrid.setToolTipText("fill entire grid with selected color");
			buttonPanel.add(fillGrid);
		
			// Add directional left button
			shiftLeft = new JButton();
			try 
			{
				Image img = ImageIO.read(getClass().getResource("/gridEditor/resources/icon-arrow-left.png"));
				shiftLeft.setIcon(new ImageIcon(img));
			}catch (Exception ex) {
				System.out.println(ex);
			}
			shiftLeft.setToolTipText("shift all nodes one node left");
			buttonPanel.add(shiftLeft);
			
			
			// Add directional down button
			shiftDown = new JButton();
			try 
			{
				Image img = ImageIO.read(getClass().getResource("/gridEditor/resources/icon-arrow-down.png"));
				shiftDown.setIcon(new ImageIcon(img));
			}catch (Exception ex) {
				System.out.println(ex);
			}  
			shiftDown.setToolTipText("shift all nodes one node down");
			buttonPanel.add(shiftDown);
			
			
					
					
			// Add directional right button
			shiftRight = new JButton();
			try 
			{
				Image img = ImageIO.read(getClass().getResource("/gridEditor/resources/icon-arrow-right.png"));
				shiftRight.setIcon(new ImageIcon(img));
			}catch (Exception ex) {
				System.out.println(ex);
			}  
			shiftRight.setToolTipText("shift all nodes one node right");
			buttonPanel.add(shiftRight);
			
			
			
			AddTimeEditingGUI(gridConfigurePanel);
			
			gridConfigurePanel.add(buttonPanel,BorderLayout.NORTH);
			colorPanel = new JPanel();
			colorChooser = new JColorChooser();
			colorChooser.setPreviewPanel(new JPanel());
			AbstractColorChooserPanel[] panels = colorChooser.getChooserPanels();
			for(AbstractColorChooserPanel swatchesRGB:panels)
			{
				String displayName = swatchesRGB.getDisplayName();
				switch (displayName)
				{
					case "HSV":
						colorChooser.removeChooserPanel(swatchesRGB);
						break;
					case "HSL":
						colorChooser.removeChooserPanel(swatchesRGB);
						break;
					case "CMYK":
						colorChooser.removeChooserPanel(swatchesRGB);
						break;
				}
			}
			colorPanel.add(colorChooser,BorderLayout.CENTER);
			gridConfigurePanel.add(colorPanel,BorderLayout.CENTER);
			
						// Add Frame Edit Panel
			contentPane.add(frameEditPanel, BorderLayout.NORTH);
			
			// Add a "+" Button to add a frame (Added here as button should never move or change)
			frameActionPanel = new JPanel();
			
			frameEditPanel.add(frameActionPanel);
			/*
			addFrame = new JButton("Add Frame (+)");
			addFrame.setToolTipText("add blank frame to end of show");
			frameActionPanel.add(addFrame);
			*/
			frameActionPanel.add(new JButton("Play"));
			frameActionPanel.add(new JButton("Stop"));
			frameActionPanel.add(new JButton("Pause"));
	
			//createAddFrameEventHandler(frameActionPanel.getComponent(0));
			createPlayEventHandler(frameActionPanel.getComponent(0));
			createStopEventHandler(frameActionPanel.getComponent(1));
			createPauseEventHandler(frameActionPanel.getComponent(2));

		}
		initGrid();
		
	}
	
	private void AddTimeEditingGUI(JPanel gridConfigurePanel){
		JPanel timePanel = new JPanel();
		durationLabel = new JLabel("Duration (ms): ");
		startTimeLabel = new JLabel("Start time (ms): ");
		durationField  = new JTextField(6);
		startTimeField  = new JTextField(6);
		
		timePanel.add(durationLabel);
		timePanel.add(durationField);
		timePanel.add(startTimeLabel);
		timePanel.add(startTimeField);	
		
		AddDurationFieldListener();
		AddStartTimeFieldListener();
		
		gridConfigurePanel.add(timePanel, BorderLayout.SOUTH);
	}
	
	private void AddDurationFieldListener(){
		durationField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateDuration();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateDuration();

			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}
	
	private void AddStartTimeFieldListener(){
		startTimeField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				UpdateStartTime();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				UpdateStartTime();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
	}
	
	private void UpdateStartTime(){
		boolean validInput = EnsureStringIsInt(startTimeField);

		if(validInput == true){
			int newStartTime = newStartTime = Integer.parseInt(startTimeField.getText());
			
			int previousStartTime = -1;
			int nextStartTime = 0;
			if(currentFrame != 0){
				previousStartTime = frames.get(currentFrame-1).getStartingTime();
			}
			if(currentFrame != frames.size()-1){
				nextStartTime = frames.get(currentFrame+1).getStartingTime();
			}
			
			if((newStartTime >= nextStartTime && currentFrame != frames.size()-1) || newStartTime <= previousStartTime ||
					(currentFrame == 0 && newStartTime != 0)){
				startTimeLabel.setForeground(Color.RED);
				return;
			}
			
			frames.get(currentFrame).setStartingTime(newStartTime);
			int newDuration = getFrameDuration(currentFrame);
			durationField.setText(Integer.toString(newDuration));
			startTimeLabel.setForeground(Color.BLACK);
		}
		else{
			startTimeLabel.setForeground(Color.RED);
		}
	}
	
	private void UpdateDuration(){
		boolean validInput = EnsureStringIsInt(durationField);
		
		if(validInput == true){
			int newDuration = Integer.parseInt(durationField.getText());
			
			if(currentFrame != frames.size()-1){
				// Update subsequence frames
				int nextStartTime = frames.get(currentFrame+1).getStartingTime();
				int currentStartTime = frames.get(currentFrame).getStartingTime();
				int oldDuration = nextStartTime - currentStartTime;
				int durationDiff = newDuration - oldDuration;
				for(int i=currentFrame+1; i<frames.size(); i++){
					int oldStartTime = frames.get(i).getStartingTime();
					frames.get(i).setStartingTime(oldStartTime + durationDiff);
				}
			}

			durationLabel.setForeground(Color.BLACK);
		}
		else{
			durationLabel.setForeground(Color.RED);
		}
	}
	
	private boolean EnsureStringIsInt(JTextField textField){
		try{
			int value = Integer.parseInt(textField.getText());
			if(value < 0){
				return false;
			}
		}
		catch(NumberFormatException e){
			return false;
		}
		return true;
	}
	
	private void createPlayEventHandler(Component playButton){
		playButton.addMouseListener(new AnimationButtonActionListener(){
			@Override
			public void mouseClicked(MouseEvent arg1) {
				revertFrame = currentFrame;
				animationStatus = AnimationStatus.ANIMATING;
				Runnable updateGUIFrame = new Runnable(){
					public void run(){
						initGrid();
						createNodeButtonEventHandlers();
					}
				};
				
				// Start a separate thread to run the animation
				Thread animateThread = new Thread(){
					public void run(){
						while(animationStatus == AnimationStatus.ANIMATING && currentFrame < frames.size()){
							// Tell the event dispatch thread to update the GUI
							SwingUtilities.invokeLater(updateGUIFrame);
							int sleepTime = getFrameDuration(currentFrame);
							try {
								Thread.sleep(sleepTime);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							currentFrame++;
						}
						
						// This is executed after the animation is paused or stopped
						currentFrame--;
						if(animationStatus != AnimationStatus.PAUSED){
							currentFrame = revertFrame;
							SwingUtilities.invokeLater(updateGUIFrame);
						}
						animationStatus = AnimationStatus.STOPPED;
					}
				};
				animateThread.start();
			}
		});
	}
	
	private int getFrameDuration(int frameNumber){
		// Default nextStartTime to the the default duration milliseconds beyond the last frame
		int nextStartTime = frames.get(frames.size()-1).getStartingTime() + Frame.defaultFrameDuration;
		if(frameNumber < frames.size()-1){
			nextStartTime = frames.get(frameNumber+1).getStartingTime();
		}
		return nextStartTime - frames.get(frameNumber).getStartingTime();
	}
	
	private void createStopEventHandler(Component stopButton){
		stopButton.addMouseListener(new AnimationButtonActionListener(){
			@Override
			public void mouseClicked(MouseEvent arg1) {
				animationStatus = AnimationStatus.STOPPED;
				
			}
		});
	}
	
	private void createPauseEventHandler(Component pauseButton){
		pauseButton.addMouseListener(new AnimationButtonActionListener(){
			@Override
			public void mouseClicked(MouseEvent arg1) {
				animationStatus = AnimationStatus.PAUSED;
			}
		});
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
				//Code to create new TAN file

				JTextField rows = new JTextField(3);
				JTextField columns = new JTextField(3);
				JPanel newPanel = new JPanel();
				newPanel.add(new JLabel("rows:"));
				newPanel.add(rows);
				newPanel.add(Box.createHorizontalStrut(10));
				newPanel.add(new JLabel("columns:"));
				newPanel.add(columns);
				
				// pop up menu prompt for grid dimensions
				try {
					boolean validInput = false;
					do {
						int result = JOptionPane.showConfirmDialog(null, newPanel, "Enter frame dimensions", JOptionPane.OK_CANCEL_OPTION);
						if (result == JOptionPane.OK_OPTION) {
							if(!isInteger(rows.getText()) || !isInteger(columns.getText())) {
								JOptionPane.showMessageDialog(null, "Must enter an integer from 1 to 20.");
							}
							else {
								gridRows = Integer.parseInt(rows.getText());
								gridCols = Integer.parseInt(columns.getText());	
								frames = null;
								validInput = true;
							}
						}
						if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
							break;
						}
					} while(!validInput);
					
					if (validInput) {
						initComponents();
						createEvents();
					}
				}
				catch(Exception e2) {
					JOptionPane.showMessageDialog(null, "Error Entering Dimensions");
				}
				
				
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
						currentFile=tanFile.getAbsolutePath();
						setTitle("GoofyGlasses Editor " + currentFile);
						//System.out.println("Selected File: " + currentFile);
						//Loads Frames from file into temp ArrayList
						//If temp is empty do nothing
						frames = TanFile.readFile(tanFile);
						if(frames.size()==0){
							return;
						}
						
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
						setTitle("GoofyGlasses Editor " + path);
						File newSaveFile = new File(path);
						TanFile.writeFile(newSaveFile, frames);
					}
					else {
						TanFile.writeFile(tanSaveFile, frames);
						currentFile = tanSaveFile.getAbsolutePath();
						setTitle("GoofyGlasses Editor " + currentFile);
					}
				}
				catch(Exception e1) {
					saveAsDialog(mntmSaveAs);
				}
			}
		});
		
		// Save as file handler
		mntmSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveAsDialog(mntmSaveAs);
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
		/////////////////////////////////////////////////////////
		// Event Handlers for the cell shifting buttons
		////////////////////////////////////////////////////////
		shiftUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create an array of Colors (This is to hold row 1 colors, as they will
				// be changed before the last row can access them, so we must store them)
				Color rowColor[] = new Color[gridCols];

				// Go through each row and column, have the current node take the color of the next
				// row (which will be below it) making it appear as the nodes are moving up
				for(int r = 0; r < gridRows; r++){
					for(int c = 0; c < gridCols; c++){
						Color next;
						if (r < gridRows - 1)
							next = frames.get(currentFrame).getNodeColor(r+1, c);
						else
							next = rowColor[c];
						if (r == 0)
							rowColor[c] = frames.get(currentFrame).getNodeColor(0, c);
						btnGrid[r][c].setOpaque(true);
						btnGrid[r][c].setBackground(next);
						frames.get(currentFrame).setNodeColor(r, c, next);
					}
				}
				// redraw the grid
				initGrid();
				createNodeButtonEventHandlers();
			}
		});
		
		shiftDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				// Create an array of Colors (This is to hold last row colors, as they will
				// be changed before the first row can access them, so we must store them)
				Color rowColor[] = new Color[gridCols];

				// Go through each row and column, have the current node take the color of the previous
				// row (which will be above it) making it appear as the nodes are moving down
				for(int r = gridRows-1; r >= 0; r--){
					for(int c = 0; c < gridCols; c++){
						Color next;
						if (r >= 1)
							next = frames.get(currentFrame).getNodeColor(r-1, c);
						else
							next = rowColor[c];
						if (r == gridRows-1)
							rowColor[c] = frames.get(currentFrame).getNodeColor(gridRows-1, c);
						btnGrid[r][c].setOpaque(true);
						btnGrid[r][c].setBackground(next);
						frames.get(currentFrame).setNodeColor(r, c, next);
					}
				}
				// redraw the grid
				initGrid();
				createNodeButtonEventHandlers();
			}
		});
		
		shiftLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				// Create an array of Colors (This is to hold column 1 colors, as they will
				// be changed before the last column can access them, so we must store them)
				Color rowColor[] = new Color[gridRows];

				// Go through each row and column, have the current node take the color of the next
				// column (which will be to the right) making it appear as the nodes are moving left
				for(int r = 0; r < gridRows; r++){
					for(int c = 0; c < gridCols; c++){
						Color next;
						if (c < gridCols - 1)
							next = frames.get(currentFrame).getNodeColor(r, c+1);
						else
							next = rowColor[r];
						if (c == 0)
							rowColor[r] = frames.get(currentFrame).getNodeColor(r, 0);
						btnGrid[r][c].setOpaque(true);
						btnGrid[r][c].setBackground(next);
						frames.get(currentFrame).setNodeColor(r, c, next);
					}
				}
				// redraw the grid
				initGrid();
				createNodeButtonEventHandlers();
			}
		});
		
		shiftRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Create an array of Colors (This is to hold last col of colors, as they will
				// be changed before the first col can access them, so we must store them)
				Color rowColor[] = new Color[gridRows];

				// Go through each row and column, have the current node take the color of the previous
				// col (which will be to the left) making it appear as the nodes are moving right
				for(int r = 0; r < gridRows; r++){
					for(int c = gridCols - 1; c >= 0; c--){
						Color next;
						if (c >= 1)
							next = frames.get(currentFrame).getNodeColor(r, c-1);
						else
							next = rowColor[r];
						if (c == gridCols - 1)
							rowColor[r] = frames.get(currentFrame).getNodeColor(r, gridCols-1);
						btnGrid[r][c].setOpaque(true);
						btnGrid[r][c].setBackground(next);
						frames.get(currentFrame).setNodeColor(r, c, next);
					}
				}
				// redraw the grid
				initGrid();
				createNodeButtonEventHandlers();
			}
		});
		
		
		fillGrid.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			//Fill entire grid with the selected color
				for(int r = 0; r < gridRows; r++){
					for(int c = 0; c < gridCols; c++){
					
						color = colorChooser.getColor();
					
						btnGrid[r][c].setOpaque(true);
						btnGrid[r][c].setBackground(color);
						frames.get(currentFrame).setNodeColor(r, c, color);
					}
				}
				// redraw the grid
				initGrid();
				createNodeButtonEventHandlers();
			}
		});
		
		addFrame.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
			//Fill entire grid with the selected color
			addFrame(popup.getFrameNumber(), false, FRAME_AFTER);
			}	
		});
	}
	
	/////////////////////////////////////////////////////
	// Method for SaveAs and Save
	// Opens up dialog box for saving a file
	/////////////////////////////////////////////////////
	private void saveAsDialog(JMenuItem mntmSaveAs){
		int returnValue = openFileChooser.showSaveDialog(mntmSaveAs);
		
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File tanSaveFile = openFileChooser.getSelectedFile();
			// check for tan extension
			if(!tanSaveFile.getName().endsWith(".tan")) {
				String path = tanSaveFile.getAbsolutePath() + ".tan";
				currentFile = path;
				setTitle("GoofyGlasses Editor " + currentFile);
				File newSaveFile = new File(path);
				TanFile.writeFile(newSaveFile, frames);
			}
			else {
				TanFile.writeFile(tanSaveFile, frames);	
				currentFile = tanSaveFile.getAbsolutePath();
				setTitle("GoofyGlasses Editor " + currentFile);
			}
		}
	}
	/////////////////////////////////////////////////////
	// Method to determine if input string is an integer 
	// from 1 to 20 - valid row and column sizes
	/////////////////////////////////////////////////////
	private boolean isInteger(String n) {
		boolean validity = false;
		try {
			int i =Integer.parseInt(n);
			if(i > 0 && i < 21) {
				validity = true;
			}
		}
		catch(Exception e) {
			validity = false;
		}
		return validity;
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
		
		contentPane.add(gridPanel, BorderLayout.CENTER);
		
		
		/* GridLayout seems to be responsible for the pane-width buttons
		 * According to SO this can be fixed by nesting the GridLayout inside
		 * of a flowLayout. I will work more on this later (SethF)
		 * 
		 * http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html#border
		 */ 
		gridPanel.setLayout(new GridLayout(gridRows, gridCols, 1, 1)); // ... , 1, 1) adds buffer space to cells
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		// Add Frame Edit Panel (Below Grid, but above scroll pane), fixes issue scroll bar when loading in frame
		contentPane.add(frameEditPanel, BorderLayout.NORTH);
		
		// Add girdConfigurePanel (fixes issue scroll bar when loading in frame)
		contentPane.add(gridConfigurePanel, BorderLayout.EAST);
		
		previewPanel = new JPanel();
		scrollPane.setViewportView(previewPanel);
		
		if (frames != null){
			for(int f = 0; f < frames.size(); f++){
				JButton tempBtn = new JButton("Frame: " + f);
				previewPanel.add(tempBtn);
				tempBtn.setIcon(frames.get(f).getFrameIcon());
				
				if (currentFrame == f)
				tempBtn.setBackground(Color.blue);
			}
		}
		
		createFrameButtonEventHandlers();
		
		// Set The Position that was Saved after selecting a frame 
		scrollPane.getViewport().setViewPosition(scrollPoint);
		
		btnGrid = new JLabel[gridRows][gridCols];
		for(int r = 0; r < gridRows; r++){
			for(int c = 0; c < gridCols; c++){
				createNodeButton(r, c);
				gridPanel.add(btnGrid[r][c]);
			}
		}
		
		SetTimeFields();
		
		contentPane.revalidate();
		//TODO: repainting is probably not the best solution. Improve if/when possible
		contentPane.repaint();
	}

	private void SetTimeFields(){
		int startingTime = frames.get(currentFrame).getStartingTime();
		int nextStartingTime = startingTime + Frame.defaultFrameDuration;
		if(currentFrame != frames.size()-1){
			nextStartingTime = frames.get(currentFrame+1).getStartingTime();
		}
		int duration = nextStartingTime - startingTime;
		startTimeField.setText(Integer.toString(startingTime)); 
		durationField.setText(Integer.toString(duration));
	}
	
	/////////////////////////////////////////////////////
	// This method creates the event handlers for the 
	// frame buttons
	/////////////////////////////////////////////////////
	private void createFrameButtonEventHandlers(){
		for(int i=0; i < previewPanel.getComponentCount(); i++){
			previewPanel.getComponent(i).addMouseListener(new FrameButtonActionListener(i){
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getButton()!=1) return;//only changes frame on left click
					int newFrameNumber = this.getFrameNumber();
					if(newFrameNumber < frames.size()){
						currentFrame = newFrameNumber;
						// Save the current position
						scrollPoint = scrollPane.getViewport().getViewPosition();
						initGrid();
						createNodeButtonEventHandlers();	
					}
					
					//previewPanel[newFrameNumber].setBackground(black);
				}
				@Override
				public void mousePressed(MouseEvent e){
					if(e.isPopupTrigger()){
						popup.show(e.getComponent(), e.getX(), e.getY());
						popup.setFrameNumber(this.getFrameNumber());
					}
				}
				
				@Override
				public void mouseReleased(MouseEvent e){
					if(e.isPopupTrigger()){
						popup.show(e.getComponent(), e.getX(), e.getY());
						popup.setFrameNumber(this.getFrameNumber());
					}
				}
			});
		}
	}
	
	//this method creates the right click menu for the frame previews
	//also adds listeners for the menu items
	private void createFrameRightClickMenu(){
		popup = new FramePopupMenu(0);
	    JMenuItem menuItem=new JMenuItem("Delete Frame");
	    menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				deleteFrame(popup.getFrameNumber());
			}
	    });
	    popup.add(menuItem);
	    
	    menuItem=new JMenuItem("Insert Blank Frame After");
	    menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFrame(popup.getFrameNumber(), false, FRAME_AFTER);
			}
	    });
	    popup.add(menuItem);
	    
	    menuItem=new JMenuItem("Insert Blank Frame Before");
	    menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFrame(popup.getFrameNumber(), false, FRAME_BEFORE);
			}
	    });    
	    popup.add(menuItem);
	    
	    menuItem=new JMenuItem("Duplicate Frame");
	    menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFrame(popup.getFrameNumber(), true, FRAME_AFTER);
			}
	    });
	    popup.add(menuItem);
	    
	    menuItem=new JMenuItem("Duplicate Frame at End");
	    menuItem.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addFrame(popup.getFrameNumber(), true, FRAME_END);
			}
	    });
	    popup.add(menuItem);
	}
	
	//this method handles all creation of new frames
	void addFrame(int frameNum, boolean copy, int frameLoc){
		// Acts very similar to the ReadFile.java class, need to get copy of current frame
		// and put it in the frames array list (add a new frame to out current list of frames)
		Frame tempFrame = new Frame(gridRows, gridCols);
		int previousStartingTime;
		int frameDuration=Frame.defaultFrameDuration;
		
		if(copy){
			//copies frame duration
			frameDuration = getFrameDuration(frameNum);
			for(int j=0; j<gridRows; j++)
			{
				for(int k=0; k<gridCols; k++) 
				{
					//get color value
					Color tempColor = frames.get(currentFrame).getNodeColor(j, k);
					//assign color to current node
					tempFrame.setNodeColor(j, k, tempColor);
				}
			}
		}
		
		
		//calculates starting time and adds frame to appropriate location
		if(frameLoc==FRAME_END){
			previousStartingTime = frames.get(frames.size()-1).getStartingTime();
			frames.add(tempFrame);
			currentFrame = frames.size()-1;
		}
		else if(frameLoc==FRAME_BEFORE){
			previousStartingTime=frames.get(frameNum).getStartingTime();
			//shifts subsequent Frames' starting times
			for(int x=frameNum; x<frames.size();x++){
				frames.get(x).adjustStartingTime(frameDuration);
			}
			frames.add(frameNum, tempFrame);
			currentFrame = frameNum;
		}
		else{
			if(frameNum<frames.size()-1){
				previousStartingTime=frames.get(frameNum+1).getStartingTime();
			}
			else{
				previousStartingTime=frames.get(frameNum).getStartingTime()+getFrameDuration(frameNum);
			}
			//shifts subsequent Frames' starting times
			for(int x=frameNum+1; x<frames.size();x++){
				frames.get(x).adjustStartingTime(frameDuration);
			}
			frames.add(frameNum+1, tempFrame);
			currentFrame = frameNum+1;
			
		}
		//calculates scroll bar position: if Frame added at end, scroll to max, else keep current position
		JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
		if(frameLoc==FRAME_END||(frameLoc==FRAME_AFTER&&frameNum==frames.size()-2)){
			scrollPoint = new Point(horizontal.getMaximum(), 0);
		}
		else{
			scrollPoint = new Point(horizontal.getValue(), 0);
		}
		//sets new Frame's starting time				
		tempFrame.setStartingTime(previousStartingTime + frameDuration);	
		
		initGrid();
		createNodeButtonEventHandlers();
	}
	
	//this method handles deletion of a frame
	void deleteFrame(int frameNum){
		//if only one frame, clear it
		if(frames.size()==1){
			frames.set(0, new Frame(gridRows, gridCols));
		}
		//otherwise, remove frame and shift other frames' starting times as needed
		else{
			int duration=getFrameDuration(frameNum);
			frames.remove(frameNum);
			for(int x=frameNum;x<frames.size();x++){	
				frames.get(x).adjustStartingTime(0-duration);
			}
		}
		//if last frame was selected, select the new last frame
		if(currentFrame>frames.size()-1){
			currentFrame=frames.size()-1;
		}
		initGrid();
		createNodeButtonEventHandlers();
	}

	/////////////////////////////////////////////////////
	// This method creates event handler for the add 
	// frame button
	/////////////////////////////////////////////////////
	private void createAddFrameEventHandler(Component addFrameButton ){
		addFrameButton.addMouseListener(new FrameButtonActionListener(-1){
			@Override
			public void mouseClicked(MouseEvent arg1) {
				addFrame(currentFrame, false, FRAME_END);
			}
		});
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
						if(arg0.isShiftDown()&&lastPressedRow>-1){
							int maxRow=this.getRow()>lastPressedRow ? this.getRow() : lastPressedRow;
							int minRow=this.getRow()<lastPressedRow ? this.getRow() : lastPressedRow;
							int maxCol=this.getCol()>lastPressedCol ? this.getCol() : lastPressedCol;
							int minCol=this.getCol()<lastPressedCol ? this.getCol() : lastPressedCol;
							fillNodes(colorChooser.getColor(), minRow, minCol, maxRow, maxCol);
						}
						else{
							color = colorChooser.getColor();
							btnGrid[this.getRow()][this.getCol()].setOpaque(true);
							btnGrid[this.getRow()][this.getCol()].setBackground(color);
							frames.get(currentFrame).setNodeColor(this.getRow(), this.getCol(), color);
							// redraw the grid
							initGrid();
							createNodeButtonEventHandlers();
						}
						lastPressedRow=this.getRow();
						lastPressedCol=this.getCol();
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
	
	//TODO: Call this method to set background color
	//this method fills nodes that were shift selected
	private void fillNodes(Color color, int minRow, int minCol, int maxRow, int maxCol){
		for(int i=minRow;i<=maxRow;i++){
			for(int j=minCol;j<=maxCol;j++){
				color = colorChooser.getColor();
				btnGrid[i][j].setOpaque(true);
				btnGrid[i][j].setBackground(color);
				frames.get(currentFrame).setNodeColor(i, j, color);
				
			}
		}
		initGrid();
		createNodeButtonEventHandlers();
	}

	/////////////////////////////////////////////////////
	// Given row/column values, this method creates a 
	// button and sets its color
	/////////////////////////////////////////////////////
	private void createNodeButton(int row, int column){
		//btnGrid[row][column] = new JLabel("R:" + row + " " + "C:" + column);
		btnGrid[row][column] = new JLabel();
		btnGrid[row][column].setHorizontalAlignment(SwingConstants.CENTER);
		btnGrid[row][column].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			
		/* Supposedly setPreferredSize is bad practice, and this seems to not do anything anyways
		 * looking at another solution(SethF) 
		 * This is connected to the GridLayout issue @line ~295 (SethF)
		 */ 
//		btnGrid[row][column].setPreferredSize(new Dimension(gridCellWidth, gridCellHeight));
		
		if(frames != null){
			Color nodeColor = frames.get(currentFrame).getNodeColor(row, column);
			btnGrid[row][column].setOpaque(true);
			btnGrid[row][column].setBackground(nodeColor);
		}
	}	
}

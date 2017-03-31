package gridEditor.views;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import java.awt.Toolkit;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.filechooser.FileNameExtensionFilter;

import files.ReadFile;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;

import gridEditor.common.*;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.BoxLayout;
import java.awt.Component;

public class GridEditorGui extends JFrame {

	private JPanel contentPane;
	private JPanel gridPanel;
	private JFileChooser openFileChooser;
	private int gridRows = 20;
	private int gridCols = 10;
	private int gridCellWidth = 75;
	private int gridCellHeight = 25;
	private JButton[][] btnGrid;
	private JMenuItem mntmOpen;
	private JMenuItem mntmNew;
	private JMenuItem mntmSave;
	private JMenuItem mntmSaveAs;
	private JMenuItem mntmExit;
	private JMenuItem mntmGridSize;
	private JCheckBoxMenuItem chckbxmntmSingleNodeMode;
	private JCheckBoxMenuItem chckbxmntmMultiNodeMode;
	private JMenuItem mntmAbout;
	private String currentFile;//stores currently opened file for saving purposes
	private ArrayList<Frame> frames;
//	private ArrayList<JButton> btnFrame;
	private int currentFrame=0;
	private JPanel previewPanel;
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
		setBounds(100, 100, 800, 600);
		
		contentPane = new JPanel();
		gridPanel = new JPanel();
		scrollPane = new JScrollPane();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		openFileChooser = new JFileChooser();
		
		// Sets directory to user's home directory
		openFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TANG files", "tang"));
		
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
			frames.add(new Frame(gridCols, gridRows));
		}
	}
	
	//This method will be used to initialize the grid
	//Called at the start, and when a file is opened
	private void initGrid(){
		if(gridPanel!=null){
			contentPane.remove(gridPanel);
			contentPane.invalidate();
		}
		contentPane.setLayout(new BorderLayout(0, 0));
		
		contentPane.add(gridPanel, BorderLayout.NORTH);
		gridPanel.setLayout(new GridLayout(gridRows, gridCols, 0, 0));
		
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		previewPanel = new JPanel();
		scrollPane.setViewportView(previewPanel);
		
		if (frames != null){
			// btnFrame = new ArrayList<JButton>();
			for(int f = 0; f < frames.size(); f++){
				previewPanel.add(new JButton("Frame: " + f));
			}
		}
		
		btnGrid = new JButton[gridRows][gridCols];
		for(int r = 0; r < gridRows; r++){
			for(int c = 0; c < gridCols; c++){
				btnGrid[r][c] = new JButton("R:" + r + " " + "C:" + c);
				btnGrid[r][c].setPreferredSize(new Dimension(gridCellWidth, gridCellHeight));
				gridPanel.add(btnGrid[r][c]);
			}
		}
		contentPane.revalidate();
		//TODO: repainting is probably not the best solution. Improve if/when possible
		contentPane.repaint();
	}

	//This method creates the event handlers for the node buttons
	//Called at start, and when a file is opened
	private void createGridButtons(){
		////////////////////////////////////////////////////////
		// node buttons
		////////////////////////////////////////////////////////
		for(int r = 0; r < gridRows; r++){
			for(int c = 0; c < gridCols; c++){
				btnGrid[r][c].addActionListener(new NodeActionListener(r,c){
					public void actionPerformed(ActionEvent e){
						System.out.println("Button Row = " + this.getRow()  + " Column =  " + this.getCol());
					}
				});
			}
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
			public void actionPerformed(ActionEvent e) {
		//TODO Code to create new TANG file
			}
		});
		
		// Open File handler
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnValue = openFileChooser.showOpenDialog(mntmOpen);
				
				if (returnValue == JFileChooser.APPROVE_OPTION){
					try{
						File tangFile = openFileChooser.getSelectedFile();
						currentFile=tangFile.getAbsolutePath();
						System.out.println("Selected File: " + currentFile);
						//Loads Frames from file into temp ArrayList
						//If temp is empty do nothing
						ArrayList<Frame> temp=new ArrayList<Frame>();
						ReadFile.readFile(currentFile, temp);
						if(temp.size()==0){
							return;
						}
						//Re-initialize grid based off of values from file
						frames=temp;
						gridRows=frames.get(0).getHeight();
						gridCols=frames.get(0).getWidth();
						initGrid();
						createGridButtons();
						
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
			public void actionPerformed(ActionEvent e) {
		//TODO add code for saving tang file		
			}
		});
		
		// Save as file handler
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		// TODO add code for the Save as function	
			}
		});
		
		// Exit handler
		mntmExit.addActionListener(new ActionListener() {
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
			public void actionPerformed(ActionEvent e) {
		// TODO code to adjust grid size		
			}
		});
		
		//node buttons
		createGridButtons();
	
	}
}

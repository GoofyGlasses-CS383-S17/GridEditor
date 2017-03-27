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

import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GridEditorGui extends JFrame {

	private JPanel contentPane;
	private JPanel gridPanel;
	private final JFileChooser openFileChooser;
	private int gridRows = 10;
	private int gridCols = 20;

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
		
		openFileChooser = new JFileChooser();
		// Line below allows you to set the directory the file chooser starts at. Uncomment and change path to use this feature
		// Default should be users home directory I think...
		// openFileChooser.setCurrentDirectory(new File("c:\\temp"));
		openFileChooser.setFileFilter(new FileNameExtensionFilter("TANG files", "tang"));
		
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		
		
		gridPanel = new JPanel();
		contentPane.add(gridPanel);
		gridPanel.setLayout(new GridLayout(gridRows, gridCols, 0, 0));
		
		JButton[][] btnGrid = new JButton[gridRows][gridCols];
		for(int r = 0; r < gridRows; r++){
			for(int c = 0; c < gridCols; c++){
				btnGrid[r][c] = new JButton();
				btnGrid[r][c].setPreferredSize(new Dimension(200, 200));
				gridPanel.add(new JButton());
			}
		}
		
				
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnValue = openFileChooser.showOpenDialog(mntmOpen);
				
				if (returnValue == JFileChooser.APPROVE_OPTION){
					File tangFile = openFileChooser.getSelectedFile();
					//TODO Pass file to parser or read method to read it into the program
					System.out.println("Selected File: " + tangFile.getAbsolutePath());
				}
				else {
					//TODO Add message to inform no file was selected
					
				}
			}
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmGridSize = new JMenuItem("Grid Size");
		mnEdit.add(mntmGridSize);
		
		JCheckBoxMenuItem chckbxmntmSingleNodeMode = new JCheckBoxMenuItem("Single Node Mode");
		mnEdit.add(chckbxmntmSingleNodeMode);
		
		JCheckBoxMenuItem chckbxmntmMultiNodeMode = new JCheckBoxMenuItem("Multi Node Mode");
		mnEdit.add(chckbxmntmMultiNodeMode);
		
		JMenu mnWindow = new JMenu("Window");
		menuBar.add(mnWindow);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		
	}
	
	////////////////////////////////////////////////////
	// This method contains all of the code for creating
	// events.
	////////////////////////////////////////////////////
	private void createEvents() {
		// TODO Auto-generated method stub
		
	}
}

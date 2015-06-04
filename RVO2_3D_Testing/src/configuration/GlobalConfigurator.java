package configuration;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import java.awt.FileDialog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import search.multiobjective.MultiObjectiveSearch;
import sim.display.GUIState;
import sim.engine.SimState;
import tools.UTILS;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class GlobalConfigurator extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	public static Configuration config= Configuration.getInstance();
	private String file;
	private BufferedReader br;
	
	private JButton btnNext;
	private JButton btnFinish;
	private JButton btnOpen;
	private JButton btnLoad;
	private JTextField alertTimeTextField;

	public GlobalConfigurator(SimState simState, GUIState guiState) 
	{
		setLayout(null);
		
		JRadioButton rdbtnSSEnable = new JRadioButton("SS Enable?");
		rdbtnSSEnable.setBounds(12, 20, 102, 15);
		this.add(rdbtnSSEnable);
		rdbtnSSEnable.setSelected(config.globalConfig.selfSeparationEnabler);
		rdbtnSSEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.selfSeparationEnabler = true;
				} else {
					
					config.globalConfig.selfSeparationEnabler = false;
				}
			}
		});
		
		JRadioButton rdbtnAccidentDetectorEnable = new JRadioButton("AccidentDetector Enable?");
		rdbtnAccidentDetectorEnable.setBounds(12, 39, 229, 15);
		this.add(rdbtnAccidentDetectorEnable);
		rdbtnAccidentDetectorEnable.setSelected(config.globalConfig.accidentDetectorEnabler);
		rdbtnAccidentDetectorEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.accidentDetectorEnabler = true;
				} else {
					
					config.globalConfig.accidentDetectorEnabler = false;
				}
			}
		});
		
		JRadioButton rdbtnSensorNoiseEnable = new JRadioButton("Sensor noise enable?");
		rdbtnSensorNoiseEnable.setBounds(12, 57, 254, 23);
		add(rdbtnSensorNoiseEnable);
		rdbtnSensorNoiseEnable.setSelected(config.globalConfig.sensorNoiseEnabler);
		rdbtnSensorNoiseEnable.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.sensorNoiseEnabler = true;
				} else {
					
					config.globalConfig.sensorNoiseEnabler = false;
				}
			}
		});
		
		
		JRadioButton rdbtnSensorValueUncertainty = new JRadioButton("Sensor value uncertainty?");
		rdbtnSensorValueUncertainty.setBounds(12, 83, 229, 15);
		this.add(rdbtnSensorValueUncertainty);
		rdbtnSensorValueUncertainty.setSelected(config.globalConfig.sensorValueUncertainty);
		rdbtnSensorValueUncertainty.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.sensorValueUncertainty = true;
				} else {
					
					config.globalConfig.sensorValueUncertainty = false;
				}
			}
		});
		
		JRadioButton rdbtnEncounterGenerator = new JRadioButton("encounter generator enable?");
		rdbtnEncounterGenerator.setBounds(12, 107, 290, 15);
		this.add(rdbtnEncounterGenerator);
		rdbtnEncounterGenerator.setSelected(config.globalConfig.encounterGeneratorEnabler);
		rdbtnEncounterGenerator.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.encounterGeneratorEnabler = true;
				} else {
					
					config.globalConfig.encounterGeneratorEnabler = false;
				}
			}
		});
		
		JRadioButton rdbtnWhiteNoiseEnabler = new JRadioButton("white noise enable?");
		rdbtnWhiteNoiseEnabler.setBounds(12, 127, 229, 15);
		this.add(rdbtnWhiteNoiseEnabler);
		rdbtnWhiteNoiseEnabler.setSelected(config.globalConfig.whiteNoiseEnabler);
		rdbtnWhiteNoiseEnabler.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if(((JRadioButton)e.getSource()).isSelected())
				{
					config.globalConfig.whiteNoiseEnabler = true;
				} else {
					
					config.globalConfig.whiteNoiseEnabler = false;
				}
			}
		});
		
		JLabel lblAlertTime = new JLabel("Alert time");
		lblAlertTime.setBounds(12, 150, 129, 23);
		add(lblAlertTime);
		
		alertTimeTextField = new JTextField();
		alertTimeTextField.setText(""+config.globalConfig.alertTime);
		alertTimeTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.globalConfig.alertTime= Integer.parseInt(alertTimeTextField.getText());
			}
		});
		alertTimeTextField.setBounds(173, 152, 86, 20);
		add(alertTimeTextField);
		alertTimeTextField.setColumns(10);
			
		btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				String result = JOptionPane.showInputDialog(null, "copy and paste:", "Genome",JOptionPane.PLAIN_MESSAGE);

				if(result!=null && !result.isEmpty())
				{
					MultiObjectiveSearch.genomeString2Config(result);					
					if(br!=null)
					{
						System.err.println("you forgoet to close the file");
					}
					   
					SAAConfigurator theSAAConfigurator = ((SAAConfigurator)((JButton)e.getSource()).getRootPane().getParent());
					theSAAConfigurator.refresh();	
				}
			}
		});
		btnLoad.setBounds(22, 185, 77, 25);
		this.add(btnLoad);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
			       
				String result = JOptionPane.showInputDialog(null, "please add comments:", "Comments",JOptionPane.PLAIN_MESSAGE);
			
				String comment = "";
				if(result!=null && !result.isEmpty())
				{
					comment = result.trim();
				}
				
	        	StringBuilder dataItem = new StringBuilder();
	        	dataItem.append(comment+",");

	        	for(String intruder:config.encountersConfig.keySet())
	        	{
	        		EncounterConfig encounterConfig = config.encountersConfig.get(intruder);
		        	dataItem.append(encounterConfig.CAPY+",");
		        	dataItem.append(encounterConfig.CAPR+",");
		        	dataItem.append(encounterConfig.CAPTheta+",");
		        	dataItem.append(encounterConfig.CAPVy+",");
		        	dataItem.append(encounterConfig.CAPGs+",");
		        	dataItem.append(encounterConfig.CAPBearing+",");	      	
	        	}	        	
	        	UTILS.writeDataItem2CSV("./src/tools/ChallengingDB_MaxNMAC.csv", dataItem.toString(), true);	        	        		
			}					
		});
		btnSave.setBounds(221, 185, 77, 25);
		this.add(btnSave);				
		
		
		{
			JPanel filePanel = new JPanel();
			filePanel.setBackground(Color.YELLOW);
			filePanel.setForeground(Color.YELLOW);
			filePanel.setBounds(22, 222, 276, 127);
			add(filePanel);
			filePanel.setLayout(null);
			
			final JLabel lblFile = new JLabel("filename");
			lblFile.setBounds(88, 12, 176, 26);
			filePanel.add(lblFile);
			
			btnOpen = new JButton("open");
			btnOpen.setBounds(12, 13, 70, 25);
			filePanel.add(btnOpen);
			btnOpen.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					
					FileDialog fd = new FileDialog((SAAConfigurator)((JButton)e.getSource()).getRootPane().getParent(), "select a file", FileDialog.LOAD);
					String workingDir = System.getProperty("user.dir");
					fd.setDirectory(workingDir);
					fd.setFile("*.csv");
					fd.setVisible(true);
					String filename = fd.getFile();
					if(filename!=null)
					{
						file= fd.getDirectory()+fd.getFile();
						lblFile.setText(filename);
//						System.out.println(file);
						try {
							br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
							br.readLine();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						
						btnNext.setEnabled(true);
						btnFinish.setEnabled(true);
						btnOpen.setEnabled(false);
						btnLoad.setEnabled(false);
					}
				}
			});
			
			final JLabel lblCurrentConfig = new JLabel("");
			lblCurrentConfig.setBounds(10, 86, 256, 30);
			filePanel.add(lblCurrentConfig);
			
			btnNext = new JButton("Next");// won't refresh the panel
			btnNext.setBounds(12, 50, 66, 25);
			filePanel.add(btnNext);
			btnNext.setEnabled(false);
			btnNext.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e) 
				{
					try 
					{					
						String line = br.readLine();
//						System.out.println(line);
						if(line!=null && !line.isEmpty())
						{
							MultiObjectiveSearch.genomeString2Config(line);																
							SAAConfigurator theSAAConfigurator = ((SAAConfigurator)((JButton)e.getSource()).getRootPane().getParent());
							theSAAConfigurator.refresh();	
						}
						else
						{
							lblCurrentConfig.setText("null, "+ "try next!");
						}
						
						
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}			
		
				}
			});
			
			btnFinish = new JButton("Finish");
			btnFinish.setBounds(120, 50, 76, 25);
			filePanel.add(btnFinish);
			btnFinish.setEnabled(false);
			btnFinish.addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent e) 
				{
					if(br!=null)
					{
						// Done with the file
						try {
							br.close();
							br = null;
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					btnNext.setEnabled(false);
					btnFinish.setEnabled(false);
					btnOpen.setEnabled(true);
					btnLoad.setEnabled(true);
				}
			});	
			
		}
			
		{			
			JPanel performancePanel = new JPanel();
			performancePanel.setBackground(Color.LIGHT_GRAY);
			performancePanel.setBounds(12, 361, 290, 143);
			add(performancePanel);
			performancePanel.setLayout(null);
			JLabel lblMaxspeed = new JLabel("MaxGS");
			lblMaxspeed.setBounds(12, 14, 82, 15);
			performancePanel.add(lblMaxspeed);
			
			
			JTextField maxSpeedTextField_1 = new JTextField();
			maxSpeedTextField_1.setBounds(170, 14, 114, 19);
			performancePanel.add(maxSpeedTextField_1);
			maxSpeedTextField_1.setText(String.valueOf(config.globalConfig.maxGS));
			maxSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxSpeedTextField = (JTextField) e.getSource();
					config.globalConfig.maxGS = new Double(maxSpeedTextField.getText());
				}
			});
			maxSpeedTextField_1.setColumns(10);
			
			
			JLabel lblMinspeed = new JLabel("MinGS");
			lblMinspeed.setBounds(12, 43, 70, 19);
			performancePanel.add(lblMinspeed);
			
			
			JTextField minSpeedTextField_1 = new JTextField();
			minSpeedTextField_1.setBounds(170, 45, 114, 19);
			performancePanel.add(minSpeedTextField_1);
			minSpeedTextField_1.setText(String.valueOf(config.globalConfig.minGS));
			minSpeedTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField minSpeedTextField = (JTextField) e.getSource();
					config.globalConfig.minGS = new Double(minSpeedTextField.getText());
				}
			});
			minSpeedTextField_1.setColumns(10);
					
			JLabel lblMaxClimb = new JLabel("MaxVS");
			lblMaxClimb.setBounds(11, 75, 70, 19);
			performancePanel.add(lblMaxClimb);
			
			
			JTextField maxClimbTextField_1 = new JTextField();
			maxClimbTextField_1.setBounds(169, 73, 114, 19);
			performancePanel.add(maxClimbTextField_1);
			maxClimbTextField_1.setText(String.valueOf(config.globalConfig.maxVS));
			maxClimbTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxClimbTextField = (JTextField) e.getSource();
					config.globalConfig.maxVS = new Double(maxClimbTextField.getText());
				}
			});
			maxClimbTextField_1.setColumns(10);
			
			JLabel lblMaxDescent = new JLabel("MinVS");
			lblMaxDescent.setBounds(11, 105, 101, 19);
			performancePanel.add(lblMaxDescent);
			
			
			JTextField maxDescentTextField_1 = new JTextField();
			maxDescentTextField_1.setBounds(169, 107, 114, 19);
			performancePanel.add(maxDescentTextField_1);
			maxDescentTextField_1.setText(String.valueOf(config.globalConfig.minVS));
			maxDescentTextField_1.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					JTextField maxDescentTextField = (JTextField) e.getSource();
					config.globalConfig.minVS = new Double(maxDescentTextField.getText());
				}
			});
			maxDescentTextField_1.setColumns(10);
			
		}
	
		
		{
			JPanel noisePanel = new JPanel();
			noisePanel.setBounds(12, 546, 306, 95);
			add(noisePanel);
			noisePanel.setLayout(null);
			
			JLabel lblStdDevX = new JLabel("SDX");
			lblStdDevX.setBounds(15, 5, 28, 15);
			noisePanel.add(lblStdDevX);
			
			final JLabel stdDevXLabel = new JLabel(""+config.globalConfig.stdDevX);
			stdDevXLabel.setBounds(250, 6, 56, 15);
			noisePanel.add(stdDevXLabel);
			
			JSlider ownshipStdDevXSlider = new JSlider();
			ownshipStdDevXSlider.setBounds(53, 5, 193, 16);
			noisePanel.add(ownshipStdDevXSlider);
			ownshipStdDevXSlider.setSnapToTicks(true);
			ownshipStdDevXSlider.setPaintLabels(true);		
			ownshipStdDevXSlider.setMaximum(3);
			ownshipStdDevXSlider.setMinimum(0);
			ownshipStdDevXSlider.setValue((int)(config.globalConfig.stdDevX));
			ownshipStdDevXSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.globalConfig.stdDevX = source.getValue();
					stdDevXLabel.setText(""+config.globalConfig.stdDevX);
				}
			});
			
			JLabel lblStdDevY = new JLabel("SDY");
			lblStdDevY.setBounds(15, 32, 27, 15);
			noisePanel.add(lblStdDevY);
			
			final JLabel stdDevYLabel = new JLabel(""+config.globalConfig.stdDevY);
			stdDevYLabel.setBounds(250, 31, 56, 15);
			noisePanel.add(stdDevYLabel);
			
			JSlider ownshipStdDevYSlider = new JSlider();
			ownshipStdDevYSlider.setBounds(53, 31, 193, 16);
			noisePanel.add(ownshipStdDevYSlider);
			ownshipStdDevYSlider.setSnapToTicks(true);
			ownshipStdDevYSlider.setPaintLabels(true);		
			ownshipStdDevYSlider.setMaximum(3);
			ownshipStdDevYSlider.setMinimum(0);
			ownshipStdDevYSlider.setValue((int)(config.globalConfig.stdDevY));
			ownshipStdDevYSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.globalConfig.stdDevY = source.getValue();
					stdDevYLabel.setText(""+config.globalConfig.stdDevY);
				}
			});
		
			
			JLabel lblStdDevZ = new JLabel("SDZ");
			lblStdDevZ.setBounds(15, 59, 29, 15);
			noisePanel.add(lblStdDevZ);
			
			final JLabel stdDevZLabel = new JLabel(""+config.globalConfig.stdDevZ);
			stdDevZLabel.setBounds(250, 58, 56, 15);
			noisePanel.add(stdDevZLabel);
			
			JSlider ownshipStdDevZSlider = new JSlider();
			ownshipStdDevZSlider.setBounds(53, 58, 193, 16);
			noisePanel.add(ownshipStdDevZSlider);
			ownshipStdDevZSlider.setSnapToTicks(true);
			ownshipStdDevZSlider.setPaintLabels(true);		
			ownshipStdDevZSlider.setMaximum(3);
			ownshipStdDevZSlider.setMinimum(0);
			ownshipStdDevZSlider.setValue((int)(config.globalConfig.stdDevZ));
			ownshipStdDevZSlider.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent e) {
					JSlider source = (JSlider) e.getSource();
					config.globalConfig.stdDevZ = source.getValue();
					stdDevZLabel.setText(""+config.globalConfig.stdDevZ);
				}
			});
			
		}			
			
	}
}

package org.dang0x5f;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.awt.Insets;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;
import javax.swing.JSplitPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.DefaultTableModel;

public class Gfx
{
	private App 			   app;
	
    private JFrame             frame;
    private Border             border;
    private GridBagConstraints constraints;
    private GridBagConstraints constraints2;

    private JMenuBar           menu_bar;
    private JMenu              file_menu;
    private JMenu              edit_menu;
    private JMenu              view_menu;
    private JMenu              about_menu;

    private JPanel             global_pane;
    private JPanel             playlist_container;
    private JLabel             playlist_label;
    private JList<String>      playlist_options;
    private JPanel             thumbnail_container;
    private JLabel             thumbnail_label;
    private JList<String>      thumbnail_options;

    private JTabbedPane        tab_pane;

    private JSplitPane         audio_tab;

    private JPanel             audio_top;
    private JLabel             format_label;
    private JComboBox          format_list;
    private JTextField         url_field;
    private JButton            download_button;
    private JLabel             download_dir;
    private JFileChooser       chooser_dir;
    private JButton            chooser_button;

    private JPanel             audio_bot;
    private JTable             table;
    private JScrollPane        table_scroll;
    private DefaultTableModel  table_model;

    private JPanel             video_tab;

    public Gfx(App _app)
    {
    	app = _app;
    	
        init();
        createFrame();
        createMenuBar();
        createGlobalPane();
        createTabPane();

        download_button.addActionListener(new DownloadActionThread(this)); 
    }

    private void init()
    {
        border = BorderFactory.createEtchedBorder();

        constraints         = new GridBagConstraints();
        constraints.fill    = GridBagConstraints.HORIZONTAL;
        constraints.anchor  = GridBagConstraints.PAGE_START;
        constraints.insets  = new Insets(10,3,5,3);

        constraints2        = new GridBagConstraints();
        constraints2.fill   = GridBagConstraints.HORIZONTAL;
        constraints2.anchor = GridBagConstraints.PAGE_START;
        constraints2.insets = new Insets(5,5,5,5);
    }

    private void createFrame()
    {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(512,380);
    }

    private void createMenuBar()
    {
        menu_bar   = new JMenuBar();
        file_menu  = new JMenu("file");
        edit_menu  = new JMenu("edit");
        view_menu  = new JMenu("view");
        about_menu = new JMenu("about");

        menu_bar.add(file_menu);
        menu_bar.add(edit_menu);
        menu_bar.add(view_menu);
        menu_bar.add(about_menu);

        frame.setJMenuBar(menu_bar);
    }

    private void createTabPane()
    {
        tab_pane  = new JTabbedPane();
        tab_pane.setBorder(new EmptyBorder(10,0,0,0));

        createAudioPane();
        createVideoPane();

        tab_pane.addTab("audio",audio_tab);
        tab_pane.addTab("video",video_tab);

        frame.add(tab_pane,BorderLayout.CENTER);
    }

    private void createAudioPane()
    {
        audio_top = new JPanel(new GridBagLayout());
        audio_top.setBorder(new EmptyBorder(5,10,5,10));
        populateAudioTop();
        audio_bot = new JPanel(new GridLayout());
        populateAudioBot();

        audio_tab = new JSplitPane(JSplitPane.VERTICAL_SPLIT,audio_top,audio_bot);
    }

    private void populateAudioTop()
    {
        String audio_formats[] = {
            "aac"
           ,"alac"
           ,"flac"
           ,"m4a"
           ,"mp3"
           ,"opus"
           ,"vorbis"
           ,"wav"
        };

        format_label = new JLabel("audio format");
        format_list  = new JComboBox<String>(audio_formats);
        format_list.setSelectedIndex(4);

        url_field       = new JTextField(50);
        // url_field.setBorder(new EmptyBorder(5,5,5,5));
        download_button = new JButton("submit");

        download_dir = new JLabel(" . . . . . ."); /* change to default value */
        download_dir.setBorder(border);
        chooser_dir  = new JFileChooser();
        chooser_dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser_button = new JButton("select destination");
        chooser_button.addActionListener(e -> {
            chooser_dir.showOpenDialog((Component)e.getSource());
            download_dir.setText((chooser_dir.getSelectedFile()).getPath());
        });

        constraints2.gridx     = 0;
        constraints2.gridy     = 0;
        audio_top.add(format_label,constraints2);

        constraints2.gridx     = 2;
        constraints2.gridy     = 0;
        audio_top.add(format_list,constraints2);

        constraints2.gridx     = 0;
        constraints2.gridy     = 1;
        constraints2.weightx   = 1;
        constraints2.gridwidth = 4;
        audio_top.add(url_field,constraints2);

        constraints2.gridx     = 4;
        constraints2.gridy     = 1;
        constraints2.weightx   = 0;
        constraints2.gridwidth = 1;
        audio_top.add(download_button,constraints2);

        constraints2.gridx     = 0;
        constraints2.gridy     = 2;
        constraints2.weightx   = 1;
        constraints2.gridwidth = 4;
        audio_top.add(download_dir,constraints2);

        constraints2.gridx     = 4;
        constraints2.gridy     = 2;
        constraints2.weightx   = 0;
        constraints2.gridwidth = 1;
        audio_top.add(chooser_button,constraints2);
    }

    private void populateAudioBot()
    {
        table_model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int a, int b) { return false; }
        };
        table_model.addColumn("name");
        table_model.addColumn("format");
        table_model.addColumn("progress");
        table_model.addColumn("status");
        table_model.addColumn("pid");
        table_model.addColumn("#");
        
        table = new JTable(table_model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(25);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(4).setPreferredWidth(25);
        table.getColumnModel().getColumn(5).setPreferredWidth(25);
        table.setShowGrid(true);

        table_scroll = new JScrollPane(table);

        audio_bot.add(table_scroll);
    }
    
    private void createVideoPane()
    {
        video_tab = new JPanel();
    }

    private void createGlobalPane()
    {
        global_pane = new JPanel();
        global_pane.setLayout(new GridBagLayout());

        playlist_container = new JPanel();
        playlist_container.setBorder(border);
        playlist_container.setLayout(new GridLayout(2,1));

        thumbnail_container = new JPanel();
        thumbnail_container.setBorder(border);
        thumbnail_container.setLayout(new GridLayout(2,1));

        String options[] = {"include","exclude"};

        playlist_label    = new JLabel("playlist", SwingConstants.CENTER);
        playlist_options  = new JList<String>(options);
        playlist_options.setSelectedIndex(1);
        thumbnail_label   = new JLabel("thumbnail",SwingConstants.CENTER);
        thumbnail_options = new JList<String>(options);
        thumbnail_options.setSelectedIndex(1);

        playlist_container.add(playlist_label);
        playlist_container.add(playlist_options);
        thumbnail_container.add(thumbnail_label);
        thumbnail_container.add(thumbnail_options);

        constraints.gridx   = 0;
        constraints.gridy   = 0;
        global_pane.add(playlist_container,constraints);
        constraints.gridx   = 0;
        constraints.gridy   = 1;
        constraints.weighty = 1;
        global_pane.add(thumbnail_container,constraints);

        frame.add(global_pane,BorderLayout.WEST);
    }

    public String getURL()
    {
        return url_field.getText();
    }

    public void setURL(String s)
    {
        url_field.setText(s);
    }

    public String getFormat()
    {
        return (String)format_list.getSelectedItem();
    }

    public int getPlaylistIndex()
    {
        return playlist_options.getSelectedIndex();
    }
    
    public int getThumbnailIndex()
    {
        return thumbnail_options.getSelectedIndex();
    }

    public void addNewRow(Object[] row)
    {
        table_model.addRow(row);
    }

    public int getNumOfRows()
    {
        return table_model.getRowCount();
    }

    public void setNameField(String name, int row, int col)
    {
        table.setValueAt(name,row,col);
    }
    
    public void setFormatField(String format, int row, int col)
    {
        table.setValueAt(format,row,col);
    }

    public void setProgressBarValue(int value, int row, int col)
    {
        table.setValueAt(value,row,col);
    }

    public void setStatusValue(int value, int row, int col)
    {
        table.setValueAt(value, row, col);
    }

    /* TODO: needs testing on windows */
    public String getPath()
    {
        if(Files.exists(Paths.get(download_dir.getText())))
        	if(getOS().equals("Windows 10"))
        		return download_dir.getText() + '\\';
        	else
				return download_dir.getText() + '/';
        else
            return "";
    }

    public void display()
    {
        this.frame.pack();
        this.frame.setVisible(true);
    }

    public String getOS()
    {
    	return app.getOS();
    }
    
    public String getUsername()
    {
    	return app.getUsername();
    }
    
    public String getHome()
    {
    	return app.getHome();
    }

    public String getPWD()
    {
    	return app.getPWD();
    }
}

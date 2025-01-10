import java.io.File;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.util.Vector;
import java.lang.StringBuilder;
import javax.swing.JFileChooser;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;

import java.io.InputStream;
import java.io.BufferedReader;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableColumn;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;

public class temp 
{
    public static JTable table;
    public static DefaultTableModel table_model;
    private static final String progress_opt_str = " --newline --progress-template >%(progress._percent_str)s%(info.title)s ";
    private static final String output_opt_str   = " -o %(title)s.%(ext)s ";
    private static final String audio_opt_str    = " -x --audio-format ";

    public static void main(String[] args)
    {

        Border etched_border = BorderFactory.createEtchedBorder();

        JPanel global_option_panel = new JPanel();
        // global_option_panel.setBackground(Color.MAGENTA);
        // global_option_panel.setLayout(new BoxLayout(global_option_panel,BoxLayout.Y_AXIS));
        global_option_panel.setLayout(new GridBagLayout());
        // global_option_panel.setBorder(etched_border);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.insets = new Insets(20, 0, 0, 0);
        // gbc.weighty = 1;
        


        JPanel playlist_pan = new JPanel();
        // playlist_pan.setToolTipText("is this a playlist?");
        playlist_pan.setLayout(new GridLayout(2,1));
        playlist_pan.setBorder(etched_border);
        JLabel playlist_lbl = new JLabel("playlist",SwingConstants.CENTER);
        playlist_lbl.setBorder(etched_border);

        String g_opt_playlist[] = { "include", "exclude" };
        JList<String> playlist_listbox = new JList<String>(g_opt_playlist);
        playlist_listbox.setSelectedIndex(1);
        playlist_pan.add(playlist_lbl);
        playlist_pan.add(playlist_listbox);



        JPanel thumbnail_pan = new JPanel();
        thumbnail_pan.setLayout(new GridLayout(2,1));
        thumbnail_pan.setBorder(etched_border);
        JLabel thumbnail_lbl = new JLabel("thumbnail",SwingConstants.CENTER);
        thumbnail_lbl.setBorder(etched_border);

        String g_opt_thumbnail[] = { "include", "exclude" };
        JList<String> thumbnail_listbox = new JList<String>(g_opt_thumbnail);
        thumbnail_listbox.setSelectedIndex(1);
        thumbnail_pan.add(thumbnail_lbl);
        thumbnail_pan.add(thumbnail_listbox);


        gbc.gridx = 0;
        gbc.gridy = 0;
        global_option_panel.add(playlist_pan,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weighty = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        global_option_panel.add(thumbnail_pan,gbc);



        // audio options
        // audio_panel.setBackground(Color.YELLOW);

        JPanel audio_downloads = new JPanel(new GridLayout());
        audio_downloads.setBackground(Color.ORANGE);
        JProgressBar pgb1 = new JProgressBar();
        pgb1.setMaximum(100);
        pgb1.setValue(25);

        
        table_model = new DefaultTableModel()
        {
            @Override
            public boolean isCellEditable(int a, int b)
            {
                return(false);
            }
        };
        table_model.addColumn("name");
        table_model.addColumn("format");
        table_model.addColumn("progress");
        table_model.addColumn("status");
        table = new JTable(table_model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(30);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setCellRenderer(new ProgressCellRender());
        table.getColumnModel().getColumn(3).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setCellRenderer(new AudioExtractCellRender());
        table.setShowGrid(true);

        JScrollPane dl_scrollpane = new JScrollPane(table);
        audio_downloads.add(dl_scrollpane);


        String audio_formats[] = { "aac", "alac", "flac", "m4a", "mp3", "opus", "vorbis", "wav" };
        // JList<String> list_box = new JList<String>(audio_formats);
        // list_box.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        JComboBox<String> list_box = new JComboBox<String>(audio_formats);
        list_box.setSelectedIndex(4);
        // JScrollPane scroll_pane = new JScrollPane(list_box);
        
        JLabel format_label = new JLabel("format");
        JTextField url_textfield = new JTextField(50);

        JButton dl_button = new JButton("download");
        dl_button.addActionListener(e -> {
                
            SwingWorker worker_thread = new SwingWorker<Integer,Void>() {
                private int rowcount;

                @Override
                public Integer doInBackground(){

                    StringBuilder cmd = new StringBuilder("yt-dlp ");

                    if(playlist_listbox.getSelectedIndex() == 0)
                        cmd.append(" --yes-playlist ");
                    else
                        cmd.append(" --no-playlist ");

                    if(thumbnail_listbox.getSelectedIndex() == 0)
                        cmd.append(" --embed-thumbnail ");

                    cmd.append(progress_opt_str);
                    cmd.append(audio_opt_str + list_box.getSelectedItem());
                    cmd.append(" --audio-quality 0 ");
                    cmd.append(output_opt_str);
                    cmd.append(url_textfield.getText());
                    
                    try{

                        Process proc = Runtime.getRuntime().exec(cmd.toString());

                        url_textfield.setText("");
                        InputStream input_stream = proc.getInputStream();

                        rowcount = 0;
                        char line[] = new char[256];
                        int c, x=0, round=0;
                        while((c=input_stream.read()) != -1){
                            if((char)c == '>'){
                                while((char)(c=input_stream.read()) != '\n')
                                    line[x++] = (char)c;
                                // line[x] = '\0';

                                if(round < 1){
                                    Object[] row = { new String(line).substring(6), list_box.getSelectedItem(), 0, 0 };
                                    table_model.addRow(row);
                                    rowcount = table_model.getRowCount();
                                    round++;
                                } else {
                                    table.setValueAt((int)Float.parseFloat(new String(line).substring(0,5)),rowcount-1,2);
                                }
                            }
                            else if((char)c == 'D'){
                                round = 0;
                                table.setValueAt(1,rowcount-1,3);
                                while((char)(c=input_stream.read()) != '\n')
                                    line[x++] = (char)c;
                            }else{
                                while((char)(c=input_stream.read()) != '\n')
                                    line[x++] = (char)c;
                            }


                            for(int i = 0; i < line.length; i++)
                                line[i] = '\0';
                            x = 0;
                        }

                        proc.waitFor();    
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }

                    return(0);
                }

                @Override
                public void done(){
                    table.setValueAt(100,rowcount-1,2);
                    table.setValueAt(1,rowcount-1,3);
                }

            };
            worker_thread.execute();

        });


        JPanel audio_options = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        // gbc2.fill = GridBagConstraints.HORIZONTAL;
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.anchor = GridBagConstraints.PAGE_START;
        gbc2.insets = new Insets(5,5,5,5);
        gbc2.gridx = 0; 
        gbc2.gridy = 0; 
        audio_options.add(format_label,gbc2);
        gbc2.gridx = 2; 
        gbc2.gridy = 0; 
        audio_options.add(list_box,gbc2);
        gbc2.gridx = 0; 
        gbc2.gridy = 1; 
        gbc2.weightx = 1;
        gbc2.gridwidth = 4;
        audio_options.add(url_textfield,gbc2);
        gbc2.gridx = 4; 
        gbc2.gridy = 1; 
        gbc2.weightx = 0;
        gbc2.gridwidth = 1;
        audio_options.add(dl_button,gbc2);
        JLabel download_dir = new JLabel(". . . . . . . . . . .");
        gbc2.gridx = 0;
        gbc2.gridy = 2;
        gbc2.weightx = 1;
        gbc2.gridwidth = 4;
        audio_options.add(download_dir,gbc2);
        JFileChooser dir_chooser = new JFileChooser();
        dir_chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JButton dir_btn = new JButton("choose dir...");
        dir_btn.addActionListener(e -> {
            // File dirpath;
            dir_chooser.showOpenDialog((Component)e.getSource());
            // dirpath = dir_chooser.getSelectedFile();
            // download_dir.setText(dirpath.getPath());
            download_dir.setText((dir_chooser.getSelectedFile()).getPath());
        });
        gbc2.gridx = 4; 
        gbc2.gridy = 2; 
        gbc2.weightx = 0;
        gbc2.gridwidth = 1;
        audio_options.add(dir_btn,gbc2);
        // audio_panel.add(scroll_pane);
        // audio_panel.add(url_textfield);

        JSplitPane audio_panel = new JSplitPane(JSplitPane.VERTICAL_SPLIT,audio_options,audio_downloads);





        JPanel video_panel = new JPanel();



        // meun bar 
        JMenuBar menu_bar = new JMenuBar();
        JMenu menu_file = new JMenu("File");
        JMenu menu_edit = new JMenu("Edit");
        JMenu menu_view = new JMenu("View");
        JMenu menu_about = new JMenu("About");
        // add to bar
        menu_bar.add(menu_file);
        menu_bar.add(menu_edit);
        menu_bar.add(menu_view);
        menu_bar.add(menu_about);

        // tab_pane + add to pane
        JTabbedPane tab_pane = new JTabbedPane();
        tab_pane.addTab("audio",audio_panel);
        tab_pane.addTab("video",video_panel);

        // frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(512,380);

        // add to frame
        frame.setJMenuBar(menu_bar);
        frame.add(tab_pane,BorderLayout.CENTER);
        frame.add(global_option_panel,BorderLayout.WEST);


        frame.setVisible(true);
    }

    public static class ProgressCellRender extends JProgressBar implements TableCellRenderer 
    {

        private JProgressBar bar;

        public ProgressCellRender()
        {
            bar = new JProgressBar(0,100);
            bar.setStringPainted(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            int progress = 0;
            // if (value instanceof Float) {
            //     progress = Math.round((Float) value) ;
            // } else if (value instanceof Integer) {
                progress = (int) value;
            // }
            bar.setValue(progress);
            return bar;
        }
    }

    public static class AudioExtractCellRender extends ImageIcon implements TableCellRenderer
    {
        private JLabel label_cell;
        private final String done       = "done.";
        private final String extracting = "extracting...";

        public AudioExtractCellRender()
        {
            label_cell = new JLabel(extracting);

            label_cell.setHorizontalAlignment(JLabel.CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if((int)value == 0){
                label_cell.setText(extracting);
                return label_cell;
            }

            label_cell.setText(done);
            return label_cell;
        }
    }

}


import java.awt.Color;
import java.awt.Insets;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import javax.swing.JRootPane;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.WindowConstants;
import javax.swing.SwingConstants;

public class Main 
{
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
        playlist_pan.add(playlist_lbl);
        playlist_pan.add(playlist_listbox);



        JPanel thumbnail_pan = new JPanel();
        thumbnail_pan.setLayout(new GridLayout(2,1));
        thumbnail_pan.setBorder(etched_border);
        JLabel thumbnail_lbl = new JLabel("thumbnail",SwingConstants.CENTER);
        thumbnail_lbl.setBorder(etched_border);

        String g_opt_thumbnail[] = { "include", "exclude" };
        JList<String> thumbnail_listbox = new JList<String>(g_opt_thumbnail);
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
        JPanel audio_panel = new JPanel();
        // audio_panel.setBackground(Color.YELLOW);
        String audio_formats[] = { "aac", "alac", "flac", "m4a", "mp3", "opus", "vorbis", "wav" };
        JList<String> list_box = new JList<String>(audio_formats);
        audio_panel.add(list_box);

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
        frame.setSize(400,300);

        // add to frame
        frame.setJMenuBar(menu_bar);
        frame.add(tab_pane,BorderLayout.CENTER);
        frame.add(global_option_panel,BorderLayout.WEST);


        frame.setVisible(true);
    }
}

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import javax.swing.JRootPane;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.WindowConstants;

public class Main 
{
    public static void main(String[] args)
    {
        // west panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        JPanel global_option_panel = new JPanel(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 0;
        String g_opt_playlist[] = { "playlist", "no playlist" };
        JList<String> playlist_listbox = new JList<String>(g_opt_playlist);
        global_option_panel.add(playlist_listbox, gbc);

        gbc.gridy = 1;
        String g_opt_thumbnail[] = { "thumbnail", "no thumbnail" };
        JList<String> thumbnail_listbox = new JList<String>(g_opt_thumbnail);
        global_option_panel.add(thumbnail_listbox, gbc);

        // audio options
        JPanel audio_panel = new JPanel();
        String audio_formats[] = { "aac", "alac", "flac", "m4a", "mp3", "opus", "vorbis", "wav" };
        JList<String> list_box = new JList<String>(audio_formats);
        audio_panel.add(list_box);

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
        tab_pane.addTab("video",null);

        // frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(400,300);
        frame.setVisible(true);

        // add to frame
        frame.setJMenuBar(menu_bar);
        frame.add(tab_pane);
        frame.add(global_option_panel,BorderLayout.WEST);
    }
}

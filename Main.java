import java.awt.Component;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

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

        Border etched_border = BorderFactory.createEtchedBorder();

        JPanel global_option_panel = new JPanel();
        global_option_panel.setLayout(new BoxLayout(global_option_panel,BoxLayout.Y_AXIS));

        JPanel playlist_pan = new JPanel();
        // playlist_pan.setToolTipText("is this a playlist?");
        playlist_pan.setLayout(new BoxLayout(playlist_pan,BoxLayout.Y_AXIS));
        playlist_pan.setBorder(etched_border);
        JLabel playlist_lbl = new JLabel("playlist");
        playlist_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        String g_opt_playlist[] = { "include", "exclude" };
        JList<String> playlist_listbox = new JList<String>(g_opt_playlist);
        playlist_pan.add(playlist_lbl);
        playlist_pan.add(playlist_listbox);
        playlist_pan.add(Box.createHorizontalGlue());

        JPanel thumbnail_pan = new JPanel();
        thumbnail_pan.setLayout(new BoxLayout(thumbnail_pan,BoxLayout.Y_AXIS));
        thumbnail_pan.setBorder(etched_border);
        JLabel thumbnail_lbl = new JLabel("thumbnail");
        thumbnail_lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        String g_opt_thumbnail[] = { "include", "exclude" };
        JList<String> thumbnail_listbox = new JList<String>(g_opt_thumbnail);
        thumbnail_pan.add(thumbnail_lbl);
        thumbnail_pan.add(thumbnail_listbox);
        thumbnail_pan.add(Box.createHorizontalGlue());


        global_option_panel.add(playlist_pan);
        global_option_panel.add(thumbnail_pan);




        // audio options
        JPanel audio_panel = new JPanel();
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
        frame.setVisible(true);

        // add to frame
        frame.setJMenuBar(menu_bar);
        frame.add(tab_pane);
        frame.add(global_option_panel,BorderLayout.WEST);
    }
}

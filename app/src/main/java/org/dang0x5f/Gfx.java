package org.dang0x5f;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.JTabbedPane;

public class Gfx
{
    private JFrame      frame;

    private JMenuBar    menu_bar;
    private JMenu       file_menu;
    private JMenu       edit_menu;
    private JMenu       view_menu;
    private JMenu       about_menu;

    private JPanel        global_pane;
    private JLabel        playlist_label;
    private JList<String> playlist_options;
    private JLabel        thumbnail_label;
    private JList<String> thumbnail_options;

    private JTabbedPane tab_pane;
    private JPanel      audio_tab;
    private JPanel      video_tab;

    public Gfx()
    {
        createFrame();
        createMenuBar();
        createGlobalPane();
        createTabPane();
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
        audio_tab = new JPanel();
        video_tab = new JPanel();

        tab_pane.addTab("audio",audio_tab);
        tab_pane.addTab("video",video_tab);

        frame.add(tab_pane,BorderLayout.CENTER);
    }

    private void createGlobalPane()
    {
        global_pane = new JPanel();

        String options[] = {"include","exclude"};

        playlist_label    = new JLabel("playlist", SwingConstants.CENTER);
        playlist_options  = new JList<String>(options);
        thumbnail_label   = new JLabel("thumbnail",SwingConstants.CENTER);
        thumbnail_options = new JList<String>(options);

        global_pane.add(playlist_label);
        global_pane.add(playlist_options);
        global_pane.add(thumbnail_label);
        global_pane.add(thumbnail_options);

        frame.add(global_pane,BorderLayout.WEST);
    }

    public void display()
    {
        this.frame.setVisible(true);
    }
}

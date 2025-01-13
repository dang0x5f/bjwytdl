package org.dang0x5f;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;

public class Gfx
{
    private JFrame      frame;

    private JMenuBar    menu_bar;
    private JMenu       file_menu;
    private JMenu       edit_menu;
    private JMenu       view_menu;
    private JMenu       about_menu;

    private JTabbedPane tab_pane;
    private JPanel      audio_tab;
    private JPanel      video_tab;

    public Gfx()
    {
        createFrame();
        createMenuBar();
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

        frame.add(tab_pane);
    }

    public void display()
    {
        this.frame.setVisible(true);
    }
}

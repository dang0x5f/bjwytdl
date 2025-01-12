package org.dang0x5f;

public class Main
{
    public static App application;

    public static void main(String[] args)
    {
        application = App.initialize();

        application.run();
    }
}

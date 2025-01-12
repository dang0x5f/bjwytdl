package org.dang0x5f;

public class App
{
    private Gfx win;
    
    private App()
    {
        win = new Gfx();
    }

    public static App initialize()
    {
        App app = new App();

        return app;
    }

    public void run()
    {
        this.win.display();
    }

}

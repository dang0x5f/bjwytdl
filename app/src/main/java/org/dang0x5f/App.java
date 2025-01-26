package org.dang0x5f;

public class App
{
    private String os_name;
    private String user_name;
    private String user_home;
    private String pwd;

    private Gfx win;
    
    private App()
    {
    	os_name   = System.getProperty("os.name");
    	user_name = System.getProperty("user.name");
    	user_home = System.getProperty("user.home");
    	pwd = System.getProperty("user.dir");

        win = new Gfx(this);
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
    
    public String getOS()
    {
    	return os_name;
    }
    
    public String getUsername()
    {
    	return user_name;
    }
    
    public String getHome()
    {
    	return user_home;
    }

    public String getPWD()
    {
    	return pwd;
    }
}

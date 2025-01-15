package org.dang0x5f;

import java.io.InputStream;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;

public class DownloadActionThread implements ActionListener
{
    @Override
    public void actionPerformed(ActionEvent e)
    {
        SwingWorker worker_thread = new SwingWorker<Integer,Void>()
        {
            @Override
            public Integer doInBackground()
            {
                try{
                    Process proc = Runtime.getRuntime().exec("ls");

                    InputStream i = proc.getInputStream();

                    int c;
                    while((c=i.read()) != -1){
                        System.out.print((char)c);
                    }

                    proc.waitFor();
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }

                return(0);
            }
        };
        
        worker_thread.execute();
    }
}

package org.dang0x5f;

import java.io.InputStream;
import java.lang.StringBuilder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;

public class DownloadActionThread implements ActionListener
{
    private Gfx gfx;

    private String bin                = "yt-dlp";
    private String newline_option     = " --newline";
    private String progress_option    = " --progress-template ";
    private String progress_parm      = ">%(progress._percent_str)s%(info.title)s";
    private String audio_option       = " -x --audio-format ";
    private String quality_option     = " --audio-quality ";
    private String playlist_option[]  = { " --yes-playlist"    , " --no-playlist" };
    private String thumbnail_option[] = { " --embed-thumbnail" , " "              };
    private String output_option      = " -o ";
    private String output_parm        = "%(title)s.%(ext)s ";

    public DownloadActionThread(Gfx _gfx)
    {
        gfx = _gfx;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        SwingWorker worker_thread = new SwingWorker<Integer,Void>()
        {
            private int status;
            private int rowcount;

            @Override
            public Integer doInBackground()
            {
                status = 0;
                if(gfx.getURL().isEmpty()){
                    System.out.println("empty url");
                    status = -1; 
                    return(status);
                }

                StringBuilder command = new StringBuilder(bin);
                
                command.append(playlist_option [ gfx.getPlaylistIndex()  ]);
                command.append(thumbnail_option[ gfx.getThumbnailIndex() ]);
                command.append(newline_option);
                command.append(progress_option);
                command.append(progress_parm);
                command.append(audio_option);
                command.append(gfx.getFormat());
                command.append(output_option);
                command.append(gfx.getPath());
                command.append(output_parm);
                command.append(gfx.getURL()); 
                

                try{
                    Process proc = Runtime.getRuntime().exec(command.toString());

                    gfx.setURL("");
                    InputStream i = proc.getInputStream();

                    rowcount = 0;
                    char line[] = new char[256];
                    int c, x = 0, round = 0;
                    while((c=i.read()) != -1){
                        // System.out.print((char)c);
                        if((char)c == '>'){
                            while((char)(c=i.read()) != '\n')
                                line[x++] = (char)c;
                            
                            if(round < 1){
                                Object[] row = { 
                                    new String(line).substring(6)
                                   ,gfx.getFormat() ,0 ,0 };
                                gfx.addNewRow(row);                                
                                rowcount = gfx.getNumOfRows();
                                round++;
                            } else {
                                gfx.setProgressBarValue(
                                        (int)Float.parseFloat(new String(line).substring(0,5))
                                       ,rowcount-1,2);
                            }
                        } else if((char)c == 'D') {
                            round = 0;
                            gfx.setStatusValue(1,rowcount-1,3);
                            while((char)(c=i.read()) != '\n')
                                line[x++] = (char)c;
                        } else {
                            while((char)(c=i.read()) != '\n')
                                line[x++] = (char)c;
                        }

                        for(int z = 0; z < line.length; z++)
                            line[z] = '\0';

                        x = 0;
                    }

                    System.out.println(command);
                    proc.waitFor();
                }
                catch(Exception ex){
                    // ex.printStackTrace();
                    status = -1;
                }

                return(status);
            }

            @Override
            public void done(){
                if(status == 0){
                    gfx.setProgressBarValue(100,rowcount-1,2);
                    gfx.setStatusValue(1,rowcount-1,3);
                }
            }

        };
        
        worker_thread.execute();
    }
}

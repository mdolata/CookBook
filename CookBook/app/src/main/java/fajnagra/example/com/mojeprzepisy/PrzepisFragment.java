package fajnagra.example.com.mojeprzepisy;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import model.*;

/**
 * Created by kgb_putin on 2015-05-21.
 */

public class  PrzepisFragment extends Fragment {
    private Potrawa obiad;
    private ArrayList<Przepis> przepisy;
    private static CountDownTimer c;
    private TextView przepis,kroki_txt,lcd;
    private Button prev,next,pauza,stop,play;
    private int len,czas_v;
    private int i;
    public  boolean leci;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObiadActivity activity = (ObiadActivity)getActivity();
        obiad = activity.obiad;
        len=activity.przepisy.size();
        przepisy = activity.przepisy;
        final View rootView = inflater.inflate(R.layout.przepis, container, false);
        przepis = (TextView)rootView.findViewById(R.id.przepis);
        lcd = (TextView)rootView.findViewById(R.id.lcd);
        kroki_txt = (TextView)rootView.findViewById(R.id.kroki_txt);
        play = (Button)rootView.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if((!leci)&&(len>0)){
                    leci=true;
                    c = new CountDownTimer(1000*czas_v*60,1000){
                        public void onTick(long ms){
                            String min = (ms/60000<10)?"0"+ms/60000:ms/60000+"";
                            String sec = ((ms/1000)%60<10)?"0"+(ms/1000)%60:(ms/1000)%60+"";
                            lcd.setText(min+":"+sec);
                        }
                        public void onFinish(){
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
                            mBuilder.setContentTitle("Koniec!!");
                            mBuilder.setContentText(obiad.getNazwa());
                            mBuilder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
                            mBuilder.setSmallIcon(R.drawable.clock);

                            NotificationManager mNotificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(0, mBuilder.build());
                            lcd.setText("00:00");
                            leci=false;
                        }
                    }.start();
                }
            }
        });
        stop = (Button)rootView.findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(len>0) {
                    if (c != null)
                        c.cancel();
                    leci = false;
                    setLcd();
                }
            }
        });
        pauza = (Button)rootView.findViewById(R.id.pauza);
        pauza.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Not supportet yet", Toast.LENGTH_SHORT).show();

            }
        });
        prev = (Button)rootView.findViewById(R.id.prev);
        prev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (c != null) {
                    if(i>0)
                        c.cancel();
                    leci = false;
                }
                if(len>0)
                    if(i>0)setPrzepis(--i);
            }
        });
        next = (Button)rootView.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c != null) {
                    if (i + 1 < len)
                        c.cancel();
                    leci = false;
                }
                if (len > 0)
                    if (i + 1 < len) setPrzepis(++i);
            }
        });
        if(len>0)
            setPrzepis(i);
        return rootView;
    }
    public void setPrzepis(int i){
        if(!leci)
            czas_v = przepisy.get(i).getCzas();
        przepis.setText(przepisy.get(i).getText());
        setLcd();
        kroki_txt.setText("    Krok" + (i + 1) + "/" + len);
    }
    public void setLcd(){
        if(czas_v<10) {
            lcd.setText("0"+ czas_v + ":00");
        }
        else{
            lcd.setText(czas_v + ":00");
        }
    }
}
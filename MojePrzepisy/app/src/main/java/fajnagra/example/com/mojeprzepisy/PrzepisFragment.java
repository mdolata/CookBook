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

/**
 * Created by kgb_putin on 2015-05-21.
 */

public class  PrzepisFragment extends Fragment {
    private Intent mServiceIntent;
    private Obiad obiad;
    private static CountDownTimer c;
    private TextView przepis,kroki_txt,czas,lcd;
    private Button prev,next,pauza,stop,play;
    private int len,czas_v;
    private int i;
    public  boolean leci,pauza_v;

    public PrzepisFragment(Obiad obiad){
        this.obiad=obiad;
        len=obiad.getKroki();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View rootView = inflater.inflate(R.layout.przepis, container, false);
        przepis = (TextView)rootView.findViewById(R.id.przepis);
        czas = (TextView)rootView.findViewById(R.id.czas);
        lcd = (TextView)rootView.findViewById(R.id.lcd);
        kroki_txt = (TextView)rootView.findViewById(R.id.kroki_txt);
        play = (Button)rootView.findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!leci){
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
                if(c!=null)
                    c.cancel();
                leci=false;
                setLcd();
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
                    c.cancel();
                    leci = false;
                }
                if(i>0)setPrzepis(--i);
            }
        });
        next = (Button)rootView.findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c != null) {
                    c.cancel();
                    leci = false;
                }
                if (i + 1 < len) setPrzepis(++i);

            }
        });
        setPrzepis(i);
        return rootView;
    }
    public void setPrzepis(int i){
        if(!leci)
            czas_v = obiad.getPrzepis(i).getCzas();
        przepis.setText(obiad.getPrzepis(i).text + "");
        czas.setText(czas_v+" min");
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
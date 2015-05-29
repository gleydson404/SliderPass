package vsgdev.com.br.sliderpass;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Activity responsavel por exibir e tratar os comandos do passador de slides.
 *
 * @author Franklin Soares
 */
public class SliderPassActivity extends Activity implements View.OnTouchListener {
    Button btnLeft;
    Button btnRight;
    Button btnF5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slider_layout);

        btnLeft = (Button) findViewById(R.id.btn_left);
        btnLeft.setOnTouchListener(this);

        btnRight = (Button) findViewById(R.id.btn_right);
        btnRight.setOnTouchListener(this);

        btnF5 = (Button) findViewById(R.id.btn_f5);
        btnF5.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (view.equals(btnF5)){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                UDPSenderThread.getInstance().setBuffer("4".getBytes());
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                UDPSenderThread.getInstance().setBuffer("5".getBytes());
            }
        }

        if (view.equals(btnLeft)){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                UDPSenderThread.getInstance().setBuffer("0".getBytes());
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                UDPSenderThread.getInstance().setBuffer("1".getBytes());
            }
        }

        if (view.equals(btnRight)){
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                UDPSenderThread.getInstance().setBuffer("2".getBytes());
            }
            if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                UDPSenderThread.getInstance().setBuffer("3".getBytes());
            }
        }
        return false;
    }
}

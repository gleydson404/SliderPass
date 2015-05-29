package vsgdev.com.br.sliderpass;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.InetAddress;

/**
 * Atividade Principal (MainActivity) exibida quando o app é iniciado. Trata da identificacao do computador que contém a apresentacao/slides.
 *
 * @author Gleydson Silva
 */
public class MainActivity extends Activity implements View.OnClickListener{
    public static final String PREFS_NAME = "IPS";
    Button btnOkServer;
    UDPSenderThread udpSenderThread;
    EditText txtServerIP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        txtServerIP = (EditText) findViewById(R.id.et_server_ip);
        btnOkServer = (Button) findViewById(R.id.btn_ok_server);
        btnOkServer.setOnClickListener(this);

        SharedPreferences ips = getSharedPreferences(PREFS_NAME, 0);
        txtServerIP.setText(ips.getString("IPAnterior", ""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (udpSenderThread != null){
            udpSenderThread.setBuffer("5".getBytes());
            udpSenderThread.running = false;
        }

    }

    @Override
    public void onClick(View view) {
        if (btnOkServer.isPressed()){
            String serverIp = (txtServerIP).getText().toString();

            if(Patterns.IP_ADDRESS.matcher(serverIp).matches()){
                saveIPtoSharedPreferences();
                try {
                    if (udpSenderThread == null){
                        InetAddress address = InetAddress.getByName(serverIp);
                        udpSenderThread = UDPSenderThread.getInstance();
                        udpSenderThread.setServer(address);
                        udpSenderThread.start();
                    }
                    startActivity(new Intent(this, SliderPassActivity.class));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(getApplicationContext(),"IP digitado não é válido",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveIPtoSharedPreferences() {
        SharedPreferences ips = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = ips.edit();
        editor.putString("IPAnterior", txtServerIP.getText().toString());
        editor.commit();
    }
}

package com.example.apptccautomacao;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    boolean isSwitchOn;
    Button btn_ligar, btn_desligar;
    Button btn_conectar_ip;
    EditText editip, editporta;
    String ip = "";
    String porta = "";
    TextView txterro;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declaraObjetos();

        btn_conectar_ip.setBackgroundColor(Color.parseColor("#a4c639"));


        btn_ligar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                btn_ligar.setTextColor(Color.parseColor("white"));
                btn_ligar.setBackgroundColor(Color.parseColor("#36BD31"));

                if (ip.equals("")) {
                    Toast.makeText(getApplicationContext(), "Informe o ip", Toast.LENGTH_LONG).show();
                } else {
                    ligarLed();
                }
            }
        });

        btn_desligar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                btn_desligar.setTextColor(Color.parseColor("white"));
                btn_desligar.setBackgroundColor(Color.parseColor("#36BD31"));

                if (ip.equals("")) {
                    Toast.makeText(getApplicationContext(), "Informe o ip", Toast.LENGTH_LONG).show();
                } else {
                    desligarLed();
                }
            }
        });

        btn_conectar_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ip = editip.getText().toString();
                porta = editporta.getText().toString();
                if (ip.equals("")) {
                    Toast.makeText(getApplicationContext(), "Informe o ip", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "ip adicionado com sucesso", Toast.LENGTH_LONG).show();
                    testarConexao();
                }
            }
        });
    }

    public void declaraObjetos() {
        btn_ligar = (Button) findViewById(R.id.button1);
        btn_desligar = (Button) findViewById(R.id.button2);
        btn_conectar_ip = (Button) findViewById(R.id.btn_conectar_ip);
        editip = (EditText) findViewById(R.id.editip);
        editporta = (EditText) findViewById(R.id.editporta);
        txterro = (TextView) findViewById(R.id.txterro);
        txterro.setText("se der erro vai aparecer aqui");
    }

    public void testarConexao() {
        //String url = "https://"+ip;   ESSA URL FUNCIONOU A CONEXÃO
        String url = "https://"+ip+":"+porta;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "conexão realizada com sucesso", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro na conexao com microcontrolador"+ error.getMessage(), Toast.LENGTH_LONG).show();
                        txterro.setText(error.getMessage());
                    }
                }){
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    public void ligarLed() {
        //String url = "https://"+ip+"/post";
        String url = "https://"+ip+":"+porta;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "conexão realizada com sucesso", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro na conexao com microcontrolador" + error.getMessage(), Toast.LENGTH_LONG).show();
                        txterro.setText(error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params= new HashMap<String, String>();
                String value = String.valueOf("1");
                params.put("data",value);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    public void desligarLed() {
        //String url = "http://"+ip+"/post";
        String url = "https://"+ip+":"+porta;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "conexão realizada com sucesso", Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "erro na conexao com microcontrolador" + error.getMessage(), Toast.LENGTH_LONG).show();
                        txterro.setText(error.getMessage());
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params= new HashMap<String, String>();
                String value = String.valueOf("0");
                params.put("data",value);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}
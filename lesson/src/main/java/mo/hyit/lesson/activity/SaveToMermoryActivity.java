package mo.hyit.lesson.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import mo.hyit.lesson.R;

public class SaveToMermoryActivity extends AppCompatActivity {

    private Button button_savetom,button_savetoup,button_savetoxml;
    private EditText editText_n,editText_p,editText_nn,editText_pp,editText_nnn,editText_ppp;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_to_mermory);

        initView();
        initData();
        setListener();
    }

    private void initData() {
        try {
            FileInputStream fis = openFileInput("info.txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(fis));
            String sbf = bf.readLine();
            String out[] = sbf.split("##");
            editText_n.setText(out[0]);
            editText_p.setText(out[1]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editText_nn.setText(sp.getString("user",null));
        editText_pp.setText(sp.getString("pwd",null));

        try {
            FileInputStream fis = new FileInputStream(new File(SaveToMermoryActivity.this.getCacheDir(),"info.xml"));

            XmlPullParser xml = Xml.newPullParser();

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setListener() {
        button_savetom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    FileOutputStream fos = openFileOutput("info.txt", Context.MODE_PRIVATE);
                    String tmp = editText_n.getText().toString().trim()+"##"+editText_p.getText().toString().trim();
                    fos.write(tmp.getBytes());
                    fos.flush();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        button_savetoup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user",editText_nn.getText().toString().trim());
                editor.putString("pwd",editText_pp.getText().toString().trim());
                editor.commit();
            }
        });
        button_savetoxml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(SaveToMermoryActivity.this.getCacheDir(),"info.xml");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    XmlSerializer xml = Xml.newSerializer();
                    xml.setOutput(fos,"utf-8");
                    xml.startDocument("utf-8",true);

                    xml.startTag(null,"info");

                    xml.startTag(null,"user");
                    xml.text(editText_nnn.getText().toString().trim());
                    xml.endTag(null,"user");

                    xml.startTag(null,"pwd");
                    xml.text(editText_ppp.getText().toString().trim());
                    xml.endTag(null,"pwd");

                    xml.endDocument();
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        sp = this.getSharedPreferences("infoi",Context.MODE_PRIVATE);

        button_savetom = findViewById(R.id.button_savetom);
        button_savetoup = findViewById(R.id.button_savetoup);
        button_savetoxml = findViewById(R.id.button_savetoxml);
        editText_ppp = findViewById(R.id.editText_ppp);
        editText_pp = findViewById(R.id.editText_pp);
        editText_p = findViewById(R.id.editText_p);
        editText_nnn = findViewById(R.id.editText_nnn);
        editText_nn = findViewById(R.id.editText_nn);
        editText_n = findViewById(R.id.editText_n);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}

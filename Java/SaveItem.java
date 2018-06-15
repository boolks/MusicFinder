package musicfinder.com.musicfinder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SaveItem extends AppCompatActivity{
    String gstring, ystring, estring;
    TextView gTextView, yTextView, eTextView;
    Intent it = getIntent();

    @Override
    protected void onCreate(Bundle savedInstantState) {
        super.onCreate(savedInstantState);
        setContentView(R.layout.saved_select);
        loadItem();
        gTextView = (TextView)findViewById(R.id.textView2);
        gTextView.setText("장르 : " + gstring);
        yTextView = (TextView)findViewById(R.id.textView3);
        yTextView.setText("연도 : " + ystring);
        eTextView = (TextView)findViewById(R.id.textView4);
        eTextView.setText("감정 : " + estring);
    }



    public void loadItem() {
        SharedPreferences settings = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor edit = settings.edit();
        edit.apply();
        gstring = settings.getString("Gstr", "No Input").toString();
        ystring = settings.getString("Ystr", "No Input").toString();
        estring = settings.getString("Estr", "No Input").toString();
    }


    public void onClick5(View view) {
        Intent intent = new Intent(SaveItem.this , PhpDownMain.class);
        SharedPreferences settings = getSharedPreferences("Data", MODE_PRIVATE);
        if(settings.getString("Gstr", "No Input").toString() == "No Input") {
            Toast.makeText(SaveItem.this, "입력받은 데이터 값이 없습니다", Toast.LENGTH_LONG).show();
        } else {
            intent.putExtra("Gstr", settings.getString("Gstr", "default").toString());
            intent.putExtra("Ystr", settings.getString("Ystr", "default").toString());
            intent.putExtra("Estr", settings.getString("Estr", "default").toString());
            startActivity(intent);
        }
    }
}

package musicfinder.com.musicfinder;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<CharSequence> adGspin;     // 스피너 레이아웃과 객체를 연결해주기 위한 어댑터변수
    ArrayAdapter<CharSequence> adYspin;
    ArrayAdapter<CharSequence> adEspin;
    private long lastTimeBackPressed;
    Spinner Gspin;  // 장르 스피너
    Spinner Yspin;  // 연도 스피너
    Spinner Espin;  // 감정 스피너
    SaveItem save = new SaveItem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {    // 여기서 처음 메인 화면이 실행됨
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spinnertest);   // 스피너화면을 띄워줌
        startActivity(new Intent(this,Splash.class));
        //스피너변수를 레이아웃과 연결해서 spinnertest 레이아웃에 보이도록 설정
        Gspin = (Spinner) findViewById(R.id.Gspinner);
        adGspin = ArrayAdapter.createFromResource(this, R.array.Genre, android.R.layout.simple_spinner_item);
        adGspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Gspin.setAdapter(adGspin);

        Yspin = (Spinner) findViewById(R.id.Yspinner);
        adYspin = ArrayAdapter.createFromResource(this, R.array.Year, android.R.layout.simple_spinner_item);
        adYspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Yspin.setAdapter(adYspin);

        Espin = (Spinner) findViewById(R.id.Espinner);
        adEspin = ArrayAdapter.createFromResource(this, R.array.Emotion, android.R.layout.simple_spinner_item);
        adEspin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Espin.setAdapter(adEspin);
    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);

        if(System.currentTimeMillis() - lastTimeBackPressed <1500){
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();
    }
    // 스피너상에서 노래 추천받기 버튼을 누를 경우 list를 보여주는 화면으로 넘어가는 메소드

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, PhpDownMain.class);
        intent.putExtra("Gstr", Gspin.getSelectedItem().toString());
        intent.putExtra("Ystr", Yspin.getSelectedItem().toString());
        intent.putExtra("Estr", Espin.getSelectedItem().toString());
        saveItem();
        startActivity(intent);
    }
    public void saveItem() {
        SharedPreferences settings = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Gstr", Gspin.getSelectedItem().toString());
        editor.putString("Ystr", Yspin.getSelectedItem().toString());
        editor.putString("Estr", Espin.getSelectedItem().toString());
        editor.commit();
    }
    public void restItem() {
        SharedPreferences settings = getSharedPreferences("Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.commit();
    }

    public void onClick2(View view){
        Intent intent = new Intent(MainActivity.this, SaveItem.class);
        startActivity(intent);
    }

    public void onClick4(View view) {
        restItem();
        Toast.makeText(MainActivity.this, "삭제 완료",  Toast.LENGTH_LONG).show();
    }
    public void onClickWeb(View view) {
        Intent intent = new Intent(MainActivity.this, webView.class);
        startActivity(intent);
    }

}
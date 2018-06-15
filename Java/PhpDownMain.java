package musicfinder.com.musicfinder;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class PhpDownMain extends AppCompatActivity{
    ListView list;
    phpDown task;
    ArrayList<ListItem> listItem = new ArrayList<ListItem>();   // 리스트를 담기위한 배열리스트

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // 메인 리스트 화면을 띄워줌
        task = new phpDown();
        list = (ListView)findViewById(R.id.list);
        task.execute("http://211.219.34.162/appdata.php");  // 인자로 php 주소를 넘겨주어 백그라운드 작업 수행
    }

    // 리스트뷰에 들어가는 항목들을 서버에서 가져와 처리하는 일
    private class phpDown extends AsyncTask<String, Integer, String> {
        Intent it = getIntent();
        String Gdata = "Genre=" + it.getExtras().getString("Gstr") + "&";   // php 문서 형식으로 스트링을 변경 (POST 방식으로 제대로 읽어들일 수 있도록)
        String Ydata = "Year=" + it.getExtras().getString("Ystr") + "&";
        String Edata = "Emotion=" + it.getExtras().getString("Estr");
        String Adata = Gdata + Ydata + Edata;   // 장르 연도 감정을 한번에 송신하기 위해 묶어줌
        @Override
        // execute에서 입력한 인자를 배열로 받음
        protected String doInBackground(String... urls) {
            StringBuilder jsonHtml = new StringBuilder();
            try {
                // 연결 url 설정
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // 서버와 통신을 하기위한 클래스 변수
                // 연결되었으면.
                if (conn != null) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);
                    // POST 방식으로 웹에 데이터를 보내줌
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Cache-Control", "no-cache");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setDoOutput(true);
                    // 문자를 바이트단위 스트림으로 변경해서 웹에 보내줌
                    OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                    // php 파일에 스피너로 선택한 값들을 전부 POST 방식으로 전송
                    os.write(Adata);
                    os.flush();
                    conn.connect();
                    // 연결되었음 코드가 리턴되면.
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        // Json 형식으로 변환된 php 문서를 안드로이드로 읽어들이는 과정
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        for (; ; ) {
                            // 웹상에 보여지는 텍스트를 라인단위로 읽어 저장.
                            String line = br.readLine();
                            if (line == null) break;
                            // 저장된 텍스트 라인을 jsonHtml에 붙여넣음
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return jsonHtml.toString();
        }

        // 백그라운드 실행(웹에서 글을 가져오는 일) 후 처리과정
        protected void onPostExecute(String str) {
            String id;
            String Singer;
            String Song;
            String Year;
            String Genre;
            String Emotion;
            String Album;
            String Track;
            String Image;
            String youtube;
            try {
                // JSon 문서를 JsonObject 객체로 받은 뒤 결과값 리스트를 배영 형식(JsonArray)으로 받아옴
                JSONObject root = new JSONObject(str);
                JSONArray marray = root.getJSONArray("results");

                // 받아온 JSon 문서의 값을 리스트에 저장
                for (int i = 0; i < marray.length(); i++) {
                    JSONObject mobject = marray.getJSONObject(i);
                    id = mobject.getString("id");
                    Singer = mobject.getString("Singer");
                    Song = mobject.getString("Song");
                    Year = mobject.getString("Year");
                    Genre = mobject.getString("Genre");
                    Emotion = mobject.getString("Emotion");
                    Album = mobject.getString("Album");
                    Track = mobject.getString("Track");
                    Image = mobject.getString("ImageUrl");
                    youtube = mobject.getString("Youtube");
                    listItem.add(new ListItem(id, Singer, Song, Year, Genre, Emotion, Album, Track, Image, youtube));
                }
                // ListView레이아웃에 받아온 배열값을 표시하기 위해 어댑터로 연결해줌
                ListAdapter adapter = new ListAdapter(getBaseContext(), R.layout.listitem, listItem);
                list.setAdapter(adapter);
                // 데이터를 클릭했을 때 유투브 링크Url을 유투브어플에 연결해주는 이벤트리스너
                // get(position)이 내가 클릭한 항목의 위치 getData는 그 항목의 9번째 데이터(유투브 Url)
                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(listItem.get(position).getData(9)));
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

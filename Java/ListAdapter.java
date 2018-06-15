package musicfinder.com.musicfinder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

// 리스트뷰에 웹에서 받아온 데이터들을 표시하도록 레이아웃과 객체를 연결해주는 어댑터 클래스
public class ListAdapter extends ArrayAdapter<ListItem> {
    private ArrayList<ListItem> itemList;   // ListItem의 값들을 배열로 저장함
    private Context context;
    private int rowResourceId;
    public ListAdapter(Context context, int textViewResourceId, ArrayList<ListItem> itemList){
        super(context, textViewResourceId, itemList);
        this.itemList = itemList;                   // listitem 저장
        this.context = context;
        this.rowResourceId = textViewResourceId;    // R.layout.listitem 저장
    }

    @NonNull
    @Override
    // ListView 레이아웃에 데이터를 표시해주는 메소드
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem viewHolder;

        // 최초 호출 시
        if(convertView == null){
            // XML 문서에 정의된 레이아웃(listItem.xml 부분)과 뷰의 속성(listitem.xml 내부의 속성들)을 읽어 실제 뷰 객체를 생성해내는 동작
            // setContentView랑 같다고 보면 됨
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(this.rowResourceId, parent, false);

            viewHolder = new ViewHolderItem();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            viewHolder.Singer = (TextView)convertView.findViewById(R.id.Singer);
            viewHolder.Song = (TextView)convertView.findViewById(R.id.Song);
            viewHolder.Genre = (TextView)convertView.findViewById(R.id.Genre);
            viewHolder.Album = (TextView)convertView.findViewById(R.id.Album);
            viewHolder.Track = (TextView)convertView.findViewById(R.id.Track);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolderItem)convertView.getTag();
        }
        ListItem item = itemList.get(position);     // 리스트의 상단에서부터 순서대로 position 번 째의 값을 가져옴 (처음은 맨 위니까 position = 0)
        viewHolder.Singer.setText("Singer: " + item.getData(1));    // 가수명의 데이터를 표시함(getData(1)는 listItem 의 mData(1)을 불러오는 것
        viewHolder.Song.setText("Song: " + item.getData(2));
        viewHolder.Genre.setText("Genre: " + item.getData(4));
        viewHolder.Album.setText("Album: " + item.getData(6));
        // Glide란 서버 이미지 폴더에 있는 이미지 주소를 받아 바로 그림으로 나타내주는 구글에서 만든 라이브러리 이미지 로더
        Glide.with(viewHolder.image.getContext()).load(item.getData(8)).into(viewHolder.image);
        return convertView;
    }
}
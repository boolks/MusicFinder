package musicfinder.com.musicfinder;

public class ListItem {
    private String[] mData;

    public ListItem(String[] data) {
        mData = data;
    }

    // JSon 문서에서 받아온 값을 각각 항목별로 저장해주는 클래스
    public ListItem(String id, String Singer, String Song, String Year, String Genre, String Emotion, String Album, String Track, String Image, String Uri) {
        mData = new String[10];
        mData[0] = id;
        mData[1] = Singer;
        mData[2] = Song;
        mData[3] = Year;
        mData[4] = Genre;
        mData[5] = Emotion;
        mData[6] = Album;
        mData[7] = Track;
        mData[8] = Image;
        mData[9] = Uri;     // 유투브 주소
    }

    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        return mData[index];
    }

    public void setData(String[] data){
        mData = data;
    }
}

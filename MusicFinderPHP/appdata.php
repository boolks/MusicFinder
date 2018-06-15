 <?php
    // 데이터베이스 접속 문자열. (db위치, 유저 이름, 비밀번호)
    $connect=mysql_connect( "localhost", "HWAN", "60132308") or  
        die( "SQL server에 연결할 수 없습니다.");
 
    
    mysql_query("SET NAMES UTF8");
   // 데이터베이스 선택
   mysql_select_db("musiclist",$connect); 
   // 세션 시작
   session_start();
   // 안드로이드에서 POST 방식으로 스피너에서 선택한 값의 문자열을 받음
   $Genre = $_POST['Genre'];
   $Year = $_POST['Year'];
   $Emotion = $_POST['Emotion'];
   // 쿼리문 생성
   $sql = "select * from musiclist WHERE Genre = '$Genre' AND Year = '$Year' AND Emotion = '$Emotion' ORDER BY RAND() limit 5";
 
   // 쿼리 실행 결과를 $result에 저장
   $result = mysql_query($sql, $connect);
   // 반환된 전체 레코드 수 저장.
   $total_record = mysql_num_rows($result);
 
   // JSONArray 형식으로 만들기 위해서...
   echo "{\"status\":\"OK\",\"num_results\":\"$total_record\",\"results\":[";
 
   // 반환된 각 레코드별로 JSONArray 형식으로 만들기.
   for ($i=0; $i < $total_record; $i++)                    
   {
      // 가져올 레코드로 위치(포인터) 이동  
      mysql_data_seek($result, $i);       
        
      $row = mysql_fetch_array($result);
   echo "{\"id\":\"$row[id]\",\"ImageUrl\":\"$row[ImageUrl]\", \"Singer\":\"$row[Singer]\",\"Song\":\"$row[Song]\",\"Year\":\"$row[Year]\",\"Genre\":\"$row[Genre]\",\"Emotion\":\"$row[Emotion]\",\"Album\":\"$row[Album]\",\"Track\":\"$row[Track]\",\"Youtube\":\"$row[Youtube]\"}";
 
   // 마지막 레코드 이전엔 ,를 붙인다. 그래야 데이터 구분이 되니깐.  
   if($i<$total_record-1){
      echo ",";
   }
   
   }
   
   // JSONArray의 마지막 닫기
   echo "]}";
?>
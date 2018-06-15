<?php
    session_start();
?>
<HTML>
<HEAD>
	<TITLE>Musiclist_Main</TITLE>
</HEAD>
<BODY>
<?php
    require_once('MYDB.php');
    $pdo = db_connect();
    if(isset($_GET['action']) && $_GET['action'] == 'delete' && $_GET['id'] > 0) {
        try{
            $pdo->beginTransaction();
            $id = $_GET['id'];
            $sql = "DELETE FROM musiclist WHERE id = :id";
            $stmh = $pdo->prepare($sql);
            $stmh->bindValue(':id',$id,PDO::PARAM_INT);
            $stmh->execute();
            $pdo->commit();
            print "데이터를".$stmh->rowCount()."건 삭제하였습니다.<BR>";
        } catch (PDOException $Exceptionx) {
            $pdo->rollBack();
            print "오류:".$Exception->getMessage();
        }
    }
?>
<HR size="1" noshade>
Musiclist
<HR size="1" noshade>
[ <a href="form.html">새로운 데이터 등록</a>]
<BR>
<FORM name="form1" method="post" action="list.php">
    검색: <INPUT type="text" name="search_key"><INPUT type="submit" value="검색">
</FORM>
<?php
    require_once("MYDB.php");
    $pdo = db_connect();
    // 삭제 처리
    if(isset($_GET['acion']) && $_GET['action'] = 'delete' && $_GET['id'] > 0) {
        try{
            $pdo->beginTranslation();
            $id = $_GET['id'];
            $sql = "DELETE FROM musiclist WHERE id = :id";
            $stmh = $pdo->prepare($sql);
            $stmh->bindValue(':id',$id,PDO::PARAM_INT);
            $stmh->execute();
            $stmh->commit();
            print "데이터를".$stmh->rowCount()."건 삭제하였습니다.<br>";
        } catch (PDOException $Exception) {
            $pdo->rollback();
            print "오류:".$Exception->getMessage();
        }
    }
    // 입력 처리
    if(isset($_POST['action']) && $_POST['action'] == 'insert'){
        try{
            $pdo->beginTransaction();
            $sql = "INSERT INTO musiclist (ImageUrl, Singer, Song, Year, Genre, Emotion, Album, Track, Youtube) VALUES (:ImageUrl, :Singer, :Song, :Year, :Genre, :Emotion, :Album, :Track, :Youtube)";
            $stmh = $pdo->prepare($sql);
            $stmh->bindValue(':ImageUrl',$_POST['ImageUrl'],PDO::PARAM_STR);
            $stmh->bindValue(':Singer',$_POST['Singer'],PDO::PARAM_STR);
            $stmh->bindValue(':Song',$_POST['Song'],PDO::PARAM_STR);
            $stmh->bindValue(':Year',$_POST['Year'],PDO::PARAM_INT);
            $stmh->bindValue(':Genre',$_POST['Genre'],PDO::PARAM_STR);
            $stmh->bindValue(':Emotion',$_POST['Emotion'],PDO::PARAM_STR);
            $stmh->bindValue(':Album',$_POST['Album'],PDO::PARAM_STR);
            $stmh->bindValue(':Track',$_POST['Track'],PDO::PARAM_INT);
            $stmh->bindValue(':Youtube',$_POST['Youtube'],PDO::PARAM_STR);
            $stmh->execute();
            $pdo->commit();
            print "데이터를".$stmh->rowCount()."건 입력하였습니다.<BR>";
        } catch (PDOException $Exception) {
            $pdo->rollBack();
            print "오류:".$Exception->getMessage();
        }
    }
    // 수정 처리
    if(isset($_POST['action']) && $_POST['action'] == 'update'){
        // 세션 변수에 따라 id를 받습니다.
        $id = $_SESSION['id'];
        try{
          $pdo->beginTransaction();
          $sql = "UPDATE musiclist SET ImageUrl = :ImageUrl, Singer = :Singer, Song = :Song, Year = :Year, Genre = :Genre, Emotion = :Emotion, Album = :Album, Track = :Track, Youtube = :Youtube WHERE id = :id";
          $stmh = $pdo->prepare($sql);
          $stmh->bindValue(':ImageUrl',$_POST['ImageUrl'],PDO::PARAM_STR);
          $stmh->bindValue(':Singer',$_POST['Singer'],PDO::PARAM_STR);
          $stmh->bindValue(':Song',$_POST['Song'],PDO::PARAM_STR);
          $stmh->bindValue(':Year',$_POST['Year'],PDO::PARAM_INT);
          $stmh->bindValue(':Genre',$_POST['Genre'],PDO::PARAM_STR);
          $stmh->bindValue(':Emotion',$_POST['Emotion'],PDO::PARAM_STR);
          $stmh->bindValue(':Album',$_POST['Album'],PDO::PARAM_STR);
          $stmh->bindValue(':Track',$_POST['Track'],PDO::PARAM_INT);
          $stmh->bindValue(':Youtube',$_POST['Youtube'],PDO::PARAM_STR);
          $stmh->bindValue(':id',$id,PDO::PARAM_INT);
          $stmh->execute();
          $pdo->commit();
          print "데이터를".$stmh->rowCount()."건 수정하였습니다.<BR>";
        } catch (PDOException $Exception) {
            $pdo->rollBack();
            print "오류:".$Exception->getMessage();
        }
        // 사용한 세션 변수를 삭제합니다.
        unset($_SESSION['id']);
    }
    // 검색 및 현재의 모든 데이터를 표시합니다.
    try{
        if(isset($_POST['search_key']) && $_POST['search_key'] != ""){
            $search_key = '%'.$_POST['search_key'].'%';
            $sql = "SELECT * FROM musiclist WHERE Singer LIKE :Singer OR Song LIKE :Song OR Year LIKE :Year OR Genre LIKE :Genre OR Emotion LIKE :Emotion";
            $stmh = $pdo->prepare($sql);
            $stmh->bindValue(':Singer',$search_key,PDO::PARAM_STR);
            $stmh->bindValue(':Song',$search_key,PDO::PARAM_STR);
            $stmh->bindValue(':Year',$search_key,PDO::PARAM_INT);
            $stmh->bindValue(':Genre',$search_key,PDO::PARAM_STR);
            $stmh->bindValue(':Emotion',$search_key,PDO::PARAM_STR);
            $stmh->execute();
        }else{
            $sql = "SELECT * FROM musiclist";
            $stmh = $pdo->query($sql);
        }
        $count = $stmh->rowCount();
        print "검색 결과는".$count."건입니다.<BR>";
    } catch (PDOException $Exception) {
        print "오류:".$Exception->getMessage();
    }
    if($count < 1){
        print "검색 결과가 없습니다.<BR>";
    }else{
        ?>
    <TABLE width="100" border="1" cellspacing="1" cellpadding="5">
        <TBODY>
            <TR>
                <TH>번호</TH>
                <TH>앨범커버</TH>
                <TH>가수</TH>
                <TH>노래</TH>
                <TH>발매연도</TH>
                <TH>장르</TH>
                <TH>감정</TH>
                <TH>앨범</TH>
                <TH>트랙번호</TH>
                <TH>유투브</TH>
                <TH>&nbsp</TH>
                <TH>&nbsp</TH>
            </TR>
            <?php
                while($row = $stmh->fetch(PDO::FETCH_ASSOC)){
                    ?>
                    <TR>
                        <TD align="center"><?=htmlspecialchars($row['id'])?></TD>
                        <TD width="10%"><?=htmlspecialchars($row['ImageUrl'])?></TD>
                        <TD width="20%"><?=htmlspecialchars($row['Singer'])?></TD>
                        <TD width="25%"><?=htmlspecialchars($row['Song'])?></TD>
                        <TD align="center" width="5%"><?=htmlspecialchars($row['Year'])?></TD>
                        <TD width="25%"><?=htmlspecialchars($row['Genre'])?></TD>
                        <TD><?=htmlspecialchars($row['Emotion'])?></TD>
                        <TD width="20%"><?=htmlspecialchars($row['Album'])?></TD>
                        <TD align="center"><?=htmlspecialchars($row['Track'])?></TD>
                        <TD width="15%"><?=htmlspecialchars($row['Youtube'])?></TD>
                        <TD align="center"><a href=updateform.php?id=<?=htmlspecialchars($row['id'])?>>수정</a></TD>
                        <TD align="center"><a href=list.php?action=delete&id=<?=htmlspecialchars($row['id'])?>>삭제</a></TD>
                    </TR>
                    <?php
                }
            ?>
        </TBODY>
    </TABLE>
    <?php
    }
?>
<a href="list.php">메인화면으로 돌아가기</a>
</BODY>
</HTML>
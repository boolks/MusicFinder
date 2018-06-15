<?php
    session_start();
?>
<HTML>
<HEAD>
    <TITLE>Musiclist_Fix</TITLE>
</HEAD>
<BODY>
<HR size="1" noshade>
수정화면
<HR size="1" noshade>
[<a href="list.php">돌아가기</a>]
<BR>
<?php
    require_once("MYDB.php");
    $pdo = db_connect();
    // 여기를 변경하면 수정 대상이 바뀝니다.
    if(isset($_GET['id']) && $_GET['id'] > 0) {
        $id = $_GET['id'];
        $_SESSION['id'] = $id;
    }else{
        exit('잘못된 파라미터입니다.');
    }
    //$id = 1;
    //$_SESSION['id'] = $id;
    try{
        $sql = "SELECT * FROM musiclist WHERE id = :id ";
        $stmh = $pdo->prepare($sql);
        $stmh->bindValue(':id',$id,PDO::PARAM_INT);
        $stmh->execute();
        $count = $stmh->rowCount();
    } catch (PDOException $Exception) {
        print "오류:".$Exception->getMessage();
    }
    if($count < 1){
        print "수정 데이터가 없습니다.<BR>";
    }else{
        $row = $stmh->fetch(PDO::FETCH_ASSOC);
        ?>
        <FORM name="form1" method="post" action="list.php">
            번호:<?=htmlspecialchars($row['id'])?><BR>
            앨범Url:<INPUT type="text" name="ImageUrl" value="<?=htmlspecialchars($row['ImageUrl'])?>"><BR>
            가수:<INPUT type="text" name="Singer" value="<?=htmlspecialchars($row['Singer'])?>"><BR>
            노래:<INPUT type="text" name="Song" value="<?=htmlspecialchars($row['Song'])?>"><BR>
            발매연도:<INPUT type="text" name="Year" value="<?=htmlspecialchars($row['Year'])?>"><BR>
            장르:<INPUT type="text" name="Genre" value="<?=htmlspecialchars($row['Genre'])?>"><BR>
            감정:<INPUT type="text" name="Emotion" value="<?=htmlspecialchars($row['Emotion'])?>"><BR>
            앨범:<INPUT type="text" name="Album" value="<?=htmlspecialchars($row['Album'])?>"><BR>
            트랙번호:<INPUT type="text" name="Track" value="<?=htmlspecialchars($row['Track'])?>"><BR>
            유투브:<INPUT type="text" name="Youtube" value="<?=htmlspecialchars($row['Youtube'])?>"><BR>
            <INPUT type="hidden" name="action" value="update">
            <INPUT type="submit" value="수정">
        </FORM>
        <?php
    }
?>
</BODY>
</HTML>


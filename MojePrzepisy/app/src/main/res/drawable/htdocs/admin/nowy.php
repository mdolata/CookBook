<?php
 include("config.php");
 ?>

<!DOCTYPE html>
<html dir="ltr" lang="en-US"><head><!-- Created by Artisteer v4.1.0.59861 -->
    <meta charset="utf-8">
    <title></title>
    <meta name="viewport" content="initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no, width = device-width">

    <link rel="stylesheet" href="style.css" media="screen">
    <link rel="stylesheet" href="style.responsive.css" media="all">
	<script src="js/tinymce.min.js"></script>
<script>
        tinymce.init({selector:'textarea'});
</script>

    <script src="jquery.js"></script>
    <script src="script.js"></script>
    <script src="script.responsive.js"></script>


</head>
<body>
<div id="art-main">
<header class="art-header">


    <div class="art-shapes">

            </div>
<h2 class="art-slogan" data-left="2.78%">EClub-AdminPanel</h2>




<nav class="art-nav">
    <div class="art-nav-inner">
    <ul class="art-hmenu"><li><a href="home.html" class="">Home</a></li><li><a href="nowy.html" class="active">Nowy</a></li><li><a href="archiwum.html">Archiwum</a></li><li><a href="wyloguj.html">Wyloguj</a></li></ul> 
        </div>
    </nav>

                    
</header>
<div class="art-sheet clearfix">
            <div class="art-layout-wrapper">
                <div class="art-content-layout">
                    <div class="art-content-layout-row">
                        <div class="art-layout-cell art-content"><article class="art-post art-article">
                                <h2 class="art-postheader">Dodaj wpis</h2>
								<form enctype="multipart/form-data" action="nowy.php?akcja=wykonaj" method="POST">
								<input type="text" name="tytul">
								<br></br>
								<textarea name="tresc" rows="20" cols="50"></textarea>
								<br></br>
								<input name="plik" type="file" />
								<input type="submit" value="WyĹlij plik" />
								</form>
                                                
                                
                

</article></div>
                    </div>
                </div>
            </div>
    </div>


</div>
<?php
$akcja = $_GET['akcja'];
if ($akcja == wykonaj) {
$file= $_FILES['plik'];
$plik_tmp = $_FILES['plik']['tmp_name'];
$plik_nazwa = $_FILES['plik']['name'];
$plik_rozmiar = $_FILES['plik']['size'];
$nazwa = $_POST['tytul'];
$tresc = $_POST['tresc'];


if(is_uploaded_file($plik_tmp)) {
     move_uploaded_file($plik_tmp, "test/$plik_nazwa");
    echo "Plik: <strong>$plik_nazwa</strong> o rozmiarze 
    <strong>$plik_rozmiar bajtow</strong> zostal przeslany na serwer!"; 
   mysql_query("INSERT INTO `news` (nazwa_zdj,nazwa,data,autor,tresc) VALUES('$plik_nazwa','$nazwa',now(),'kornad930','$tresc')") or die("Nie mogłem Cie zarejestrować!"); 
}
}
?> 

</body></html>

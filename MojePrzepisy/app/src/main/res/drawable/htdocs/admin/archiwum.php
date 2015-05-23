<?php
 include("config.php");


$akcja = $_GET['akcja'];
if ($akcja == wykonaj) {
	$index = $_GET['index'];
	$temp = 10*$index;
}
else{
 $index =0;
 $temp=0;
}

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
                                <h2 class="art-postheader">Wpisy</h2>
								<?php
								$wynik = mysql_query("SELECT * FROM news  order by `data` desc limit $temp,18446744073709551615")
									or die('Błąd zapytania');
								if(mysql_num_rows($wynik) > 0) {
									while($r = mysql_fetch_assoc($wynik)) {
										echo "<tr>";
										echo "</tr>";
								?>
									<br></br>
		           					<h2><span style="color: #808080;"><?php echo"".$r['nazwa']."";?></span></h2>
									<div class="art-postcontent art-postcontent-1 clearfix">
									<span style="color: #808080;"><?php echo"".substr($r['tresc'],0,200)."";?>...</span>
									<p><br></p>
                </div>
                 
									<hr></hr>
									<?php 	}} ?>
						<form enctype="multipart/form-data" action="archiwum.php?akcja=wykonaj&index=<?php echo"".($index+1)."";?>" method="POST">
								<br></br>
								<input type="submit" value="dalej" />
								</form>
                                         
                                                
                                
                

</article></div>
                    </div>
                </div>
            </div>
    </div>


</div>


</body></html>

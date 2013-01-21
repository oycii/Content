<html>
	<head>
		<title>oycii multimedia</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="category">
			<div class="category">
				<g:link controller="game" action="list">Java Игры</g:link>
			</div>
			<div class="category">
				<g:link controller="song" action="list">Мелодии</g:link>
			</div>
			<div class="category">
				<g:link controller="image" action="list">Изображения</g:link>
			</div>
		</div>	
		<div class="main">
			<p align="center">
				<g:link controller="upload" action="update" params="[type: 'games']">Загрузить игры</g:link><br/>
				<g:link controller="upload" action="update" params="[type: 'songs']">Загрузить музыку</g:link><br/>
				<g:link controller="upload" action="update" params="[type: 'images']">Загрузить изображения</g:link><br/>
			</p>
		</div>	
		
	</body>
</html>
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
			<g:form action="uploadImages">
				Путь к файлу:
				<g:textField name="path" value="${path}" class="path"/><br/>
				Путь к описанию:
				<g:textField name="pathDTD" value="${pathDTD}" class="path"/><br/>
				Тип:
				<g:textField name="type" value="${type}" class="path"/><br/>
				<g:actionSubmit value="Загрузить" action="uploadImages" />
			</g:form>
		</div>	
		
	</body>
</html>
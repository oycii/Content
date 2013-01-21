<html>
	<head>
		<title>oycii mobile - Изображения</title>
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
				[ Изображения ]
			</div>
		</div>

		<div class="contents">
		<table>
			<tr>
			<td width="25%">
				<g:if test="${genres?.size() > 0}">
					<p>Жанры:</p>
					<br/>
					<div class="genre">
						<g:link controller="image" action="list">Показать все</g:link>
					</div>
					<g:each var="genre" in="${genres}">
						<div class="genre">
							<g:link controller="image" action="list" params="[genreId:genre.id]">${genre.name}</g:link>
						</div>
					</g:each>
				</g:if>
			</td>
			<td width="75%">
			
			<p><oycii:pageCount count="${c}" itemcount="${images.size()}" max="${params.max}" offset="${params.offset}"></oycii:pageCount></p>
			<br/>			
			<g:if test="${images != null}">
				<div class="main">
					
					<g:each var="image" in="${images}">
						<g:link action="buy" params="[id: image.id]">						
							<oycii:imageScreenshot image="${image}"></oycii:imageScreenshot>
						</g:link>
					</g:each>
				</div>
				
			</g:if>
				<div class="paginateButtons">
						<g:paginate total="${params.total}" params="[genreId:params.genreId]"></g:paginate>
				</div>	
			</td>
			</tr>
		</table>
		</div>
	</body>
</html>
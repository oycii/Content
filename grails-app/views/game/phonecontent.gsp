<html>
	<head>
		<title>oycii mobile - Java Игры</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="category">
			<div class="category">
				[ Java Игры ]
			</div>
			<div class="category">
				<g:link controller="song" action="list">Мелодии</g:link>
			</div>
			<div class="category">
				<g:link controller="image" action="list">Изображения</g:link>
			</div>
		</div>
		<div class="category">/</div>
		<g:if test="${session?.phoneid != null}">
			<div class="category"><b><g:link controller="game" action="phonecontent" params="[phoneid:session?.phoneid]">Игры для твоего телефона ${phone.model}</g:link></b></div>
			<div class="category"><g:link controller="general" action="vendors" params="[category: 'game']">(изменить модель)</g:link></div>
		</g:if>
		<g:else>
			<div class="category"><b><g:link controller="general" action="vendors" params="[category: 'game']">Игры для твоего телефона</g:link></b></div>
		</g:else>
		
		<div class="contents">
		<table>
			<tr>
			<td width="25%">
				<g:if test="${genres?.size() > 0}">
					<p>Жанры:</p>
					<br/>
					<div class="genre">
						<g:link controller="game" action="list">Показать все</g:link>
					</div>
					<g:each var="genre" in="${genres}">
						<div class="genre">
							<g:link controller="game" action="list" params="[genreId:genre.id]">${genre.name}</g:link>
						</div>
					</g:each>
				</g:if>
			</td>
			<td width="75%">
			<p><oycii:pageCount count="${c}" itemcount="${games.size()}" max="${params.max}" offset="${params.offset}"></oycii:pageCount></p>
			<br/>
			<g:if test="${games != null}">
				<div class="main">
					<g:each var="game" in="${games}">
							<div class="portal">
								<p><b>${game.name}</b></p>
								<iframe src="./show/${game.id}">
									<p>Ваш браузер не поддерживает элемент IFrame.</p>
								</iframe>
								<p><g:link controller="game" action="buy" params="[id:game.id]">загрузить >></g:link></p>
						</div>
					</g:each>
				</div>

			</g:if>
			<div class="main">
					<div class="paginateButtons">
						<g:paginate total="${params.totalGames}" params="[genreId:params.genreId]"></g:paginate>
					</div>
			</div>				
			</td>
			</tr>
		</table>
		</div>
	</body>
</html>

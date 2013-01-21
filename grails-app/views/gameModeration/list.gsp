<html>
	<head>
		<title>oycii multimedia</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="category">
				<g:if test="${categores?.size() > 0}">
						<g:each var="category" in="${categores}">
							<div class="category">
								<g:link controller="category" action="show" id="${category.id}">${category.name}</g:link>
							</div>
						</g:each>
						<div class="category"><b>музыка</b></div>
						<div class="category"><b>видео</b></div>
						<div class="category"><b>заставки</b></div>
						
				</g:if>
		</div>
		<div class="category"><g:link controller="game" action="list">Сервис игр</g:link></div>
		<div class="category"><g:link controller="gameModeration" action="list">Все</g:link></div>
		<div class="category"><g:link controller="gameModeration" action="listYesModeration">+</g:link></div>
		<div class="category"><g:link controller="gameModeration" action="listNoModeration">-</g:link></div>
		<div class="category">Кол-во игр: ${count}</div>
		
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
							<b>
							<g:if test="${genre.status == 0}">
								<div id="${'message-genre' + genre.id}">${genre.status}</div>
							</g:if>
							<g:if test="${genre.status == 1}">
								<div id="${'message-genre' + genre.id}">+</div>
							</g:if>
							<g:if test="${genre.status == 2}">
								<div id="${'message-genre' + genre.id}">-</div>
							</g:if>
							</b>
							${genre.name}
							<g:remoteLink action="addGenre" id="${genre.id}" update="${'message-genre' + genre.id}">+</g:remoteLink>
							<g:remoteLink action="removeGenre" id="${genre.id}" update="${'message-genre' + genre.id}">-</g:remoteLink>
						</div>
						<br/>
					</g:each>
				</g:if>
			</td>
			<td width="75%">
			
			<g:if test="${games}">
				<div class="main">
					<g:each var="game" in="${games}">
							<div class="portal">
								<p><b>${game.name}</b></p>
								<iframe src="../game/show/${game.id}">
									<p>Ваш браузер не поддерживает элемент IFrame.</p>
								</iframe>
								<p>
									<b>
									<g:if test="${game.status == 0}">
										<div id="${'message' + game.id}">Текущий статус: ${game.status}</div>
									</g:if>
									<g:if test="${game.status == 1}">
										<div id="${'message' + game.id}">Текущий статус: +</div>
									</g:if>
									<g:if test="${game.status == 2}">
										<div id="${'message' + game.id}">Текущий статус: -</div>
									</g:if>
									
									<g:remoteLink action="add" id="${game.id}" update="${'message' + game.id}">+</g:remoteLink>
									<g:remoteLink action="remove" id="${game.id}" update="${'message' + game.id}">-</g:remoteLink>
									</b>			
								</p>
						</div>
					</g:each>
				</div>

			</g:if>
			</td>
			</tr>
			<tr>
			<td/>
			<td>
				<g:if test="${games}">
					<div class="paginateButtons">
						<g:paginate controller="gameModeration" params="[q: count]" total="${count}"></g:paginate>
					</div>		
				</g:if>	
			</td>
			</tr>
		</table>
		</div>	
		
	</body>
</html>
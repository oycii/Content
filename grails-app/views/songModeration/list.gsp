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
		<div class="category"><g:link controller="song" action="list">Сервис игр</g:link></div>
		<div class="category"><g:link controller="songModeration" action="list">Все</g:link></div>
		<div class="category"><g:link controller="songModeration" action="listYesModeration">+</g:link></div>
		<div class="category"><g:link controller="songModeration" action="listNoModeration">-</g:link></div>
		<div class="category">Кол-во мелодий: ${count}</div>
		
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
			
			<g:if test="${songs}">
				<div class="main">
					<g:each var="song" in="${songs}">
							<div class="portal">
								<p><b>${song.name}</b></p>
								<p>
									<b>
									<g:if test="${song.status == 0}">
										<div id="${'message' + song.id}">Текущий статус: ${song.status}</div>
									</g:if>
									<g:if test="${song.status == 1}">
										<div id="${'message' + song.id}">Текущий статус: +</div>
									</g:if>
									<g:if test="${song.status == 2}">
										<div id="${'message' + song.id}">Текущий статус: -</div>
									</g:if>
									
									<g:remoteLink action="add" id="${song.id}" update="${'message' + song.id}">+</g:remoteLink>
									<g:remoteLink action="remove" id="${song.id}" update="${'message' + song.id}">-</g:remoteLink>
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
				<g:if test="${songs}">
					<div class="paginateButtons">
						<g:paginate controller="songModeration" params="[q: count]" total="${count}"></g:paginate>
					</div>		
				</g:if>	
			</td>
			</tr>
		</table>
		</div>	
		
	</body>
</html>
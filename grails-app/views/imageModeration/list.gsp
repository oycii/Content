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
		<div class="category"><g:link controller="image" action="list">Сервис изображений</g:link></div>
		<div class="category"><g:link controller="imageModeration" action="list">Все</g:link></div>
		<div class="category"><g:link controller="imageModeration" action="listYesModeration">+</g:link></div>
		<div class="category"><g:link controller="imageModeration" action="listNoModeration">-</g:link></div>
		<div class="category">Кол-во: ${size}</div>
		
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
			
			<g:if test="${images}">
				<div class="main">
					<g:each var="image" in="${images}">
							<div class="portal">
								<g:link controller="image" action="buy" params="[id: image.id]">						
									<oycii:imageScreenshot image="${image}"></oycii:imageScreenshot>
								</g:link>
								<p>
									<b>
									<g:if test="${image.status == 0}">
										<div id="${'message' + image.id}">Текущий статус: ${image.status}</div>
									</g:if>
									<g:if test="${image.status == 1}">
										<div id="${'message' + image.id}">Текущий статус: +</div>
									</g:if>
									<g:if test="${image.status == 2}">
										<div id="${'message' + image.id}">Текущий статус: -</div>
									</g:if>
									
									<g:remoteLink action="add" id="${image.id}" update="${'message' + image.id}">+</g:remoteLink>
									<g:remoteLink action="remove" id="${image.id}" update="${'message' + image.id}">-</g:remoteLink>
									</b>			
								</p>
						</div>
					</g:each>
				</div>

			</g:if>
			<div class="main">
				<div class="paginateButtons">
					<g:paginate controller="imageModeration" params="[q: size]" total="${size}"></g:paginate>
				</div>	
			</div>
			
			</td>
			</tr>
		</table>
		</div>	
		
	</body>
</html>
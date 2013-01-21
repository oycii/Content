<html>
	<head>
		<title>oycii mobile - Java Игры</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="category">
						<div class="category"><b>игры</b></div>
						<div class="category"><b>музыка</b></div>
						<div class="category"><b>видео</b></div>
						<div class="category"><b>заставки</b></div>
						
		</div>
		<div class="contents">
		<table>
			<tr>
			<td width="25%">
				<g:if test="${genres?.size() > 0}">
					<p>Жанры:</p>
					<br/>
					<div class="genre">
						<g:link controller="$category" action="list">Показать все</g:link>
					</div>
					<g:each var="genre" in="${genres}">
						<div class="genre">
							<g:link controller="$category" action="list" params="[genreId:genre.id]">${genre.name}</g:link>
						</div>
					</g:each>
				</g:if>
			</td>
			<td width="75%">
			<g:if test="${phones != null}">
				<p>Выбери модель телефона:</p>
				<div class="main">
					<g:each var="phone" in="${phones}">
							<div class="portal">
								<p><g:link controller="$category" action="phonecontent" params="[phoneid:phone.id, category: params.category]">${phone.model}</g:link></p>
						</div>
					</g:each>
					<div class="portal">
							<p><g:link controller="$category" action="listWithoutPhone">Вне списка</g:link></p>
					</div>					
					
				</div>

			</g:if>
			</td>
			</tr>
		</table>
		</div>
	</body>
</html>
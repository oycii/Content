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
		<div class="category">/</div>
		<g:if test="${session?.phoneid != null}">
			<div class="category"><b><g:link controller="game" action="phonegames" params="[phoneid:session?.phoneid]">Игры для твоего телефона</g:link></b></div>
		</g:if>
		<g:else>
			<div class="category"><b><g:link controller="game" action="vendors">Игры для твоего телефона</g:link></b></div>
		</g:else>
		
		<div class="contents">
		<table>
			<tr>
			<td width="15%">
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
			<td width="85%">

			<g:if test="${searchResult?.results}">
				<div class="main">
					<g:each var="game" in="${searchResult.results}">
							<div class="portal">
								<p><b>${game.name}</b></p>
								<iframe src="../game/show/${game.id}">
									<p>Ваш браузер не поддерживает элемент IFrame.</p>
								</iframe>
								<p><g:link controller="game" action="buy" params="[id:game.id]">загрузить >></g:link></p>
						</div>
					</g:each>
				</div>

			</g:if>

			</td>
			</tr>
			<tr>
			<td></td>
			<td> 
 
				<g:if test="${searchResult?.results}">
					<div class="paginateButtons">
						<g:paginate controller="search" params="[q: params.q]" total="${searchResult.total}"></g:paginate>
					</div>		
				</g:if>	
			</td>
			</tr>
		</table>
		</div>	
		
	</body>
</html>
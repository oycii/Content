<html>
	<head>
		<title>oycii mobile - Мелодии</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="category">
			<div class="category">
				${state.name} (<g:link controller="state" action="list">выбрать страну</g:link>)
			</div>
			<div class="category">
				<g:link controller="game" action="list">Java Игры</g:link>
			</div>
			<div class="category">
				[ Мелодии ]
			</div>
			<div class="category">
				<g:link controller="image" action="list">Изображения</g:link>
			</div>		
		</div>
		<div class="category">/</div>
		<g:if test="${session?.phoneid != null}">
			<div class="category"><b><g:link controller="song" action="phonecontent" params="[phoneid:session?.phoneid]">Мелодии для твоего телефона ${phone.model}</g:link></b></div>
			<div class="category"><g:link controller="general" action="vendors" params="[category:'song']">(изменить модель)</g:link></div>
		</g:if>
		<g:else>
			<div class="category"><b><g:link controller="general" action="vendors" params="[category:'song']">Мелодии для твоего телефона</g:link></b></div>
		</g:else>

		<div class="contents">
		<table>
			<tr>
			<td width="25%">
				<g:if test="${genres?.size() > 0}">
					<p>Жанры:</p>
					<br/>
					<div class="genre">
						<g:link controller="song" action="list">Показать все</g:link>
					</div>
					<g:each var="genre" in="${genres}">
						<div class="genre">
							<g:link controller="song" action="list" params="[genreId:genre.id]">${genre.name}</g:link>
						</div>
					</g:each>
				</g:if>
			</td>
			<td width="75%">
			<p><oycii:pageCount count="${count}" itemcount="${songs.size()}" max="${params.max}" offset="${params.offset}"></oycii:pageCount></p>
			<br/>
			<g:if test="${songs != null}">
				<div class="main">
					<g:each var="song" in="${songs}">
							<div class="music">
								<p>
									<b>${song.name}</b>
									<br/>
									<g:if test="${song?.singer != null}">
										Исполнитель:<b><g:link controller="song" action="singer" params="[singerid: song.singer.id]">${song.singer.name}</g:link></b><br/>
									</g:if>
									<g:if test="${song.songPreviews?.size() > 0}">
									Превью-версия:								
									<g:each var="songPreview" in="${song.songPreviews}">
  										<a href="${songPreview?.url?.encodeAsHTML()}">${songPreview.url.substring(songPreview.url.lastIndexOf(".") + 1, songPreview.url.size())}</a> 
  									</g:each>
  									</g:if>
								</p>
								
								<p>
									загрузить:<br/>
									<g:each var="formatInstance" in="${song.formatInstances}">
										<g:link controller="song" action="buy" params="[id:song.id, formatInstanceId: formatInstance.id]">${formatInstance.mediaFormat.name} >></g:link>
									</g:each>
								</p>
						</div>
					</g:each>
				</div>
			</g:if>
			<div class="main">
				<div class="paginateButtons">
					<g:paginate total="${count}" params="[genreId:params.genreId, singerid:params.singerid]"></g:paginate>
				</div>
			</div>			
			</td>
			</tr>

		</table>
		</div>
	</body>
</html>

<html>
	<head>
		<title>oycii mobile - Java Игры</title>
		<meta name="layout" content="main">
	</head>
	<body>
		<div class="category">
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
			<g:if test="${song != null}">
				<div class="main">
					<table>
						<tr>
							<td width="20%">
								<p><b>${song.name}</b></p>
								<p>
								<g:if test="${song.songPreviews?.size() > 0}">
									Превью-версия:<br/>								
									<g:each var="songPreview" in="${songPreviews}">
  										<a href="${songPreview?.url?.encodeAsHTML()}">${songPreview.url.substring(songPreview.url.lastIndexOf(".") + 1, songPreview.url.size())}</a> 
  									</g:each>
  									</g:if> 
								</p>
							</td>
							<td width="80%">
								<p><b>Описание:</b></p>
								<g:if test="song.singer != null">
								<p><b>Исполнитель:</b> ${song?.singer?.name}</p>
								</g:if>
								<p>Мелодия предназначена для следующих моделей телефонов:</p>
								<p>
									<g:each var="p" in="${phones}">
										<g:if test="${p?.model == phone?.model}">
										    <b>${p.model};</b>
										</g:if>
										<g:else>
										    ${p.model};
										</g:else>
									</g:each>
								</p>
								<p><b>Гарантии:</b></p>
								<p>Данная мелодия предоставлена компанией playfon, экспертом в области мобильного контента. Тщательный отбор и тестирование игр соответствует высокой степени качества. Внимание! Ваша модель телефона должна быть в списке поддерживаемых!</p>
							</td>
							
						</tr>
					</table>
					<div>
						<p>Для того, чтобы загрузить мелодию, необходимо найдти в списке своего оператора связи и отправить sms на короткий номер с текстом заказа <b>${code}</b></p>
						<p>
							<table>	
								<tr>
									<th>Оператор</th>
									<th></th>
									<th>Короткий номер</th>
									<th>Стоимость</th>
								</tr>
									<g:each var="s" in="${services}">
										<tr>
											<td>${s.operator.name}</td>
											<td><img alt="${s.operator.name}" src="${s?.operator?.logoLink?.encodeAsHTML()}"></td>
											<td>${s.phone}</td>
											<td>${s.realPrice}</td>
										</tr>
									</g:each>
							</table>
						</p>
						<hr/>
						<p>По всем вопросам загрузки игр обращайтесь по электроному адресу <a href= "mailto:content@oycii.ru" >content@oycii.ru</a>
						</p>											
					</div>
				</div>
			</g:if>
			</td>
			</tr>
		</table>
		</div>
	</body>
</html>
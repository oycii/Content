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
			<div class="category"><g:link controller="game" action="phonegames" params="[phoneid:session?.phoneid]">Игры для твоего телефона ${phone.model}</g:link></b></div>
			<div class="category"><g:link controller="general" action="vendors" params="[category:'game']">(изменить модель)</g:link></div>
		</g:if>
		<g:else>
			<div class="category"><b><g:link controller="general" action="vendors" params="[category:'game']">Игры для твоего телефона</g:link></b></div>
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
			<g:if test="${game != null}">
				<div class="main">
					<table>
						<tr>
							<td>
								<p><b>${game.name}</b></p>
								<p><img alt="${game.name}" src="${screenshots[0]?.url?.encodeAsHTML()}"><br/> </p>
							</td>
							<td>
								<p><b>Описание:</b></p>
								<p>${game.description}</p>
								<p>Игра предназначена для следующих моделей телефонов:</p>
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
								<p>Данная игра предоставлена компанией playfon, экспертом в области мобильного контента. Тщательный отбор и тестирование игр соответствует высокой степени качества. Внимание! Ваша модель телефона должна быть в списке поддерживаемых!</p>
							</td>
							
						</tr>
					</table>
					<div>
						<p>Для того, чтобы загрузить игру, необходимо найдти в списке своего оператора связи и отправить sms на короткий номер с текстом заказа <b>${code}</b></p>
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
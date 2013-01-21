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
						<g:link action="list">Показать все</g:link>
					</div>
					<g:each var="genre" in="${genres}">
						<div class="genre">
							<g:link action="list" params="[genreId:genre.id]">${genre.name}</g:link>
						</div>
					</g:each>
				</g:if>
			</td>
			<td width="75%">
			<g:if test="${image != null}">
				<div class="main">
					<table>
						<tr>
							<td>
								<p><b>${image.name}</b></p>
								<p><img alt="${image.category.name}" src="${screenshots[0]?.url?.encodeAsHTML()}"><br/> </p>
							</td>
							<td>
								<p><b>Гарантии:</b></p>
								<p>Данное изображение предоставлено компанией playfon, экспертом в области мобильного контента. Тщательный отбор и тестирование изображений соответствует высокой степени качества.</p>
							</td>
							
						</tr>
					</table>
					<div>
						<p>Для того, чтобы загрузить изображение, необходимо найти в списке своего оператора связи и отправить sms на короткий номер с текстом заказа <b>${code}</b></p>
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
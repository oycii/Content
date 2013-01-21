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
			<div class="category"><g:link controller="general" action="vendors" params="[category:'song']">(изменить модель)</g:link></div>
		</g:if>

		<div class="contents">
		<table>
			<tr>
			<td width="25%">
			</td>
			<td width="75%">
			<g:if test="${states != null}">
				<div class="main">
					<g:each var="state" in="${states}">
							<div class="music">
								<p>
									<g:link controller="state" action="choose" params="[stateId: state.stateId]">${state.name}</g:link>
								</p>
							</div>
					</g:each>
				</div>
			</g:if>
			</td>
			</tr>

		</table>
		</div>
	</body>
</html>

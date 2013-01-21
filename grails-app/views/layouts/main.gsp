<html>
    <head>
        <title><g:layoutTitle default="Grails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
         <g:javascript library="jquery" plugin="jquery"/>
    </head>
    <body>

	<div class="head">

	<table class="main-table" width="100%">
		<tr>
			<td class="td-white">
				<div class="head-image">
					<a href="http://www.oycii.ru"><img src="http://www.oycii.ru/img/oycii-logo.png" alt="oycii mobile" border="0" /></a>
				</div>
			</td>
			<td class="td-grey">
				<div class="head-main">
					<table class="table-head">
						<tr>
							<td>
								<div class="head-main-services">
								</div>
							</td>
						</tr>
						<tr>
							<td>
								<div class="head-main-search">
										<g:form controller="search" action="search">
											<g:textField size="50%" name="q" maxlength="150" value="${params.q}"/>
											<g:submitButton name="search" value="search"/>
										</g:form>
										<!-- 
									<form name="searchForm" id="searchForm" method="get" action="/search/view">
										<input size="50%" type="text" name="q" maxlength="150" value=""/>
										<input type="hidden" name="p" value="1"/>
										<input type="submit" name="search" value="поиск"/>
									</form>  -->
								</div>
							</td>
						</tr>
					</table>
				</div>
				
			</td>

		</tr>

	</table>

	</div>
    <g:layoutBody />
        

    </body>
</html>
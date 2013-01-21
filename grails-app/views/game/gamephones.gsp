<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
         <link rel="stylesheet" href="${resource(dir:'css',file:'wap.css')}" />
        <title>oycii mobile - ${game.name}</title>
    </head>
    <body>
        
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
		<p><b>${game.name}</b></p>
		
		<p><img alt="${game.name}" src="${screenshots[0]?.url?.encodeAsHTML()}"/>
		</p>
		<p>
			Поддерживаемые модели:<br/>
	    	<g:each in="${phones}" var="p">
	    		${p.model}<br/>
			</g:each>
		</p>
    </body>
</html>

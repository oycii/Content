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
		
		<p><img alt="${game.name}" src="${screenshots[0]?.url?.encodeAsHTML()}"/><br/> ${game.description}</p>
		<p>
	    	<g:each in="${screenshots}" var="s">
	    		<g:if test="${s.small} == 0">
	        		<img alt="${s?.url?.encodeAsHTML()}" src="${s?.url?.encodeAsHTML()}">
	        	</g:if>
			</g:each>
		</p>
		<p>
			<g:link action="gamephones" params="[gameid: game.id]">Поддерживаемые модели >></g:link>
		</p>
    </body>
</html>

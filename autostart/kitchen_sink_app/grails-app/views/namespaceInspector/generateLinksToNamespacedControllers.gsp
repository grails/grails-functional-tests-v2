<html>
<body>

<p>
<g:link plugin="namespaceTwo" action="index" controller="first">Link To FirstController In namespaceTwo</g:link> should be link to com.namespacetwo.FirstController
</p>
<p>
<g:link plugin="namespaceOne" action="index" controller="first">Link To FirstController In namespaceOne</g:link> should be link to com.namespaceone.FirstController
</p>
<p>
<g:link action="index" controller="first">Link To FirstController In Application</g:link> should be link to namespace.FirstController
</p>
<p>
<g:link action="redirectToNoPlugin">Redirect To FirstController In Application</g:link> should redirect to namespace.FirstController
</p>
<p>
<g:link action="redirectToPluginOne">Redirect To FirstController In namespaceOne</g:link> should redirect to com.namespaceone.FirstController
</p>
<p>
<g:link action="redirectToPluginTwo">Redirect To FirstController In namespaceTwo</g:link> should redirect to com.namespacetwo.FirstController
</p>
<p>
<g:link action="chainToNoPlugin">Chain To FirstController In Application</g:link> should chain to namespace.FirstController
</p>
<p>
<g:link action="chainToPluginOne">Chain To FirstController In namespaceOne</g:link> should chain to com.namespaceone.FirstController
</p>
<p>
<g:link action="chainToPluginTwo">Chain To FirstController In namespaceTwo</g:link> should chain to com.namespacetwo.FirstController
</p>
</body>
</html>

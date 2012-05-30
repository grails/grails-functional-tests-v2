<%@ page import="structurededitor.Game" %>



<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'name', 'error')} required">
	<label for="name">
		<g:message code="game.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${gameInstance?.name}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: gameInstance, field: 'goal', 'error')} required">
	<label for="goal">
		<g:message code="game.goal.label" default="Goal" />
		<span class="required-indicator">*</span>
	</label>
	<g:hiddenField name="goal" value="struct"/>
    <g:textField name="goal_own" value="${gameInstance?.goal?.own}"/> : <g:textField name="goal_other" value="${gameInstance?.goal?.other}"/>
</div>


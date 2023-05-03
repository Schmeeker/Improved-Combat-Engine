function init()
	System = java.import('java.lang.System')
	UI = java.import('code.UI')
	UI:message('Initialized!')
end
function hello()
	UI:message('hello mario')
end
function whenhit()
	UI:message('hit')
end
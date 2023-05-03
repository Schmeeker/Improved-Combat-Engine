function onAbsorb()
	local chance = Util:random(0, damage)
	local damageAmount = chance * armour:getProtection()
	attacker:damage(damageAmount)
	if damageAmount>0 then
		UI:damage(attacker, damageAmount, 'Thorns')
	end
Fend
function onSwing()
	if ammo>0 then
		ammo = ammo - 1
	else
		UI:message('No ammo stupid')
	end
	UI:speak(user, "Yeet")
	UI:flavour(user, "only joking")
Fend
function hello()
	System.out:println('hello')
Fend
function isReload()
	return true, false
Fend
var ammo = 15;

event onAbsorb()
	local chance = Util:random(0, damage)
	local damageAmount = chance * armour:getProtection()
	attacker:damage(damageAmount)
	if damageAmount>0 then
		UI:damage(attacker, damageAmount, 'Thorns')
	end
!Event
event onAttack()
	if ammo>0 then
		ammo = ammo - 1
	else
		UI:message('No ammo stupid')
	end
	UI:speak(user, "Yeet")
	UI:flavour(user, "only joking")
!Event
event hello()
	System.out:println('hello')
!Event
event isReload()
	return true, false
!Event
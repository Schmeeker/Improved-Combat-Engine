var ammo = 600;

event attack()
	UI:message('mini-swords go!!!')
	UI:speak(user, 'hiyah!')
	UI:me(user, 'CRAZY')
	dmg = Util:dice(2,5)
	target:damage(dmg)
	UI:damage(target, dmg, 'the gun')
!Event
	
var ammo = 6;
var maxAmmo = 6;

event attack()
	if ammo>0 then
		UI:message('Bang!')
		ammo = ammo - 1
		UI:number('Ammo: %d', ammo)
	else
		UI:message('Clik!')
		UI:speak(user, 'Huh? I\'m need more bullet')
	end
!Event
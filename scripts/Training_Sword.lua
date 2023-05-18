var durability = 5;
var cooldown = 0;

action attack()
	local amount
	if durability > 0 then
		durability = durability - 1
		amount = Util:random(0,2) + user:getStrength()
		target:damage(amount, 'Practice Sword')
		if durability == 0 then
			UI:message('The Training Sword Broke Again!')
		end
	else
		UI:message('The Training Sword is Broken.')
	end
	return amount
!Action

action charged_attack()
	local amount
	if durability > 0 then
		durability = durability - 1
		amount = Util:random(0,2) + user:getStrength()
		amount = amount * 3
		target:damage(amount, 'Charged Attack')
		if durability == 0 then
			UI:message('The Training Sword Broke Again!')
		end
	else
		UI:message('The Training Sword is Broken.')
	end
	UI:me(user, 'is exhausted!')
	UI:speak(user, '*breathes heavily')
!Action

action fix()
	UI:message('Fixing...')
	if durability < 1 then
		durability = Util:random(0,2) + user:getWit()
	else
		UI:message('The Practice Sword isnt Broken!')
	end
	if durability > 0 then
		UI:message('The Training Sword is Fixed!')
	end
!Action
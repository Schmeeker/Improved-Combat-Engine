var beginners_luck = 1;
var skill = 0;

action skilled_attack()
	user:itemAction('attack', target, true)
	
	if skill > 0 then
		local damage = Util:random(1, skill)
		UI:number("+%d Damage for skill!", damage)
		target:damage(damage, 'Skill')
		UI:speak(user, 'I guess all that studying paid off!')
		skill = 0
	else
		UI:speak(user, 'I should have studied!')
	end
!Action

action lucky_attack()
	user:itemAction('attack', target, true)
	
	if beginners_luck > 0 then
		local damage = ( Util:random(1, user:getLuck()) * 3 )
		UI:lucky(user)
		UI:crit()
		target:damage(damage, 'Luck')
		UI:speak(user, 'Heh. Beginner\'s luck!')
		beginners_luck = beginners_luck - 1
	else
		UI:speak(user, 'I\'m so unlucky!')
	end
!Action

action study()
	UI:me(user, 'is Studying...')
	local gains = Util:random(0, user:getWit())
	if gains > 0 then
		UI:number('+%d Skill Points Gained!', gains)
		skill = skill + gains
	else
		UI:message('Its no good!')
	end
!Action 
var timer = 4;

event beginTurn()
	
	if Util:random(1,10) + target:getFortitude() >= 10 then
		timer = timer - 1
	end
	
	if timer > 0 then
		local damage = Util:random(0, 4) + inflictor:getLuck()
		afflicted:damage(damage, inflictor:getName() .. '\'s ' .. me:getName())
		inflictor:heal(1 + damage / 3, 'leech seed')
	else
		UI:message('*the leech seed wore off!')
		target:removeTag(me)
	end
	
	timer = timer - 1
	
!Event